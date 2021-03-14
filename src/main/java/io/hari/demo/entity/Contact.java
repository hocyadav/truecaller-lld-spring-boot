package io.hari.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hari.demo.constant.ContactStatus;
import io.hari.demo.entity.converter.PhoneNumberConverter;
import lombok.*;

import javax.persistence.*;

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
@Table(name = "contacts")
public class Contact extends BaseEntity {
    String contactName;

    @Convert(converter = PhoneNumberConverter.class)
    PhoneNumberView phoneNumberView;

    @Enumerated(EnumType.STRING)
    ContactStatus status;

    @JsonIgnore
    public boolean isContactEqual(Contact contact) {
        if (this.contactName.equals(contact.getContactName())
                && this.phoneNumberView.equals(contact.getPhoneNumberView())) {
            return true;
        }
        return false;
    }

    //other metadata
}
