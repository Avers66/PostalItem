package avers66.postalitem;

import avers66.postalitem.data.*;
import avers66.postalitem.dto.PostOfficeDto;
import avers66.postalitem.dto.PostalDeliveryDto;
import avers66.postalitem.dto.StatusDto;
import avers66.postalitem.utils.ZonedDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.time.ZonedDateTime;
import java.util.Arrays;


import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PostalItemControllerTest
 *
 * @Author Tretyakov Alexandr
 */


@SpringBootTest
@AutoConfigureMockMvc
public class PostalItemControllerTest {

    //@Autowired
   // private  PostalService postalService;

    //private PostalController postalController;

    //private LocalDateTypeAdapter localDateTypeAdapter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostalRepository postalRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private StatusRepository statusRepo;



    private PostalDelivery postalDelivery;
    private PostalDelivery postalDelivery1;
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
        postalDelivery1 = new PostalDelivery(null, PostalDelivery.Type.PACKAGE, "100000", "Н-ск, ул Неизвестная, 111", "Сергей А..", Status.ARRIVAL, ZonedDateTime.now(), postOffice);
        postalRepo.save(postalDelivery);
        postalRepo.save(postalDelivery1);
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
                .andExpect(jsonPath("$.postalCode").value("630000"))
                .andDo(print());
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
    public void testGetPostOfficeAll() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(Arrays.asList(postOffice));

        MvcResult mvcResult = mockMvc.perform(get("/postoffice/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(json)).andReturn();
    }

    @Test
    public void testPostPostOfficeCreate2() throws Exception {
        mockMvc.perform(post("/postoffice/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Novgorod2\", \"postalCode\": \"173000\", \"address\":\"Дворцовая ул, 2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.postalCode").value("173000"))
                .andDo(print());
    }

    @Test
    public void testGetPostalItemAll() throws Exception {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
                .create();
        String json = gson.toJson(Arrays.asList(postalDelivery));

        MvcResult mvcResult = mockMvc.perform(get("/postalitem/all"))
                .andDo(print())
                .andExpect(jsonPath("$.[*].postalCode", hasItems("630000", "100000")))
                .andExpect(status().isOk()).andReturn();
                //.andExpect(content().json(json))  //отличается формат дат при сравнении

    }
}
