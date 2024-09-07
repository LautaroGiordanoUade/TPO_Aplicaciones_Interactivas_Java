package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.model.User;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        //TODO: cambiar para usar los constructores?
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setBirthDate(userRegistrationDto.getBirthDate());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());

        //TODO: to be implemented with Mysql
        userRepository.save(user);

        return new UserDto(user.getId(),user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    @Override
    public UserDto loginUser(UserLoginDto userLoginDto) {
        Optional<User> user = userRepository.findByEmail(userLoginDto.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(userLoginDto.getPassword())) {
            return new UserDto(user.get().getId(), user.get().getUsername(), user.get().getEmail(), user.get().getFirstName(), user.get().getLastName());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}