package io.hari.demo.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hari.demo.entity.PhoneNumberView;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
public class PhoneNumberConverter implements AttributeConverter<PhoneNumberView, String> {
    ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(PhoneNumberView phoneNumberView) {
        return objectMapper.writeValueAsString(phoneNumberView);
    }

    @SneakyThrows
    @Override
    public PhoneNumberView convertToEntityAttribute(String s) {
        return objectMapper.readValue(s, PhoneNumberView.class);
    }
}
