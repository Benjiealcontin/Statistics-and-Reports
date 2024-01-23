package com.statisticandreport.StatisticAndReport.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoMapper {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
