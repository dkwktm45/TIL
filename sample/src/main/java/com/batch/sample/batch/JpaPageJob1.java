package com.batch.sample.batch;

import com.batch.sample.domain.Dept;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPageJob1 {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	// 순차적으로 짤라서 가져오기 위한 변수
	// 하지만 배치를 어떻게 짜냐에 따라 이는 달라진다.
	private int chunkSize = 10;

	@Bean
	public Job jpaPageJob1_batchBuild(){
		return jobBuilderFactory.get("jpaPageJob1")
				.start(jpaPageJob1_step1()).build();
	}

	@Bean
	public Step jpaPageJob1_step1(){
		return stepBuilderFactory.get("jpaPageJob1_step1")
				.<Dept,Dept>chunk(chunkSize)
				.reader(jpaPageJob1_dbItemReader())
				.writer(jpaPageJob1_printItemWriter())
				.build();
	}

	@Bean	// reader
	public JpaPagingItemReader<Dept> jpaPageJob1_dbItemReader(){
		return  new JpaPagingItemReaderBuilder<Dept>()
				.name("jpaPageJob1_dbItemReader")
				.entityManagerFactory(entityManagerFactory)
				.pageSize(chunkSize)
				.queryString("SELECT d FROM Dept d order by dept_no").build();
	}

	@Bean //writer
	public ItemWriter<Dept> jpaPageJob1_printItemWriter(){
		return list -> {
			for (Dept dept: list){
				log.debug(dept.toString());
			}
		};
	}
}
