package cav.airbnbmanagement.test;

import cav.airbnbmanagement.controller.AirBnbRestController;
import cav.airbnbmanagement.controller.LandlordbRestController;
import cav.airbnbmanagement.model.AirBnb;
import cav.airbnbmanagement.model.Landlord;
import cav.airbnbmanagement.repositories.AirBnbRepository;
import cav.airbnbmanagement.repositories.LandlordRepository;
import cav.airbnbmanagement.service.HashGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = AirBnbRestController.class)
class AirBnbRestControllerTest {


    @MockBean
    private AirBnbRepository airBnbRepository;


    @MockBean
    private LandlordRepository landlordRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /*
        @BeforeAll
        void setUp(){
            AirBnb airBnb1 = new AirBnb();
            AirBnb airBnb2 = new AirBnb();
            airBnb1.setAddress("straße");
            airBnb2.setDesc("a room");
            airBnbRepository.save(airBnb1);
            airBnbRepository.save(airBnb2);

            List<AirBnb> list = new ArrayList<>(Arrays.asList(airBnb1,airBnb2));
            when(airBnbRepository.findAll()).thenReturn(list);

            when(airBnbRepository.findById(1L)).thenReturn(Optional.of(airBnb1));
        }
    */
    @BeforeAll
    void setUp() {

        Landlord l = new Landlord();
        l.setToken("hddjdjiedjheo473994fbcobc");
        when(landlordRepository.findById(1L)).thenReturn(Optional.of(l));

        AirBnb airBnb1 = new AirBnb();
        AirBnb airBnb2 = new AirBnb();
        airBnb1.setAddress("straße");
        airBnb2.setDesc("a room");
        airBnbRepository.save(airBnb1);
        airBnbRepository.save(airBnb2);

        List<AirBnb> list = new ArrayList<>(Arrays.asList(airBnb1,airBnb2));
        when(airBnbRepository.findAll()).thenReturn(list);

        when(airBnbRepository.findById(1L)).thenReturn(Optional.of(airBnb1));
    }


    @Test

    void createAirBnb() throws Exception {
        AirBnb ab = new AirBnb(1L, "haus", "A_1a",
                "austria", "sum", "12", null);

        Optional<Landlord> l = landlordRepository.findById(1L);
        if (l.isPresent()) {
            Landlord ll = l.get();

            mockMvc.perform(post("/airbnb/create")
                            .header("Authorization", "Bearer " + ll.getToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(ab)))
                    .andExpect(status().isOk())
                    .andDo(print());

            airBnbRepository.save(ab);
        }
    }



    @Test
    @Order(2)
    void getAirbnb() throws Exception {
        Optional<Landlord> l = landlordRepository.findById(1L);
        if (l.isPresent()) {
            Landlord ll = l.get();

            mockMvc.perform(get("/airbnb/load")
                            .header("Authorization", "Bearer " + ll.getToken()))
                    .andExpect(status().isOk());
            assertEquals(airBnbRepository.findAll().size(), 2);
        }
    }

    /*

     */

/*
    @Test
    void shouldReturnTutorial() throws Exception {
        long id = 1L;
        AirBnb tutorial = new AirBnb(id, "a", "a", "a", "a", "a", null);

        when(airBnbRepository.findById(id)).thenReturn(Optional.of(tutorial));
        mockMvc.perform(get("/api/tutorials/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.desc").value(tutorial.getDesc()))
                .andExpect(jsonPath("$.location").value(tutorial.getLocation()))
                .andExpect(jsonPath("$.address").value(tutorial.getAddress()))
                .andDo(print());
    }
    */


}