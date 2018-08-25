package com.izumi.myletter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "TEST")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class UserInfo {
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private String id;

    @Column(name = "I_NAME")
    private String name;

    @Column(name = "I_PHONE")
    private String phone;

    @Column(name = "I_DATE")
    private String date;

    @Column(name = "I_TIME")
    private String time;

    @Column(name = "I_PICTURE")
    private String picture;
}
