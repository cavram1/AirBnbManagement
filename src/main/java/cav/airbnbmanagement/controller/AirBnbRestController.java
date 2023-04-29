package cav.airbnbmanagement.controller;

import cav.airbnbmanagement.model.AirBnb;
import cav.airbnbmanagement.model.Landlord;
import cav.airbnbmanagement.repositories.AirBnbRepository;
import cav.airbnbmanagement.repositories.LandlordRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@RestController
public class AirBnbRestController {

    @Autowired
    private AirBnbRepository airBnbRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Operation(description = "creating a new airbnb")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "createt airbnb succesfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = "application/json"))
            }
    )

    @PostMapping("/airbnb/create")
    private ResponseEntity<AirBnb> create(@RequestBody AirBnb newairBnb,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            if (l != null) {
                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                airBnbRepository.save(newairBnb);
                airBnbRepository.flush();

                return new ResponseEntity<>(newairBnb, HttpStatus.OK);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @Operation(description = "creating a new airbnb")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "createt airbnb succesfully",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = "application/json"))
            }
    )

    @PostMapping("/airbnb/update")
    private ResponseEntity<String> update(@RequestBody AirBnb selectedAirbnb,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());
            Optional<AirBnb> airBnb = airBnbRepository.findById(selectedAirbnb.getAbID());

            if (l != null) {
                if (airBnb.isPresent()) {

                    l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    landlordRepository.save(l);
                    landlordRepository.flush();

                    AirBnb a = airBnb.get();
                    airBnbRepository.save(a);
                    airBnbRepository.flush();

                    return new ResponseEntity<>("success", HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/airbnb/delete/{id}")
    private ResponseEntity<String> delete(@PathVariable("id") Long id,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());
            Optional<AirBnb> airBnb = airBnbRepository.findById(id);

            if (l != null) {
                if (airBnb.isPresent()) {

                    l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                    landlordRepository.save(l);
                    landlordRepository.flush();

                    AirBnb a = airBnb.get();
                    airBnbRepository.delete(a);
                    airBnbRepository.flush();


                    return new ResponseEntity<>("success", HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/airbnb/load")
    private ResponseEntity<ArrayList<AirBnb>> loadAirbnbs(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            ArrayList<AirBnb> airBnbArrayList;
            if (l != null) {

                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                airBnbArrayList = new ArrayList<>(airBnbRepository.findAll());
                return new ResponseEntity<>(airBnbArrayList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/airbnb/load/{id}")
    private ResponseEntity<AirBnb> loadOneAirbnbs(@PathVariable("id") Optional<Long> id,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            if (l != null) {

                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();

                if (id.isPresent()) {
                    Optional<AirBnb> optionalAirbnb = airBnbRepository.findById(id.get());

                    if (optionalAirbnb.isPresent()) {
                        AirBnb a = optionalAirbnb.get();
                        return new ResponseEntity<>(a, HttpStatus.OK);
                    }
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/airbnb/filterBy/location")
    private ResponseEntity<ArrayList<AirBnb>> loadAirbnbToLocation(@RequestParam String location, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Landlord l = null;
        token = token.replace("Bearer ", "");

        try {
            l = landlordRepository.findFirstByTokenAndExpTimeIsGreaterThan(token, System.currentTimeMillis());

            ArrayList<AirBnb> airBnbArrayList;
            if (l != null) {

                l.setExpTime(System.currentTimeMillis() + 1000 * 60 * 10);
                landlordRepository.save(l);
                landlordRepository.flush();
                airBnbArrayList = new ArrayList<>(airBnbRepository.findAirBnbByLocation(location));
                return new ResponseEntity<>(airBnbArrayList, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


}
