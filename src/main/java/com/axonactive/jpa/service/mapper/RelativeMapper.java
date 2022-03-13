package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.controller.request.RelativeRequest;
import com.axonactive.jpa.entities.Relative;
import com.axonactive.jpa.service.dto.RelativeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RelativeMapper {

    RelativeDTO RelativeToRelativeDto(Relative relative);

    List<RelativeDTO> RelativesToRelativeDtos(List<Relative> relativeList);

    Relative RelativeRequestToRelative(RelativeRequest relativeRequest);


}
