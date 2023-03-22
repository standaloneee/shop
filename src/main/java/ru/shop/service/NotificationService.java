package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.entity.Notification;
import ru.shop.entity.Tag;
import ru.shop.exception.TagNotFoundException;
import ru.shop.repository.NotificationRepository;
import ru.shop.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


    public Notification saveNotification(Notification notification){
        return notificationRepository.save(notification);
    }

}
