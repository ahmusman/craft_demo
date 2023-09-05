package com.ahmad.grpc.demo.service;

import com.ahmad.grpc.demo.controller.PersonGRPC;
import com.ahmad.grpc.demo.dto.PersonDto;
import com.ahmad.grpc.demo.entity.Person;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@NoArgsConstructor
public class PersonServiceImpl implements PersonService{

    @Override
    public PersonGRPC.UserRequest createPerson(PersonDto personDto) {

        return PersonGRPC.UserRequest.newBuilder()
                .setFirstName(personDto.getFirstName())
                .setLastName(personDto.getLastName())
                .setEmail(personDto.getEmail())
                .setDob(personDto.getDob())
                .setPhone(personDto.getPhoneNumber())
                .setUpdatedAt(new Date(System.currentTimeMillis()).toString())
                .build();
    }
}