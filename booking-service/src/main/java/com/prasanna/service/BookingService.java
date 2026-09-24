package com.prasanna.service;

import com.prasanna.domain.BookingStatus;
import com.prasanna.dto.BookingRequest;
import com.prasanna.dto.SalonDTO;
import com.prasanna.dto.ServiceDTO;
import com.prasanna.dto.UserDTO;
import com.prasanna.modal.Booking;
import com.prasanna.modal.PaymentOrder;
import com.prasanna.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO user,
                          SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking>  getBookingsBySalon(Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);

    //rabbit

    Booking bookingSuccess(PaymentOrder order) throws Exception;


}
