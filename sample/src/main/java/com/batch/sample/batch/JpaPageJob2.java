package com.batch.sample.batch;

import com.batch.sample.domain.Dept;
import com.batch.sample.domain.Dept2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPageJob2 {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	private int chunkSize = 10;

	@Bean
	public Job jpaPageJob2_batchBuild(){
		return jobBuilderFactory.get("jpaPageJob2")
				.start(jpaPageJob2_step1()).build();
	}

	@Bean
	public Step jpaPageJob2_step1(){
		return stepBuilderFactory.get("jpaPageJob_step1")
				.<Dept,Dept2>chunk(chunkSize)
				.reader(jpaPageJob2_dbItemReader())
				.processor(jpaPageJob2_processor())// dept1 에있는 데이터를 dpet2로 이동시킨다.
				.writer(jpaPageJob2_dbItemWriter())
				.build();
	}

	private ItemProcessor<Dept, Dept2> jpaPageJob2_processor() {
		return dept->{
			return new Dept2(dept.getDeptNo(),"new_"+dept.getDName(), "new_"+dept.getLoc());
		};
	}

	@Bean	// reader (종류가 엄청 많다._참고)
	public JpaPagingItemReader<Dept> jpaPageJob2_dbItemReader(){
		return  new JpaPagingItemReaderBuilder<Dept>()
				.name("jpaPageJob2_dbItemReader")
				.entityManagerFactory(entityManagerFactory)
				.pageSize(chunkSize)
				.queryString("SELECT d FROM Dept d order by dept_no").build();
	}

	@Bean //writer
	public JpaItemWriter<Dept2> jpaPageJob2_dbItemWriter(){
		JpaItemWriter<Dept2> jpaItemWriter = new JpaItemWriter<>();
		jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaItemWriter;
	}
}
