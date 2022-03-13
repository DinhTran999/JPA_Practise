package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.entities.Address;
import com.axonactive.jpa.enumerate.AddressType;
import com.axonactive.jpa.service.AddressService;
import com.axonactive.jpa.service.EmployeeService;
import com.axonactive.jpa.service.dto.AddressDTO;
import com.axonactive.jpa.service.mapper.AddressMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.axonactive.jpa.constant.Constant.EMPLOYEE_ID_PARAMETER_NAME_SQL;


@RequestScoped
@Transactional
public class AddressServiceImpl implements AddressService {

    @PersistenceContext(unitName = "jpa")
    EntityManager em;

    @Inject
    EmployeeService employeeService;

    @Inject
    AddressMapper addressMapper;

    @Override
    public List<AddressDTO> getAllAddressByEmployeeId(int employeeId) {
        return addressMapper.AddressToAddressDtos(getAllAddressByEmployeeIdFromDataBase(employeeId));
    }

    @Override
    public AddressDTO addAddress(int employeeId, AddressDTO addressDTO) {
        Address address = addressMapper.AddressDTOToAddress(addressDTO);
        address.setEmployee(employeeService.getEmployeeByIdFromDataBase(employeeId));
        em.persist(address);
        return addressMapper.AddressToAddressDto(address);
    }

    @Override
    public List<AddressDTO> getAddressesByEmployeeIdAndAddressType(int employeeId, AddressType addressType) {
        List<Address> addressByTypeList = getAddressesByEmployeeIdAndAddressTypeFromDataBase(employeeId, addressType);
        return addressMapper.AddressToAddressDtos(addressByTypeList);
    }

    @Override
    public void deleteAddressByEmployeeIdAndAddressId(int employeeId, int addressId) {
        List<Address> addressList = getAllAddressByEmployeeIdFromDataBase(employeeId);
        Optional <Address> address = addressList.stream()
                                    .filter(a -> a.getId() == addressId).findFirst();
        address.ifPresent(a->em.remove(a));
    }

    @Override
    public AddressDTO updateAddressByEmployeeIdAndAddressId(int employeeId, int addressId, AddressDTO addressDTO) {
        Address address = getAddressByEmployeeIdAndAddressIdFromDataBase(employeeId, addressId);
        address.setDistrictLevel(addressDTO.getDistrictLevel());
        address.setProvinceLevel(addressDTO.getProvinceLevel());
        address.setAddressType(addressDTO.getAddressType());
        return addressMapper.AddressToAddressDto(em.merge(address));
    }

    @Override
    public AddressDTO getAddressByEmployeeIdAndAddressId(int employeeId, int addressId) {
        return addressMapper.AddressToAddressDto(getAddressByEmployeeIdAndAddressIdFromDataBase(employeeId,addressId));
    }

    private Address getAddressByEmployeeIdAndAddressIdFromDataBase(int employeeId, int addressId){
        TypedQuery<Address> namedQuery = em.createNamedQuery(Address.GET_ADDRESS_BY_EMPLOYEE_ID_AND_ADDRESS_ID, Address.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL,employeeId);
        namedQuery.setParameter("addressId",addressId);
        return namedQuery.getSingleResult();
    }

    private List<Address> getAllAddressByEmployeeIdFromDataBase(int employeeId) {
        TypedQuery<Address> namedQuery = em.createNamedQuery(Address.GET_ALL_ADDRESS_BY_EMPLOYEE_ID, Address.class);
        namedQuery.setParameter(EMPLOYEE_ID_PARAMETER_NAME_SQL, employeeId);
        return namedQuery.getResultList();
    }

    private List<Address> getAddressesByEmployeeIdAndAddressTypeFromDataBase(int employeeId, AddressType addressType) {
        List<Address> addressList = getAllAddressByEmployeeIdFromDataBase(employeeId);
        return addressList.stream()
                .filter(a -> a.getAddressType().equals(addressType))
                .collect(Collectors.toList());
    }


}
