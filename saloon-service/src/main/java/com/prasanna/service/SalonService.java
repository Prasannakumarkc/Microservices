package com.prasanna.service;

import com.prasanna.modal.Salon;
import com.prasanna.payload.dto.SalonDTO;
import com.prasanna.payload.dto.UserDTO;

import java.util.List;

public interface SalonService {
    Salon createSalon(SalonDTO salon, UserDTO user);

    Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) throws Exception;

    List<Salon> getAllSalons();

    Salon getSalonById(Long salonId) throws Exception;

    Salon getSalonByOwnerId(Long ownerId) throws Exception;

    List<Salon> searchSalonByCity(String city);
}
