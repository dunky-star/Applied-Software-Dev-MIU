package edu.miu.cs.cs489appsd.hotel.repositories;

import edu.miu.cs.cs489appsd.hotel.entities.Notification;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NotificationRepository extends ReactiveCrudRepository<Notification, Long> {
}
