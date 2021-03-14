package io.hari.demo.entity.converter;

import javax.persistence.AttributeConverter;
import java.math.BigInteger;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
public class BigIntConverter implements AttributeConverter<BigInteger, String> {
    @Override
    public String convertToDatabaseColumn(BigInteger bigInteger) {
        return bigInteger.toString();
    }

    @Override
    public BigInteger convertToEntityAttribute(String s) {
        return new BigInteger(s);
    }
}
