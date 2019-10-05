package com.example.lagomfurniture.service.userservice;


import com.example.lagomfurniture.model.User;
import com.example.lagomfurniture.repository.UserRepository;
import com.example.lagomfurniture.utils.UserPasswordHashClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordHashClass userPasswordHashClass;

    public void registerSave(User user) {
        String hashedPassword = userPasswordHashClass.getSHA256(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public User loginRead(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        System.out.println("service user : " + user);
        return user;
    }
}
