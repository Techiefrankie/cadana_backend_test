package com.cadana.cadanapay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Person {
    private String id;
    private String personName;
    private Salary salary;

    // NB: moved the specified Person's methods to DataManipulationService class where I created an instance of list or
    // person, as creating the methods within this class would make operations to apply to the current instance of Person
    // as opposed to list of Person.
}
