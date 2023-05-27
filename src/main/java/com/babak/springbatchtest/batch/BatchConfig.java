package com.babak.springbatchtest.batch;

import com.babak.springbatchtest.domain.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    final private PersonReader reader;
    final private PersonProcessor processor;
    final private PersonWriter writer;

    public BatchConfig(PersonReader reader, PersonProcessor processor, PersonWriter writer) {
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Step step(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("PersonStep", repository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("PersonJob", repository)
                .incrementer(new RunIdIncrementer())
                .flow(step(repository, transactionManager))
                .end()
                .build();
    }
}
