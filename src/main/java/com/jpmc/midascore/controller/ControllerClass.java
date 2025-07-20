package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ControllerClass {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/balance")
    public Balance showUserBalance(@RequestParam long userId){
        UserRecord user = userRepository.findById(userId);

        float balance = (user!=null)? user.getBalance() : 0f;
        return new Balance(balance);

    }
}
