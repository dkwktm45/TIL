package com.batch.sample.dto;

import lombok.*;

@Getter @Setter
@ToString @NoArgsConstructor
@AllArgsConstructor
public class CoinDto {
	String market;
	String korean_name;
	String english_name;
}

