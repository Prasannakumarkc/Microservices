package com.prasanna.modal;

import lombok.Data;

@Data
public class SalonReport {

    private Long salonId;
    private String SalonName;
    private int totalEarnings;
    private Integer totalBookings;
    private Integer cancelledBookings;
    private Double totalRefund;
}
