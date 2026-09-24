package com.prasanna.controller;

import com.prasanna.modal.ServiceOffering;
import com.prasanna.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;


    //http://localhost:5004/api/service-offering/salon/1
    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ) {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService
                .getAllServiceBySalonId(salonId, categoryId); //working
//                .getAllServiceBySalonId(categoryId, salonId);

        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("{id}")
    public ResponseEntity<ServiceOffering> getServicesById(
            @PathVariable Long id
            ) throws Exception {

        ServiceOffering serviceOffering = serviceOfferingService
                .getServiceById(id);

        return ResponseEntity.ok(serviceOffering);
    }


    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids
    ) {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService
                .getServicesByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }
}
