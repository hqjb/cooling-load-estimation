package com.reku.coolingloadestimation.batch;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.entity.EquipmentLoad;
import com.reku.coolingloadestimation.service.IEquipmentLoadService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobParameterExecutionContextCopyListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private IEquipmentLoadService equipmentLoadService;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<EquipmentLoadDTO> equipmentLoadItemFromCSVReader() {
        FlatFileItemReader<EquipmentLoadDTO> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource("/coolingloaddata/equipmentload.csv"));

        DefaultLineMapper<EquipmentLoadDTO> mapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"id", "name", "equipmentLoad"});

        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new EquipmentLoadFieldSetMapper());
        mapper.afterPropertiesSet();

        reader.setLineMapper(mapper);

        return reader;
    }

    @Bean
    public ItemWriter<EquipmentLoadDTO> equipmentLoadItemToDatabaseWriter() {
        return items -> {
            for(EquipmentLoadDTO item: items) {
                equipmentLoadService.save(item);
                System.out.println(String.format("Item %s added successfully to the database.", item.toString()));
            }
        };
    }

    @Bean
    public Step equipmentLoadReadCSVAndWriteToDatabaseStep() {
        return stepBuilderFactory.get("eqLoadReadStep")
                .<EquipmentLoadDTO, EquipmentLoadDTO>chunk(10)
                .reader(equipmentLoadItemFromCSVReader())
                .writer(equipmentLoadItemToDatabaseWriter())
                .build();
    }

    @Bean
    public Job equipmentLoadReadCSVAndWriteToDatabaseJob() {
        return jobBuilderFactory.get("eqLoadReadJob")
                .start(equipmentLoadReadCSVAndWriteToDatabaseStep())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<EquipmentLoadDTO> equipmentLoadItemFromRESTEndpointReader(@Value("#{jobParameters['name']}") String name, @Value("#{jobParameters['eqload']}") Long eqload ){

        IteratorItemReader<EquipmentLoadDTO> reader = new IteratorItemReader<>(List.of(new EquipmentLoadDTO(Long.valueOf(1), name, eqload )));

        return reader;
    }

    @Bean
    public ItemWriter<EquipmentLoadDTO> equipmentLoadItemToDatabaseAndCSVWriter() {

        FlatFileItemWriter<EquipmentLoadDTO> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("/coolingloaddata/equipmentload.csv"));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<EquipmentLoadDTO>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<EquipmentLoadDTO>(){
                    {
                        setNames(new String[] {"id", "name", "equipmentLoad"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean(name="eqLoadProcessJob") // name attribute allows bean to be referenced for autowiring using @Qualifier - refer to controller
    public Job equipmentLoadReadFromRestAndWriteToCSVAndDatabaseJob() {
        return jobBuilderFactory.get("eqLoadProcessJob")
                .incrementer(new RunIdIncrementer()).listener(equipmentLoadReadFromRestAndWriteToCSVAndDatabaseListener()).listener(new JobParameterExecutionContextCopyListener())
                .flow(equipmentLoadReadFromRestAndWriteToCSVAndDatabaseStep()).end().build();
    }

    @Bean
    public Step equipmentLoadReadFromRestAndWriteToCSVAndDatabaseStep() {

        return stepBuilderFactory.get("eqLoadProcessStep").<EquipmentLoadDTO, EquipmentLoadDTO> chunk(1)
                .reader(equipmentLoadItemFromRESTEndpointReader("", Long.valueOf(0)))
                .writer(equipmentLoadItemToDatabaseAndCSVWriter())
                .allowStartIfComplete(true).build();
    }

    @Bean
    public JobExecutionListener equipmentLoadReadFromRestAndWriteToCSVAndDatabaseListener() {
        return new JobCompletionListener();
    }
}
