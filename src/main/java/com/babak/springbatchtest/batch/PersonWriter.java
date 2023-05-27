package com.babak.springbatchtest.batch;

import com.babak.springbatchtest.domain.Person;
import com.babak.springbatchtest.repository.PersonRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PersonWriter implements ItemWriter<Person> {

    final private PersonRepository repository;

    public PersonWriter(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(Chunk<? extends Person> chunk) throws Exception {
        repository.saveAll(chunk);
    }
}
