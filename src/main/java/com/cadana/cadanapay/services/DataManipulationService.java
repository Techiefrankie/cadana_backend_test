package com.cadana.cadanapay.services;

import com.cadana.cadanapay.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataManipulationService {

    List<Person> people;

    public DataManipulationService() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            people = List.of(mapper.readValue(getClass().getResource("/api/person.json"), Person[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> sortBySalaryDesc() {
        return people.stream()
                .sorted(Comparator.comparing(person -> person.getSalary().getValue(), Comparator.reverseOrder()))
                .toList();
    }

    public List<Person> sortBySalaryAsc() {
        return people.stream()
                .sorted(Comparator.comparing(person -> person.getSalary().getValue()))
                .toList();
    }

    public List<Person> filterBySalaryCurrency(String currency) {
        return people.stream()
                .filter(person -> person.getSalary().getCurrency().equals(currency))
                .toList();
    }

    public Map<String, List<Person>> groupBySalaryCurrency() {
        return people.stream()
                .collect(Collectors.groupingBy(person -> person.getSalary().getCurrency()));
    }
}
