package com.prasanna.controller;

import com.prasanna.dto.CategoryDTO;
import com.prasanna.dto.SalonDTO;
import com.prasanna.dto.ServiceDTO;
import com.prasanna.modal.ServiceOffering;
import com.prasanna.service.ServiceOfferingService;
import com.prasanna.service.client.CategoryFeignClient;
import com.prasanna.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;


    //http://localhost:5004/api/service-offering/salon-owner
    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

//        SalonDTO salonDTO = new SalonDTO();
//        salonDTO.setId(1L);

        SalonDTO salonDTO = salonFeignClient.getSalonsByOwnerId(jwt).getBody();

//        CategoryDTO categoryDTO = new CategoryDTO();
//        categoryDTO.setId(serviceDTO.getCategory());

        CategoryDTO categoryDTO = categoryFeignClient
                .getCategoriesByIdAndSalon(serviceDTO.getCategory(), salonDTO.getId()).getBody();
//                .getCategoriesByIdAndSalon(salonDTO.getId()).getBody(),serviceDTO.getCategory();//1


        ServiceOffering serviceOfferings = serviceOfferingService
                .createService(salonDTO, serviceDTO, categoryDTO);

        return ResponseEntity.ok(serviceOfferings);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception{

        ServiceOffering serviceOfferings = serviceOfferingService
                .updateService(id,serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}
