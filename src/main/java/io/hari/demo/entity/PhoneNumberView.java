package io.hari.demo.entity;

import lombok.*;

import javax.validation.constraints.Email;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {})
@AllArgsConstructor
@Builder
public class PhoneNumberView {
    List<BigInteger> phoneNumbers = new ArrayList<>();

    //other metadata key-value,
    // we have added object mapper so if we remove also it will work, we can add as many field

//    @Email
//    String emailAddress;
//    String address;
}
