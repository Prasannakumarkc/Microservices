package com.prasanna.service.imp;

import com.prasanna.mapper.NotificationMapper;
import com.prasanna.modal.Notification;
import com.prasanna.payload.dto.BookingDTO;
import com.prasanna.payload.dto.NotificationDTO;
import com.prasanna.repository.NotificationRepository;
import com.prasanna.service.NotificationService;
import com.prasanna.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    //called as instance
    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;

    @Override
    public NotificationDTO createNotification(Notification notification) throws Exception {

        Notification savedNotification = notificationRepository.save(notification);

        BookingDTO bookingDTO = bookingFeignClient.getBookingsById(
                savedNotification.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(
                savedNotification,
                bookingDTO);

        return notificationDTO;
    }


    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
                return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) throws Exception {
        return notificationRepository.findById(notificationId).map(
                notification -> {
                    notification.setIsRead(true);
                    return notificationRepository.save(notification);
                }
        ).orElseThrow(()-> new Exception("Notification not found"));
    }
}
