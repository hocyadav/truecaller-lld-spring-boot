package io.hari.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author Hariom Yadav
 * @create 14-03-2021
 */
@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
