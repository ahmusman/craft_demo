package com.ahmad.grpc.demo.service;

import com.ahmad.grpc.demo.controller.PersonGRPC;
import com.ahmad.grpc.demo.dto.PersonDto;
import com.ahmad.grpc.demo.entity.Person;

public interface PersonService {

    PersonGRPC.UserRequest createPerson(PersonDto personDto);

}
