package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;

public interface UserServiceInterface {
    UserDto registerUser(UserRegistrationDto userRegistrationDto);
    UserDto getUserById(Long id) throws Exception;
    long getCurrentUserId=0;
    UserDto getCurrentUser();
    UserDto updateUser(Long id, UserDto userDto) throws Exception;
    User getLoggedUser();
    Boolean isEmailRegistered(String email);
}