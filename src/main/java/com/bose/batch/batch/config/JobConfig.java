package com.bose.batch.batch.config;

import com.bose.batch.batch.dto.BatchData;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class JobConfig {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;


  private ItemProcessor<BatchData, BatchData> processor = t -> {
    t.setData(t.getId() + "data");
    return t;
  };

  @Bean
  public ItemReader<BatchData> reader() {
    List<BatchData> batchDataList = IntStream.range(0, 20)
        .mapToObj(t -> new BatchData(t))
        .collect(Collectors.toList());
    return new ListItemReader<>(batchDataList);
  }

  @Bean
  public ItemWriter<BatchData> writer() {
    return batchDataList -> {
      for (BatchData batchData : batchDataList) {
        System.out.println(batchData.getId() + "--" + batchData.getData());
      }
    };
  }

  @Bean
  public Step step1(ItemReader<BatchData> reader, ItemWriter<BatchData> writer) {
    return stepBuilderFactory.get("step1")
        .<BatchData, BatchData>chunk(5)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant().retryLimit(2).retry(Exception.class)
        .build();
  }

  @Bean
  public Job job(Step step1) {
    return jobBuilderFactory.get("batch-data-test")
        .flow(step1)
        .end()
        .build();
  }

}
