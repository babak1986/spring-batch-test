package com.babak.springbatchtest.batch;

import com.babak.springbatchtest.domain.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@StepScope
public class PersonProcessor implements ItemProcessor<Person, Person> {

    Set<Person> people = new HashSet<>();

    @Override
    public Person process(Person item) throws Exception {
        if (people.contains(item)) {
            return null;
        }
        return item;
    }
}
