package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.entities.DepartmentLocation;
import com.axonactive.jpa.service.dto.DepartmentLocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DepartmentLocationMapper {
    DepartmentLocationMapper INSTANCE = Mappers.getMapper(DepartmentLocationMapper.class );

    DepartmentLocationDTO DepartmentLocationToDepartmentLocationDto(DepartmentLocation departmentLocation);
    List<DepartmentLocationDTO> DepartmentLocationsToDepartmentLocationDtos (List<DepartmentLocation> departmentLocations);

}
