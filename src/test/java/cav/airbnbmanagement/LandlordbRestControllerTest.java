package cav.airbnbmanagement;

import cav.airbnbmanagement.controller.AirBnbRestController;
import cav.airbnbmanagement.controller.LandlordbRestController;
import cav.airbnbmanagement.model.AirBnb;
import cav.airbnbmanagement.model.Landlord;
import cav.airbnbmanagement.repositories.LandlordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(controllers = LandlordbRestController.class)
class LandlordbRestControllerTest {

    @MockBean
    private LandlordRepository landlordRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Landlord l = new Landlord();
        l.setEmail("e@gmail.com");
        l.setPassword("1234");
        l.setToken("");
        l.setExpTime(0);
        landlordRepository.save(l);

        /**when(landlordRepository.findById(1L)).thenReturn(Optional.of(l));

        AirBnb airBnb1 = new AirBnb();
        AirBnb airBnb2 = new AirBnb();
        airBnb1.setAddress("straße");
        airBnb2.setDesc("a room");
        airBnbRepository.save(airBnb1);
        airBnbRepository.save(airBnb2);

        List<AirBnb> list = new ArrayList<>(Arrays.asList(airBnb1,airBnb2));
        when(airBnbRepository.findAll()).thenReturn(list);

        when(airBnbRepository.findById(1L)).thenReturn(Optional.of(airBnb1));*/
    }

    @Test
    void loginLandlord() throws Exception {
        Landlord l = new Landlord(1L, "e@gmail.com", "1234", "", 0);

        mockMvc.perform(post("/landlord/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}