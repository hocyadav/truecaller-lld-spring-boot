package io.hari.demo.entity;

import io.hari.demo.entity.converter.BigIntConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.math.BigInteger;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class GlobalContactDirectory extends BaseEntity{
    String name;

    @Column(unique = true, length = 13)
    @Convert(converter = BigIntConverter.class)
    BigInteger contactNum;

    String status; //blocked , not blocked, spam
    Integer spamCounter;

//    @Convert(converter = ContactNameListConvertor.class)
//    ContactNameList allContactNames;
}
