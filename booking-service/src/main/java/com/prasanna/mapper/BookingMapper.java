package com.prasanna.mapper;

import com.prasanna.dto.BookingDTO;
import com.prasanna.modal.Booking;

public class BookingMapper {

    public static BookingDTO toDto(Booking booking){
        BookingDTO bookingDTO= new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        bookingDTO.setTotalPrice(booking.getTotalPrice());

        return bookingDTO;


    }
}
