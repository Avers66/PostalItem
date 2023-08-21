package avers66.postalitem;

import avers66.postalitem.data.*;
import avers66.postalitem.dto.PostOfficeDto;
import avers66.postalitem.dto.PostalDeliveryDto;
import avers66.postalitem.dto.StatusDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PostalItemControllerTest
 *
 * @Author Tretyakov Alexandr
 */


@SpringBootTest
@AutoConfigureMockMvc
public class PostalItemControllerTest {

    @Autowired
    private  PostalService postalService;

    private PostalController postalController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostalRepository postalRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private StatusRepository statusRepo;



    private PostalDelivery postalDelivery;
    private PostOffice postOffice;
    private StatusHistory statusHistory;
    private Long postId;
    private Long postalId;
    private Long statusId;

    //@BeforeEach
    @BeforeEach
    public void setup() {
        postalRepo.deleteAll();
        postRepo.deleteAll();
        statusRepo.deleteAll();

        postOffice = new PostOffice(null, "Krasnodar", "350000", "Краснодар,ул Карасунская, дом 68");
        postRepo.save(postOffice);
        postId = postOffice.getId();
        postalDelivery = new PostalDelivery(null, PostalDelivery.Type.LETTER, "630000", "Новосибирск, ул Северная, 1", "Александр Т.", Status.RECEIVING, ZonedDateTime.now(), postOffice);
        postalRepo.save(postalDelivery);
        postalId = postalDelivery.getId();
        statusHistory = new StatusHistory(null, postalDelivery, Status.REGISTRATION, ZonedDateTime.now().minusDays(5), postOffice);
        statusRepo.save(statusHistory);
        Long statusId = statusHistory.getId();
    }

    @Test
    public void testGetPostOfficeById() throws Exception {
        mockMvc.perform(get("/postoffice/{id}", postId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(postId))
                        .andExpect(jsonPath("$.name").value("Krasnodar"));
    }

    @Test
    public void testGetPostalDeliveryById() throws Exception {
        mockMvc.perform(get("/postalitem/{id}", postalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postalId))
                .andExpect(jsonPath("$.postalCode").value("630000"));
    }



    @Test
    public void testPostPostOfficeCreate() throws Exception {
        PostOfficeDto postOfficeDto = new PostOfficeDto("Novgorod", "173000", "Дворцовая ул, 2");
        Gson gson = new Gson();
        String json = gson.toJson(postOfficeDto);

        mockMvc.perform(post("/postoffice/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.postalCode").value("173000"));
    }

    @Test
    public void testPostPostalDeliveryCreate() throws Exception {
        PostalDeliveryDto postalDeliveryDto= new PostalDeliveryDto(PostalDelivery.Type.LETTER,"630000", "address", "Alex", postOffice.getId());
        Gson gson = new Gson();
        String json = gson.toJson(postalDeliveryDto);

        mockMvc.perform(post("/postalitem/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.postalCode").value("630000"));
    }

    @Test
    public void testPutPostalDeliveryNewStatus() throws Exception {
        StatusDto statusDto = new StatusDto(postalId, postId, Status.ARRIVAL);
        Gson gson = new Gson();
        String json = gson.toJson(statusDto);

        mockMvc.perform(put("/postalitem/newstatus").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentStatus").value("ARRIVAL"));
    }

    @Test
    public void testGetPostofficeAll() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(Arrays.asList(postOffice));

        mockMvc.perform(get("/postoffice/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }




}
