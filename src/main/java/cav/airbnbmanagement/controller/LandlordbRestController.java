package cav.airbnbmanagement.controller;

import cav.airbnbmanagement.model.AirBnb;
import cav.airbnbmanagement.model.Landlord;
import cav.airbnbmanagement.repositories.AirBnbRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandlordbRestController {

    @Autowired
    private LandlordRepository landlordRepository;


    @Operation(description = "registration of landlord")
    @ApiResponses(value = {
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"),
            @ApiResponse(description = "landlord registered succesfully",
                    content = @Content(mediaType = "application/json"), responseCode = "200"
            )
    })
    @PostMapping("landlord/register")
    private ResponseEntity<Landlord> register(@Parameter(description = "register the landlord")
                                              @RequestBody Landlord landlord) {
        if (landlord != null) {
            Landlord l = new Landlord();
            l.setEmail(landlord.getEmail());
            l.setPassword(landlord.getPassword());
            landlordRepository.save(l);
            landlordRepository.flush();

            return new ResponseEntity<>(l, HttpStatus.OK);
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
    @PostMapping("/landlord/login")
    private ResponseEntity<String> login(@RequestBody Landlord landlord) {

        Landlord l = null;
        /**
         * Landlord l = landlordRepository.findByEmail(landlord.getEmail())
         .orElseThrow(() -> new Exception("no landlord found with email :: " + landlord.getEmail()));*/

        try {
            l = landlordRepository.findFirstByEmailAndPassword(landlord.getEmail(), landlord.getPassword());

            if (l != null) {
                String hash = HashGenerator.generateHash(l);
                l.setToken(hash);
                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                return new ResponseEntity<>(l.getToken(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }



}
