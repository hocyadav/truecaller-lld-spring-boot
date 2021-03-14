package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.GlobalContactDirectoryDao;
import io.hari.demo.dao.UserDao;
import io.hari.demo.entity.Contact;
import io.hari.demo.entity.GlobalContactDirectory;
import io.hari.demo.entity.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.hari.demo.constant.ConstantUtil.*;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Service
@Slf4j
public class UserService extends BaseService<User> implements IUserService {
    @Autowired
    GlobalContactDirectoryDao globalContactDao;

    @Autowired
    UserDao userDao;

    @Autowired
    INotificationService notificationService;

    public UserService(BaseDao<User> dao) {
        super(dao);
    }

    public User saveUser(@NonNull final User user) {
        final User saved = userDao.save(user);
        saveContactToGlobalDirectory(user.getContacts());
        return saved;
    }

    private void saveContactToGlobalDirectory(final List<Contact> contacts) {
        contacts.parallelStream().filter(Objects::nonNull).forEach(contact -> {
            final List<BigInteger> phoneNumbers = contact.getPhoneNumberView().getPhoneNumbers();
            Optional.ofNullable(phoneNumbers).ifPresent(phoneNumber -> {// null check + iterate
                phoneNumber.parallelStream().filter(Objects::nonNull).forEach(phoneNum -> {
                    final GlobalContactDirectory verified = GlobalContactDirectory.builder().name(contact.getContactName())
                            .status("verified")
                            .spamCounter(0)
                            .contactNum(phoneNum)
                            .build();
                    globalContactDao.save(verified);
                    log.info("contact {} saved to global directory", phoneNum);
                });
            });
        });
    }

    @Override
    public void receiveCall(@NonNull final User user, @NonNull final BigInteger contactNum) {
        String notificationMsg = "";
        final Optional<Contact> localContact = user.getContacts().parallelStream()
                .filter(contact -> contact.getPhoneNumberView().getPhoneNumbers().contains(contactNum)).findFirst();
        if (localContact.isPresent()) {
            final Contact contact = localContact.get();
            if (contact.getStatus().equals("blocked")) {
                notificationMsg = BLOCKED_CONTACT_CALLING + contact.getContactName();
            } else {
                notificationMsg = SAVED_CONTACT_CALLING + contact.getContactName();
            }
        } else {
            final GlobalContactDirectory globalContact = globalContactDao.findByContactNum(contactNum);
            if (globalContact != null) {
                notificationMsg = GLOBAL_DIRECTORY_CONTACT_CALLING + globalContact.getStatus();
            } else {
                notificationMsg = NEW_NUMBER_CALLING;
            }
        }
        notificationService.sendNotification(notificationMsg, contactNum);
    }

    @Override
    public List<Contact> searchContactByName(@NonNull final User user, @NonNull final String name) {
        log.info("searching local contact directory by name {}", name);
        final List<Contact> searchByName = user.getContacts().parallelStream()
                .filter(Objects::nonNull)
                .filter(i -> i.getContactName().contains(name))
                .collect(Collectors.toList());
        return searchByName;
    }

    @Override
    public List<Contact> searchContactByNumber(@NonNull final User user, @NonNull final BigInteger number) {
        log.info("searching local contact directory by number {}", number);
        final List<Contact> searchByNumber = user.getContacts().parallelStream()
                .filter(Objects::nonNull)
                .filter(i -> i.getPhoneNumberView().getPhoneNumbers().contains(number))
                .collect(Collectors.toList());
        return searchByNumber;
    }
}
