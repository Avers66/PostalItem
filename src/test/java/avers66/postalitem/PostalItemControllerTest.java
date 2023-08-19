package avers66.postalitem;

import avers66.postalitem.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.time.ZonedDateTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    public void setup() {
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
        mockMvc.perform(
                        get("/postoffice/{id}", postId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(postId));
    }
}
