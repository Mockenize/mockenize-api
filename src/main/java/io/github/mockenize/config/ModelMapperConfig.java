package io.github.mockenize.config;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.rest.domain.MockDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        registerMockDtoToMockEntityTypeMap(modelMapper);
        return modelMapper;
    }

    private void registerMockDtoToMockEntityTypeMap(ModelMapper modelMapper) {
        TypeMap<MockDto, MockEntity> mockDtoMockEntityTypeMap = modelMapper.createTypeMap(MockDto.class, MockEntity.class);
        mockDtoMockEntityTypeMap.addMappings(m -> m.skip(MockEntity::setId));
    }
}
