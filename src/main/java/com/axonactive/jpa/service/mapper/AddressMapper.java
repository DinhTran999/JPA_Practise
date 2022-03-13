package com.axonactive.jpa.service.mapper;

import com.axonactive.jpa.entities.Address;
import com.axonactive.jpa.service.dto.AddressDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {

    AddressDTO AddressToAddressDto(Address address);

    List<AddressDTO> AddressToAddressDtos(List<Address> addressList);

    Address AddressDTOToAddress(AddressDTO addressDTO);

}
