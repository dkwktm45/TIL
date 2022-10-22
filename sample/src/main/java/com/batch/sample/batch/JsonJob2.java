package com.batch.sample.batch;

import com.batch.sample.dto.CoinDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JsonJob2 {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private int chunkSize = 5;

	@Bean
	public Job jsonJob2_batchBuild(){
		return jobBuilderFactory.get("jsonJob2")
				.start(jsonJob2_batchStep1()).build();
	}

	@Bean
	public Step jsonJob2_batchStep1(){
		return stepBuilderFactory.get("jsonJob2_batchStep1")
				.<CoinDto,CoinDto>chunk(chunkSize)
				.reader(jsonJob2_jsonReader())
				.processor(jsonJob2_processor())
				.writer(jsonJob2_jsonWriter())
				.build();
	}

	private ItemProcessor<CoinDto,CoinDto> jsonJob2_processor() {
		return coinDto -> {
			if(coinDto.getMarket().startsWith("KRW-")){
				return new CoinDto(coinDto.getMarket(),coinDto.getKorean_name(), coinDto.getEnglish_name());
			}else {
				return null;
			}
		};
	}

	@Bean
	public JsonItemReader<CoinDto> jsonJob2_jsonReader(){
		return new JsonItemReaderBuilder<CoinDto>()
				.jsonObjectReader(new JacksonJsonObjectReader<>(CoinDto.class))
				.resource(new ClassPathResource("sample/json1_input.json"))
				.name("jsonJob2_jsonReader")
				.build();
	}
	@Bean
	public JsonFileItemWriter<CoinDto> jsonJob2_jsonWriter(){
		return new JsonFileItemWriterBuilder<CoinDto>()
				.jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
				.resource(new FileSystemResource("output/json2_output.json"))
				.name("jsonJob2_jsonWriter").build();
	}
}
