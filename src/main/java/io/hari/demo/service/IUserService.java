package io.hari.demo.service;

import io.hari.demo.entity.Contact;
import io.hari.demo.entity.User;
import lombok.NonNull;

import java.math.BigInteger;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
public interface IUserService {
    void receiveCall(final User user, final BigInteger contactNum);

    List<Contact> searchContactByName(final User user, final String name);

    List<Contact> searchContactByNumber(final User user, final BigInteger number);
}
