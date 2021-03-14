package io.hari.demo.service;

import lombok.NonNull;

import java.math.BigInteger;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
public interface INotificationService {
    void sendNotification(final String notificationMsg, final BigInteger contactNum);
}
