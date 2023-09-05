package com.ahmad.grpc.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class Person {

    private String firstName;

    private String lastName;

    private String dob;

    private String email;

    private String phoneNumber;

    private Date createdAt;

}
