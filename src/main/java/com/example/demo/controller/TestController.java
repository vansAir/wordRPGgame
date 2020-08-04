package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.holder.UserHolder;
import com.example.demo.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user")
    public Result getUsers(){
        return Result.success(UserHolder.users);
    }

    @PostMapping("/update")
    public void update(User user){
        UserHolder.users.put(user.getId(),user);
    }


    @PostMapping("/add")
    public void add(User user){
        UserHolder.users.put(user.getId(),user);
    }

    @PostMapping("/del")
    public void del(User user){
        UserHolder.users.remove(user.getId());
    }


}
