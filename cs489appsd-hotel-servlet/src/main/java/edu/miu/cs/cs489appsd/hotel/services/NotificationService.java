package edu.miu.cs.cs489appsd.hotel.services;

import edu.miu.cs.cs489appsd.hotel.dtos.NotificationDto;

public interface NotificationService {
    void sendEmail(NotificationDto notificationDto);
    void sendSms();
    void sendWhatsapp();
}
