package com.ahmad.grpc.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDto {

    private String firstName;

    private String lastName;

    private String dob;

    private String email;

    private String phoneNumber;
}