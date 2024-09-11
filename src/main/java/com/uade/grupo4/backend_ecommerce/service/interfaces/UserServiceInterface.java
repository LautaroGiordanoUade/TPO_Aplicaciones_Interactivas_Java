package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserLoginDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;

public interface UserServiceInterface {
    UserDto registerUser(UserRegistrationDto userRegistrationDto);
    UserDto loginUser(UserLoginDto userLoginDto);
}