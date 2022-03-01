package com.axonactive.jpa.service;

import com.axonactive.jpa.controller.request.RelativeRequest;
import com.axonactive.jpa.service.dto.RelativeDTO;

import java.util.List;

public interface RelativeService {
    List<RelativeDTO> getAllRelativeByEmployeeId(int employeeID);

    RelativeDTO getRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId);

    RelativeDTO addRelativeByEmployeeId(int employeeId, RelativeRequest relativeRequest);

    void deleteRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId);

    RelativeDTO updateRelativeByEmployeeIdAndRelativeId(int employeeId, int relativeId, RelativeRequest relativeRequest);
}
