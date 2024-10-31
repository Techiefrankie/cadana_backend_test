package com.cadana.cadanapay.services;

import com.cadana.cadanapay.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManipulationServiceTest {
    private DataManipulationService service;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        service = new DataManipulationService();
    }

    @Test
    public void test_filterBySalaryCurrency_USD() throws IOException {
        List<Person> expected = List.of(mapper.readValue(getClass().getResource("/model/filter_by_salary_currency_usd.json"), Person[].class));
        List<Person> actual = service.filterBySalaryCurrency("USD");

        assertEquals(mapper.readTree(mapper.writeValueAsString(expected)), mapper.readTree(mapper.writeValueAsString(actual)));
    }

    @Test
    public void test_groupBySalaryCurrency() throws IOException {
        Map<String, List<Person>>  expected = mapper.readValue(getClass().getResource("/model/group_by_salary_currency.json"), Map.class);
        Map<String, List<Person>> actual = service.groupBySalaryCurrency();

        assertEquals(mapper.readTree(mapper.writeValueAsString(expected)), mapper.readTree(mapper.writeValueAsString(actual)));
    }

    @Test
    public void test_sortBySalary_Asc() throws IOException {
        List<Person> expected = List.of(mapper.readValue(getClass().getResource("/model/sort_by_salary_asc.json"), Person[].class));
        List<Person> actual = service.sortBySalaryAsc();

        assertEquals(mapper.readTree(mapper.writeValueAsString(expected)), mapper.readTree(mapper.writeValueAsString(actual)));
    }

    @Test
    public void test_sortBySalary_Desc() throws IOException {
        List<Person> expected = List.of(mapper.readValue(getClass().getResource("/model/sort_by_salary_desc.json"), Person[].class));
        List<Person> actual = service.sortBySalaryDesc();

        assertEquals(mapper.readTree(mapper.writeValueAsString(expected)), mapper.readTree(mapper.writeValueAsString(actual)));
    }
}
