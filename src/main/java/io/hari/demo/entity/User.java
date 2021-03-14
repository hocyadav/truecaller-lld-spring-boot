package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hari.demo.constant.ContactStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"contacts"}, callSuper = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Contact> contacts = new ArrayList<>();

    //other metadata

    @JsonIgnore
    public List<Contact> getBlockedContact() {
        final List<Contact> blockedContacts = contacts.parallelStream()
                .filter(i -> i.getStatus().equals(ContactStatus.blocked)).collect(Collectors.toList());
        return blockedContacts;
    }
}
