package com.prasanna.service;

import com.prasanna.dto.CategoryDTO;
import com.prasanna.dto.SalonDTO;
import com.prasanna.dto.ServiceDTO;
import com.prasanna.modal.ServiceOffering;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDto,
                                  ServiceDTO serviceDTO,
                                  CategoryDTO categoryDTO);
    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws Exception;

//    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);
Set<ServiceOffering> getAllServiceBySalonId( Long categoryId,Long salonId);


    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long id) throws Exception;
}
