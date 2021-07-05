package com.one.digital.innovation.personapi.service;

import com.one.digital.innovation.personapi.dto.MessageResponseDTO;
import com.one.digital.innovation.personapi.entity.Person;
import com.one.digital.innovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(Person person) {
        Person savedPerson = personRepository.save(person);
        return MessageResponseDTO
                .builder()
                .message("Person created with ID " + savedPerson.getId())
                .build();
    }
}
