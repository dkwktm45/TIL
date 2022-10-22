package com.batch.sample.batch;

import com.batch.sample.dto.CoinDto;
import com.batch.sample.dto.TwoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JsonJob1 {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private int chunkSize = 5;

	@Bean
	public Job jsonJob1_batchBuild(){
		return jobBuilderFactory.get("jsonJob1")
				.start(jsonJob1_batchStep1()).build();
	}

	@Bean
	public Step jsonJob1_batchStep1(){
		return stepBuilderFactory.get("jsonJob1_batchStep1")
				.<CoinDto,CoinDto>chunk(chunkSize)
				.reader(jsonJob1_jsonReader())
				.writer(coinDto -> coinDto.stream().forEach(coinDto1 -> {
					log.debug(coinDto1.toString());
				}))
				.build();
	}

	@Bean
	public JsonItemReader<CoinDto> jsonJob1_jsonReader(){
		return new JsonItemReaderBuilder<CoinDto>()
				.jsonObjectReader(new JacksonJsonObjectReader<>(CoinDto.class))
				.resource(new ClassPathResource("sample/json1_input.json"))
				.name("jsonJob1_jsonReader")
				.build();
	}
}
