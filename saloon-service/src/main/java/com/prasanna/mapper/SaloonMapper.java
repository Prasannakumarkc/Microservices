package com.prasanna.mapper;

import com.prasanna.modal.Salon;
import com.prasanna.payload.dto.SalonDTO;

public class SaloonMapper {

    public static SalonDTO mapToDTO(Salon salon){
       SalonDTO salonDTO =new SalonDTO();
       salonDTO.setId(salon.getId());

       salonDTO.setName(salon.getName());
       salonDTO.setAddress(salon.getAddress());
       salonDTO.setCity(salon.getCity());
       salonDTO.setImages(salon.getImages());
//       salonDTO.setCloseTime(salon.getCloseTime());
       salonDTO.setOpenTime(salon.getOpenTime());
       salonDTO.setCloseTime(salon.getCloseTime());
       salonDTO.setPhoneNumber(salon.getPhoneNumber());
       salonDTO.setOwnerId(salon.getOwnerId());
       salonDTO.setEmail(salon.getEmail());
       return salonDTO;


    }

}
