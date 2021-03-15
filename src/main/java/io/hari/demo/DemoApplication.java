package io.hari.demo;

import io.hari.demo.config.AppConfig;
import io.hari.demo.constant.ContactStatus;
import io.hari.demo.dao.ContactDao;
import io.hari.demo.dao.GlobalContactDirectoryDao;
import io.hari.demo.dao.NativeSQLInsert;
import io.hari.demo.dao.UserDao;
import io.hari.demo.entity.*;
import io.hari.demo.service.ContactService;
import io.hari.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    AppConfig config;
    @Autowired
    UserDao userDao;
    @Autowired
	UserService userService;
    @Autowired
    ContactDao contactDao;
    @Autowired
    GlobalContactDirectoryDao contactDirectoryDao;
    @Autowired
	ContactService contactService;

    @Autowired
	NativeSQLInsert nativeSQLInsert;

    @Override
    public void run(String... args) throws Exception {
    	// todo DONE : create 2 contact and assign to one user
		final Contact chandan = Contact.builder().contactName("chandan")
				.status(ContactStatus.verified).phoneNumberView(PhoneNumberView.builder()
						.phoneNumbers(Arrays.asList(BigInteger.valueOf(9887700499L), BigInteger.valueOf(1234567890L)))
						.build()).build();

		final Contact neha = Contact.builder().contactName("neha")
				.status(ContactStatus.verified).phoneNumberView(PhoneNumberView.builder()
						.phoneNumbers(Arrays.asList(BigInteger.valueOf(5600371234L)))
						.build()).build();

		final User hariom_htc = userService.saveUser(User.builder().name("hariom htc")
				.contacts(Arrays.asList(chandan, neha))
				.build());//working 1 : M uni directional

		//todo :DONE search by name, +ve and -ve test
		final List<Contact> searchContactByName = userService.searchContactByName(hariom_htc, "neha");
		System.out.println("searchContactByName = " + searchContactByName);

		final List<Contact> chandan1 = userService.searchContactByName(hariom_htc, "chandan");
		System.out.println("chandan1 = " + chandan1);

		final List<Contact> abcSearch = userService.searchContactByName(hariom_htc, "abc");
		System.out.println("abcSearch = " + abcSearch);

		//todo search by number, +ve , -ve test
		final List<Contact> searchContactByNumber = userService.searchContactByNumber(hariom_htc, BigInteger.valueOf(9887700499L));
		System.out.println("searchContactByNumber = " + searchContactByNumber);

		final List<Contact> searchContactByNumber2 = userService.searchContactByNumber(hariom_htc, BigInteger.valueOf(1234567890L));
		System.out.println("searchContactByNumber2 = " + searchContactByNumber2);

		final List<Contact> searchContactByNumber3 = userService.searchContactByNumber(hariom_htc, BigInteger.valueOf(5600371234L));
		System.out.println("searchContactByNumber3 = " + searchContactByNumber3);

		final List<Contact> negativeSearchByNumber = userService.searchContactByNumber(hariom_htc, BigInteger.valueOf(123L));
		System.out.println("negativeSearchByNumber = " + negativeSearchByNumber);

		//todo : DONE : add to block list
		final String blocked = contactService.blockOrUnblockContact(hariom_htc, chandan, ContactStatus.blocked);
		System.out.println("blocked = " + blocked);

		//todo : DONE : receive call -> blocked number , new number, not blocked number
		userService.receiveCall(hariom_htc, BigInteger.valueOf(9887700499L));
		userService.receiveCall(hariom_htc, BigInteger.valueOf(56699L));
		userService.receiveCall(hariom_htc, BigInteger.valueOf(5600371234L));

		//todo :DONE: find in global directory by number, by name: only for premium users
		final GlobalContactDirectory byContactNum = contactDirectoryDao.findByContactNum(BigInteger.valueOf(9887700499L));
		System.out.println("premium users 9887700499L = " + byContactNum);

		final GlobalContactDirectory byContactNum2 = contactDirectoryDao.findByContactNum(BigInteger.valueOf(5600371234L));
		System.out.println("premium users byContactNum 5600371234L = " + byContactNum2);

		final List<GlobalContactDirectory> chandan2 = contactDirectoryDao.findAllByName("chandan");
		System.out.println("premium users by name chandan = " + chandan2);

		final List<GlobalContactDirectory> neha1 = contactDirectoryDao.findAllByName("neha");
		System.out.println("premium users by name neha1 = " + neha1);

		final List<GlobalContactDirectory> negative = contactDirectoryDao.findAllByName("negative");
		System.out.println("premium users by name negative = " + negative);

		//todo : DONE report spam and check in db spam count
		contactService.reportSpam(hariom_htc, BigInteger.valueOf(9887700499L));

		System.out.println("old chandan = " + chandan);
		contactService.blockOrUnblockContact(hariom_htc, chandan, ContactStatus.unblocked);
		System.out.println("new chandan = " + chandan);

		userService.receiveCall(hariom_htc, BigInteger.valueOf(9887700499L));

		//todo DONE: mark new number as spam, and get call again
		contactService.reportSpam(hariom_htc, BigInteger.valueOf(495452L));
		userService.receiveCall(hariom_htc, BigInteger.valueOf(495452L));


		//todo : DONE :  add baseService abstarct class and common methods and test
		contactService.findAll().stream().forEach(System.out::println);
		contactDirectoryDao.findAll().stream().forEach(System.out::println);
		userService.findAll().stream().forEach(System.out::println);

		//todo :DONE  report spam contanct multiple time and fetch
		contactService.reportSpam(hariom_htc, BigInteger.valueOf(495452L));
		contactService.reportSpam(hariom_htc, BigInteger.valueOf(495452L));
		contactService.reportSpam(hariom_htc, BigInteger.valueOf(495452L));
		final GlobalContactDirectory spamContact = contactDirectoryDao.findByContactNum(BigInteger.valueOf(495452L));
		System.out.println("spamContact = " + spamContact);

		//todo : read file and create contact and save contact
		final Stream<String> lines = Files.lines(Paths.get("file.txt"));
		lines.forEach(i -> {
			final String[] s = i.split(" ");
			final String status = s[2];
			final BigInteger phoneNum = BigInteger.valueOf(Long.valueOf(s[1]));
			final Contact contact = Contact.builder().contactName(s[0])
					.phoneNumberView(PhoneNumberView.builder().phoneNumbers(Arrays.asList(phoneNum)).build())
					.status(ContactStatus.valueOf(status))
					.build();
			contactDao.save(contact);
		});
//		userDao.nativeInsertSQL();
//		contactDao.nativeInsertSQL();

//		nativeSQLInsert.insertWithQuery(Contact.builder().contactName("test em native").build());



    }
}
