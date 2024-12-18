package com.purchasing.health.api;

import java.util.List;

import com.purchasing.health.models.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purchasing.health.repositories.UserGroupRepository;
import com.purchasing.health.repositories.UserRepository;
@RestController
@RequestMapping("api/user/")
public class Users {
    
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("usergroups")
    public List<UserGroup> getUserGroups(){
        return userGroupRepository.findAll();
    }

    @GetMapping("userlist")
    public Iterable<com.purchasing.health.models.Users> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("useradd")
    public com.purchasing.health.models.Users addUser(){
        UserGroup userGroup=userGroupRepository.findById(Long.parseLong("1"));
        com.purchasing.health.models.Users user=new com.purchasing.health.models.Users(
            "Tite Nguangu", 
            "titensay@gmail.com", 
            "titensay", 
            userGroup);
            
            return userRepository.save(user);
    }
}

