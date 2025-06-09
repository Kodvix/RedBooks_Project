package com.org.kodvix.redbooks.config;

import com.org.kodvix.redbooks.mapper.BookMapper;
import com.org.kodvix.redbooks.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.org.kodvix.redbooks.mapper.PublisherMapper;

@Configuration
public class MapperConfig {
    //private final PublisherRepository publisherRepository;
    @Bean
    public PublisherMapper publisherMapper() {
        return new PublisherMapper();
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
//    public MapperConfig(PublisherRepository publisherRepository) {
//        this.publisherRepository = publisherRepository;
//    }
//    @Bean
//    public BookMapper bookMapper() {
//        return new BookMapper(publisherRepository);
//    }
}
