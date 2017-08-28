package io.github.mockenize.config;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.domain.ServerEntity;
import io.github.mockenize.rest.domain.MockDetails;
import io.github.mockenize.rest.domain.ServerDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        addMappings(modelMapper);
        return modelMapper;
    }

    private void addMappings(ModelMapper modelMapper) {
        TypeMap<MockDetails, MockEntity> mockDtoMockEntityTypeMap = modelMapper.createTypeMap(MockDetails.class, MockEntity.class);
        mockDtoMockEntityTypeMap.addMappings(m -> m.skip(MockEntity::setId));

        TypeMap<ServerDetails, ServerEntity> serverDetailsServerEntityTypeMap = modelMapper.createTypeMap(ServerDetails.class, ServerEntity.class);
        serverDetailsServerEntityTypeMap.addMappings(m -> {
            m.skip(ServerEntity::setId);
            m.skip(ServerEntity::setStatus);
        });
    }
}
