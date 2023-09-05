package com.ahmad.grpc.demo.controller;

import com.ahmad.grpc.demo.dto.PersonDto;
import com.ahmad.grpc.demo.service.PersonService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping( value = "api/person")
public class PersonResource{

    private final PersonService personService;
    private static final String HOST = "localhost";
    private static final int PORT = 50051;

    @PostMapping("/")
    public HttpStatus createUser(@RequestBody PersonDto personDto) throws InterruptedException {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        SetterGrpc.SetterStub stub = SetterGrpc.newStub(managedChannel);
        try{
            stub.setUser(personService.createPerson(personDto), null);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        }
        return HttpStatus.NO_CONTENT;
    }
}