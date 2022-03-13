package com.axonactive.jpa.service;

import com.axonactive.jpa.enumerate.AddressType;
import com.axonactive.jpa.service.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddressByEmployeeId(int employeeId);

    AddressDTO addAddress(int employeeId, AddressDTO addressDTO);

    List<AddressDTO> getAddressesByEmployeeIdAndAddressType(int employeeId, AddressType addressType);

    void deleteAddressByEmployeeIdAndAddressId(int employeeId, int addressId);

    AddressDTO updateAddressByEmployeeIdAndAddressId(int employeeId, int addressId, AddressDTO addressDTO);

    AddressDTO getAddressByEmployeeIdAndAddressId(int employeeId, int addressId);
}
