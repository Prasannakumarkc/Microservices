package com.prasanna.controller;

import com.prasanna.domain.BookingStatus;
import com.prasanna.domain.PaymentMethod;

import com.prasanna.dto.*;
import com.prasanna.mapper.BookingMapper;
import com.prasanna.modal.Booking;
import com.prasanna.modal.SalonReport;
import com.prasanna.service.BookingService;
import com.prasanna.service.client.PaymentFeignClient;
import com.prasanna.service.client.SalonFeignClient;
import com.prasanna.service.client.ServiceOfferingFeignClient;
import com.prasanna.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    //http://localhost:5005/api/bookings?salonId=1&paymentMethod=RAZORPAY
//    {
//    "startTime": "2024-12-04T10:00:00",
//    "serviceIds": [1]
//    }
    @PostMapping
//    public ResponseEntity<Booking> createBooking(
    public ResponseEntity<PaymentLinkResponse> createBooking(

            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

//        UserDTO user = new UserDTO();
//        user.setId(1L);
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();

//        SalonDTO salon = new SalonDTO();
//        salon.setId(salonId);
//        salon.setOpenTime(LocalTime.now());
//        salon.setCloseTime(LocalTime.now().plusHours(12));
        SalonDTO salon = salonFeignClient.getSalonsById(salonId).getBody();

//        Set<ServiceDTO> serviceDTOset = new HashSet<>();

//        ServiceDTO serviceDTO = new ServiceDTO();
//        serviceDTO.setId(1L);
//        serviceDTO.setPrice(399);
//        serviceDTO.setDuration(45);
//        serviceDTO.setName("Hair cut for men");
//          serviceDTOSet.add(serviceDTO);

        Set<ServiceDTO> serviceDTOset = serviceOfferingFeignClient.getServicesByIds(
                bookingRequest.getServiceIds()).getBody();

        if (serviceDTOset.isEmpty()){
            throw new Exception("No services found...");
        }

        Booking booking = bookingService.createBooking(bookingRequest,
                user,
                salon,
                serviceDTOset);

        BookingDTO bookingDTO = BookingMapper.toDto(booking);
        PaymentLinkResponse paymentLinkResponse = paymentFeignClient.createPaymentLink(
                bookingDTO,
                paymentMethod,
                jwt).getBody();


//        return ResponseEntity.ok(booking);
        return ResponseEntity.ok(paymentLinkResponse);


    }

    //http://localhost:5005/api/bookings/customer
    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        if (user == null || user.getId() == null) {
            throw new Exception("User not found from jwt...");
        }

        List<Booking> bookings = bookingService.getBookingsByCustomer(user.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    //http://localhost:5005/api/bookings/salon
    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonsByOwnerId(jwt).getBody();
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream().map(booking -> {
            return BookingMapper.toDto(booking);
        }).collect(Collectors.toSet());
    }

    //    http://localhost:5005/api/bookings/2
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingsById(
            @PathVariable Long bookingId)
            throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }


    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, status);

        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam(required = false) LocalDate date) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);

        List<BookingSlotDTO> slotsDTOs = bookings.stream().map(booking -> {
            BookingSlotDTO slotDTO = new BookingSlotDTO();
            slotDTO.setStartTime(booking.getStartTime());
            slotDTO.setEndTime(booking.getEndTime());
            return slotDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(slotsDTOs);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt

    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonsByOwnerId(jwt).getBody();
        SalonReport report = bookingService.getSalonReport(salonDTO.getId());

        return ResponseEntity.ok(null);
    }
}
