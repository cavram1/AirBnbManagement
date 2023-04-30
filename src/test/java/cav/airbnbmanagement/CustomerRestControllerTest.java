package cav.airbnbmanagement;

import cav.airbnbmanagement.AirBnbManagementApplication;
import cav.airbnbmanagement.model.Customer;
import cav.airbnbmanagement.repositories.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {AirBnbManagementApplication.class})
public class CustomerRestControllerTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer c;

    @Test
    @Order(1)
    public void insertintoDB() {
        c = new Customer();
        c.setEmail("customer@email.com");


        Assertions.assertDoesNotThrow(() -> {
            customerRepository.save(c);
        });
    }

}