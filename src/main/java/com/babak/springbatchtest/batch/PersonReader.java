package com.babak.springbatchtest.batch;

import com.babak.springbatchtest.domain.Person;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PersonReader implements ItemReader<Person> {

    @Value("#{jobParameters[filePath]}")
    private String filePath;
    private FlatFileItemReader<Person> reader;

    public PersonReader() {
        this.reader = new FlatFileItemReaderBuilder<Person>()
                .name("PersonReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names(new String[]{"firstName", "lastName", "age"})
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Override
    public Person read() throws Exception {
        reader.open(new ExecutionContext());
        return reader.read();
    }
}
