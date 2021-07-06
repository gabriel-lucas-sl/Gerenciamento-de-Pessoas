package com.one.digital.innovation.personapi.service;

import com.one.digital.innovation.personapi.dto.MessageResponseDTO;
import com.one.digital.innovation.personapi.dto.request.PersonDTO;
import com.one.digital.innovation.personapi.entity.Person;
import com.one.digital.innovation.personapi.exception.PersonNotFoundException;
import com.one.digital.innovation.personapi.mapper.PersonMapper;
import com.one.digital.innovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService( PersonRepository personRepository ) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson( PersonDTO personDTO ) {
        Person personToSave = personMapper.toModel( personDTO );
        Person savedPerson = personRepository.save( personToSave );

        return createMessageResponse(savedPerson);
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream()
                    .map(personMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfPersonExists(id);
        return personMapper.toDTO(person);
    }

    public void delete( Long id ) throws  PersonNotFoundException {
        verifyIfPersonExists(id);
        personRepository.deleteById(id);
    }

    public MessageResponseDTO update( Long id, PersonDTO personDTO ) throws PersonNotFoundException {
        verifyIfPersonExists( id );

        Person personToUpdate = personMapper.toModel( personDTO );
        Person savedPerson = personRepository.save( personToUpdate );

        return createMessageResponse(savedPerson);
    }

    private MessageResponseDTO createMessageResponse(Person savedPerson) {
        return MessageResponseDTO
                .builder()
                .message("Person created with ID " + savedPerson.getId())
                .build();
    }

    private Person verifyIfPersonExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
