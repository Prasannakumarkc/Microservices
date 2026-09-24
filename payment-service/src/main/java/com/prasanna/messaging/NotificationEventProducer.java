package com.prasanna.messaging;

import com.prasanna.payload.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sentNotification(Long bookingId,
                                 Long userId,
                                 Long salonId){

        NotificationDTO notificationDTO=new NotificationDTO();
        notificationDTO.setBookingId(bookingId);
        notificationDTO.setUserId(userId);
        notificationDTO.setSalonId(salonId);
        notificationDTO.setDescription("new booking got confirmed");
        notificationDTO.setType("BOOKING");

        //rabbitmq
        rabbitTemplate.convertAndSend("notification-queue",notificationDTO);
    }
}
