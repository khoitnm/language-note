package org.tnmk.common.testingmodel.service;

import org.tnmk.common.testingmodel.Person;
import org.tnmk.common.testingmodel.PersonFactory;
import org.tnmk.common.testingmodel.Pet;

public class MockBusinessLogic {
    public Person doSomething(Pet pet){
        pet.getProperties().put("status","updated by doing something.");
        return PersonFactory.createPerson("DoSomethingGuy");
    }
}
