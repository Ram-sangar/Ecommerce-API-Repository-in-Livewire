package com.SpringBoot.EcommerceSiteProject.Address.Controller;

import com.SpringBoot.EcommerceSiteProject.Address.Service.AddressService;
import com.SpringBoot.EcommerceSiteProject.Address.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//Logger logger = LoggerFactory.getLogger(AddressController.class);
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresss(){
        List<Address> AddressList =  addressService.getAllAddress();
        return ResponseEntity.ok(AddressList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id){
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @PostMapping
    public Address createAddress(@RequestBody Address address) throws Exception {
        try {
            return addressService.saveAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Address address) throws Exception {

        addressService.updateAddress(id, address);
        return ResponseEntity.ok("Address Saved Successfully");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return ResponseEntity.ok("delete success");
    }



}
