package com.reku.coolingloadestimation.batch;

import com.reku.coolingloadestimation.dto.EquipmentLoadDTO;
import com.reku.coolingloadestimation.entity.EquipmentLoad;
import com.reku.coolingloadestimation.service.IEquipmentLoadService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

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
    public FlatFileItemReader<EquipmentLoadDTO> equipmentLoadItemReader() {
        FlatFileItemReader<EquipmentLoadDTO> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("/data/equipmentload.csv"));

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
    public ItemWriter<EquipmentLoadDTO> equipmentLoadItemWriter() {
        return items -> {
            for(EquipmentLoadDTO item: items) {
                equipmentLoadService.save(item);
                System.out.println(String.format("Item %s added successfully to the database.", item.toString()));
            }
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<EquipmentLoadDTO, EquipmentLoadDTO>chunk(10)
                .reader(equipmentLoadItemReader())
                .writer(equipmentLoadItemWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }
}
