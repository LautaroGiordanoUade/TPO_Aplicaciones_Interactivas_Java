package com.uade.grupo4.backend_ecommerce.service.interfaces;

import com.uade.grupo4.backend_ecommerce.controller.Dtos.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.controller.Dtos.UserLoginDto;
import com.uade.grupo4.backend_ecommerce.controller.Dtos.UserDto;

public interface UserServiceInterface {
    UserDto registerUser(UserRegistrationDto userRegistrationDto);
    UserDto loginUser(UserLoginDto userLoginDto);
}