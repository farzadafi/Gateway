package com.example.gateway.mapper;

import com.example.gateway.dto.UrlPathDto;
import com.example.gateway.model.UrlPath;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlPathMapper {

    UrlPathMapper INSTANCE = Mappers.getMapper(UrlPathMapper.class);

    UrlPath dtoToModel(UrlPathDto urlPathDto);
}
