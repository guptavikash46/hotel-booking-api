package com.vikas.hotelbookingsystemrestapi.service;

import com.vikas.hotelbookingsystemrestapi.entity.User;
import com.vikas.hotelbookingsystemrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public int save(User user) {
        Optional<User> guest = Optional.of(user);
        if (!guest.isEmpty() && user.getHotel() != null) {
            User usr = userRepository.save(user);
            return usr != null ? 1: 0;
        }
        return 0;
    }
}
