package io.hari.demo.service;

import io.hari.demo.dao.BaseDao;
import io.hari.demo.dao.ContactDao;
import io.hari.demo.dao.GlobalContactDirectoryDao;
import io.hari.demo.entity.Contact;
import io.hari.demo.constant.ContactStatus;
import io.hari.demo.entity.GlobalContactDirectory;
import io.hari.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Service
@Slf4j
public class ContactService extends BaseService<Contact>{
    @Autowired
    ContactDao contactDao;

    @Autowired
    GlobalContactDirectoryDao globalDirectoryDao;

    public ContactService(BaseDao<Contact> contactDao) {
        super(contactDao);
    }

    public String blockOrUnblockContact(User user, Contact contact, ContactStatus status) {
        if (status.equals("blocked") && user.getBlockedContact().equals(contact)) {
            return "already blocked";
        }
        if (status.equals("unblock") && user.getContacts().equals(contact)) {
            return "already un blocked";
        }
        final Optional<Contact> optionalContact = user.getContacts().stream()
                .filter(i -> i.isContactEqual(contact)).findFirst();
        optionalContact.ifPresent(c -> {
            c.setStatus(status);
            contactDao.save(c);
        });
        return "new contact "+status;
    }

    public void reportSpam(User user, BigInteger contactNum) {
        final GlobalContactDirectory globalContact =
                globalDirectoryDao.findByContactNum(contactNum);
        if (globalContact != null) { //old updated counters
            globalContact.setSpamCounter(globalContact.getSpamCounter() + 1);
            globalDirectoryDao.save(globalContact);
        } else { // create new entry
            globalDirectoryDao.save(GlobalContactDirectory.builder()
                    .contactNum(contactNum)
                    .spamCounter(1)
                    .status("spam")
                    .build());
        }
        log.info("User {} reported contact number {} as spam ", user.getName(), contactNum);
    }

}
