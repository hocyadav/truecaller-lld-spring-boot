package io.hari.demo.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Service
public class NotificationService implements INotificationService {

    @Override
    public void sendNotification(@NonNull final String notificationMsg, @NonNull final BigInteger contactNum) {
        System.out.println("NOTIFICATION : "+notificationMsg +" "+ contactNum);
    }
}
