package com.prasanna.controller;


import com.prasanna.mapper.SaloonMapper;
import com.prasanna.modal.Salon;
import com.prasanna.payload.dto.SalonDTO;
import com.prasanna.payload.dto.UserDTO;
import com.prasanna.service.SalonService;
import com.prasanna.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final UserFeignClient userFeignClient;

    // http://localhost:5002/api/salons

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(
            @RequestBody SalonDTO salonDTO,
            @RequestHeader("Authorization") String jwt) throws Exception {

//        UserDTO userDTO= new UserDTO();
        //        userDTO.setId(1L);// below code written(before owner only get jwt token and update)
        UserDTO userDTO= userFeignClient.getUserProfile(jwt).getBody();


        Salon salon= salonService.createSalon(salonDTO,userDTO);
        SalonDTO salonDTO1= SaloonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    // http://localhost:5002/api/salons/1

    @PutMapping ("/{salonId}")
    public ResponseEntity<SalonDTO> updateSalon(
            @PathVariable Long salonId,
//            @RequestBody SalonDTO salonDTO) throws Exception {
            @RequestBody SalonDTO salonDTO,
            @RequestHeader("Authorization") String jwt) throws Exception {

//        UserDTO userDTO= new UserDTO(); //logged in user and owner same checking
//        userDTO.setId(1L);

        UserDTO userDTO= userFeignClient.getUserProfile(jwt).getBody();


        System.out.println("-----------"+salonId+"email"+salonDTO.getEmail());
        Salon salon= salonService.updateSalon(salonDTO,userDTO,salonId);
        SalonDTO salonDTO1= SaloonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    // http://localhost:5002/api/salons/1

    @GetMapping()
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception{

        List<Salon> salons= salonService.getAllSalons();
        List<SalonDTO> salonDTOS=salons.stream().map((salon) -> {
            SalonDTO salonDTO=SaloonMapper.mapToDTO(salon);
            return salonDTO;
        }
        ).toList();

        return ResponseEntity.ok(salonDTOS);
    }

    // http://localhost:5002/api/salons/1

    @GetMapping("{salonId}")
    public ResponseEntity<SalonDTO> getSalonsById(
            @PathVariable Long salonId
    ) throws Exception{


        Salon salon=salonService.getSalonById(salonId);

        SalonDTO salonDTO=SaloonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }


    // http://localhost:5002/api/salons/search?city=mumbai
    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(
            @RequestParam("city") String city
    ) throws Exception{

        List<Salon> salons= salonService.searchSalonByCity(city);

        List<SalonDTO> salonDTOS=salons.stream().map((salon) -> {
                    SalonDTO salonDTO=SaloonMapper.mapToDTO(salon);
                    return salonDTO;
                }
        ).toList();

        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonsByOwnerId(
//            @PathVariable Long salonId, //below
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
//        UserDTO userDTO= new UserDTO();
//        userDTO.setId(1L);

        UserDTO userDTO= userFeignClient.getUserProfile(jwt).getBody();

        if (userDTO==null){
            throw new Exception("User not found from jwt..");
        }


        Salon salon=salonService.getSalonByOwnerId(userDTO.getId());

        SalonDTO salonDTO=SaloonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }


}
