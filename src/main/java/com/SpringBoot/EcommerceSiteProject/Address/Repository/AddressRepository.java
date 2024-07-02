package com.SpringBoot.EcommerceSiteProject.Address.Repository;

import com.SpringBoot.EcommerceSiteProject.Address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
