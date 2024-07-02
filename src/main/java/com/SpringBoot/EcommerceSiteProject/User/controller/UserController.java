package com.SpringBoot.EcommerceSiteProject.User.controller;

import com.SpringBoot.EcommerceSiteProject.User.model.LoginDTO;
import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.SpringBoot.EcommerceSiteProject.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String testApi(){
        return "TestApi works.";
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        System.out.println(user);
        return ResponseEntity.ok().body(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
    return userService.authenticate(loginDTO);
    }



    /*
    @PutMapping("/{id}/{role}")
    public ResponseEntity<User> updateRole(@PathVariable int id, @PathVariable String role){
        return userservice.update(id, role);
    }*

     */
}
