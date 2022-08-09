package com.example.commonInfraProxy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserEntity {
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private long mobile;
}
