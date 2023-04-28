package cav.airbnbmanagement.controller;

import cav.airbnbmanagement.model.Customer;
import cav.airbnbmanagement.model.Landlord;
import cav.airbnbmanagement.repositories.CustomerRepository;
import cav.airbnbmanagement.repositories.LandlordRepository;
import cav.airbnbmanagement.service.HashGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @PostMapping("/customer/create")
    private ResponseEntity<Customer> create(@RequestBody Customer newCustomer,
                                            @RequestHeader("Authorization") String token) {

        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            if (l != null) {
                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                Customer c = new Customer();
                c.setEmail(newCustomer.getEmail());
                c.setFullname(newCustomer.getFullname());
                c.setVorwahl(newCustomer.getVorwahl());
                c.setNum(newCustomer.getNum());

                customerRepository.save(c);
                customerRepository.flush();

                return new ResponseEntity<>(null, HttpStatus.OK);

            } else {

                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @Operation(description = "registration of landlord")
    @ApiResponses(value = {
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"),
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"
            )
    })
    @PostMapping("landlord/login")
    private ResponseEntity<Landlord> login(@RequestBody Landlord landlord) {
        try {
            Landlord l = landlordRepository.findByEmail(landlord.getEmail())
                    .orElseThrow(() -> new Exception("no landlord found with email :: " + landlord.getEmail()));

            if (l != null) {
                String hash = HashGenerator.generateHash(l);
                l.setToken(hash);
                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                return new ResponseEntity<>(l, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
