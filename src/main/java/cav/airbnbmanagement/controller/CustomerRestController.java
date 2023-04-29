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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

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

                /**Customer c = new Customer();
                 c.setEmail(newCustomer.getEmail());
                 c.setFullname(newCustomer.getFullname());
                 c.setVorwahl(newCustomer.getVorwahl());
                 c.setNum(newCustomer.getNum());*/

                customerRepository.save(newCustomer);
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
    @PostMapping("customer/update")
    private ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,
                                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());
            Optional<Customer> c = customerRepository.findById(customer.getCustID());

            if (l != null) {

                if (c.isPresent()) {

                    l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    landlordRepository.save(l);
                    landlordRepository.flush();

                    Customer cu = c.get();

                    cu.setNum(customer.getNum());
                    cu.setFullname(customer.getFullname());
                    cu.setEmail(customer.getEmail());

                    customerRepository.save(cu);
                    customerRepository.flush();

                    return new ResponseEntity<>(cu, HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }


    @Operation(description = "registration of landlord")
    @ApiResponses(value = {
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"),
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"
            )
    })
    @DeleteMapping("customer/delete/{id}")
    private ResponseEntity<Customer> delete(@PathVariable("id") Long id,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            Optional<Customer> c = customerRepository.findById(id);

            if (l != null) {
                if (c.isPresent()) {

                    l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    landlordRepository.save(l);
                    landlordRepository.flush();

                    Customer ct = c.get();
                    customerRepository.delete(ct);
                    customerRepository.flush();

                    return new ResponseEntity<>(ct, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }


    @Operation(description = "registration of landlord")
    @ApiResponses(value = {
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"),
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"
            )
    })
    @GetMapping("customer/load")
    private ResponseEntity<ArrayList<Customer>> loadCustomer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            ArrayList<Customer> customersList;

            if (l != null) {

                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();
                customersList = new ArrayList<>(customerRepository.findAll());

                return new ResponseEntity<>(customersList, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }

    @Operation(description = "registration of landlord")
    @ApiResponses(value = {
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"),
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"
            )
    })
    @GetMapping("customer/load/{id}")
    private ResponseEntity<Customer> loadAllCustomers(@PathVariable("id") Long id,
                                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            Optional<Customer> c = customerRepository.findById(id);

            if (l != null) {
                if (c.isPresent()) {
                    l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    landlordRepository.save(l);
                    landlordRepository.flush();

                    Customer customer = c.get();
                    return new ResponseEntity<>(customer, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }

}
