package com.batch.sample.batch;

import com.batch.sample.domain.Dept;
import com.batch.sample.dto.TwoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CsvJob1 {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private int chunkSize = 5;

	@Bean
	public Job csvJob1_batchBuild(){
		return jobBuilderFactory.get("csvJob1")
				.start(csvJob1_batchStep1()).build();
	}

	@Bean
	public Step csvJob1_batchStep1(){
		return stepBuilderFactory.get("csvJob1_batchStep1")
				.<TwoDto,TwoDto>chunk(chunkSize)
				.reader(csvJob1_FileReader())
				.writer(twoDto->twoDto.stream().forEach(i -> {
					log.debug(i.toString());
				}))
				.build();
	}

	@Bean
	public FlatFileItemReader<TwoDto> csvJob1_FileReader(){
		FlatFileItemReader<TwoDto> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new ClassPathResource("/sample/csvJob1_input.csv"));
		flatFileItemReader.setLinesToSkip(1);

		DefaultLineMapper<TwoDto> dtoDefaultLineMapper = new DefaultLineMapper<>();
		// csv 파일 구성 설정
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setNames("one","two");
		delimitedLineTokenizer.setDelimiter(":");
		// 해당 구성을 어떤 타입으로 할건지
		BeanWrapperFieldSetMapper<TwoDto> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(TwoDto.class);

		dtoDefaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		dtoDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		flatFileItemReader.setLineMapper(dtoDefaultLineMapper);

		return flatFileItemReader;
	}
}
