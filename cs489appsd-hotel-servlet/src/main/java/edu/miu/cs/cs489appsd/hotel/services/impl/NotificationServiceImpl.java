package edu.miu.cs.cs489appsd.hotel.services.impl;

import edu.miu.cs.cs489appsd.hotel.dtos.NotificationDto;
import edu.miu.cs.cs489appsd.hotel.entities.Notification;
import edu.miu.cs.cs489appsd.hotel.repositories.NotificationRepository;
import edu.miu.cs.cs489appsd.hotel.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender javaMailSender;

    private final NotificationRepository notificationRepository;
    @Override
    @Async // This annotation is used to run the method asynchronously to prevent blocking the main thread
    public void sendEmail(NotificationDto notificationDto) {

        SimpleMailMessage mailSender = new SimpleMailMessage();
        mailSender.setTo(notificationDto.getRecipient());
        mailSender.setSubject(notificationDto.getSubject());
        mailSender.setText(notificationDto.getBody());

        javaMailSender.send(mailSender);

        // Save to the database
        Notification notificationToSave = Notification.builder()
                .recipient(notificationDto.getRecipient())
                .subject(notificationDto.getSubject())
                .body(notificationDto.getBody())
                .bookingReference(notificationDto.getBookingReference())
                .notificationType(notificationDto.getType())
                .build();
        notificationRepository.save(notificationToSave);
    }

    @Override
    public void sendSms() {

    }

    @Override
    public void sendWhatsapp() {

    }
}
