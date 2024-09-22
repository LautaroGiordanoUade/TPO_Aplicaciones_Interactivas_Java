package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import com.uade.grupo4.backend_ecommerce.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setBirthDate(userRegistrationDto.getBirthDate());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());

        userRepository.save(user);

        return new UserDto(user.getId(),user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public UserDto getUserById(Long id) throws Exception {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("An error has happened"));
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),user.getFirstName(),user.getLastName());
    }

    public UserDto updateUser(Long id, UserDto userDto) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        // Solo permitir al admin actualizar los datos del usuario
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        userRepository.save(user);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return currentUser.getId();
        }

        throw new IllegalStateException("User is not authenticated");
    }

    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return UserMapper.toDto(currentUser);
        }

        throw new IllegalStateException("User is not authenticated");
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return currentUser;
        }

        throw new UsernameNotFoundException("User is not authenticated.");
    }
}