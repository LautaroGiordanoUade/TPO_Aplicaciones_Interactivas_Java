package com.uade.grupo4.backend_ecommerce.service.implementations;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.repository.mapper.UserMapper;
import com.uade.grupo4.backend_ecommerce.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {

        if (userRegistrationDto.getUsername() == null || userRegistrationDto.getUsername().isEmpty() ||
                userRegistrationDto.getEmail() == null || userRegistrationDto.getEmail().isEmpty() ||
                userRegistrationDto.getPassword() == null || userRegistrationDto.getPassword().isEmpty() ||
                userRegistrationDto.getBirthDate() == null ||
                userRegistrationDto.getFirstName() == null || userRegistrationDto.getFirstName().isEmpty() ||
                userRegistrationDto.getLastName() == null || userRegistrationDto.getLastName().isEmpty()) {

            throw new ValidationException("There is missed user basic data.");
        }

        if (userRepository.findByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new ValidationException("The email is already used.");
        }

        if (!isValidEmail(userRegistrationDto.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

        // maximo de edad 100 años
        if (isOlderThan(userRegistrationDto.getBirthDate())) {
            throw new ValidationException("User cannot be older than 100 years.");
        }


        if (userRepository.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new ValidationException("The username is already taken.");
        }

        User user = new User();

        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setBirthDate(userRegistrationDto.getBirthDate());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setRole(RoleEnum.USER); // siempre van a ser USER, se les cambia a admin por db, a excepcion del primer

        userRepository.save(user);

        return new UserDto(user.getId(),user.getUserName(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public UserDto getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("An error has happened"));
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(),user.getFirstName(),user.getLastName());
    }

    public UserDto updateUser(Long id, UserDto userDto) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        // ESta validacion es para solo los campos que no son nulos o vacíos en el UserDto recibido
        if (userDto.getUsername() != null && !userDto.getUsername().isEmpty()) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
            user.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
            user.setLastName(userDto.getLastName());
        }


        // Solo permitir al admin actualizar los datos del usuario
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        userRepository.save(user);
        return new UserDto(user.getId(), user.getUserName(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    public long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User currentUser) {
            return currentUser.getId();
        }

        throw new IllegalStateException("User is not authenticated");
    }

    //TODO: this and next one will be refactor to have only 1, which will return User class
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

        return null;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Valida si la persona es mayor de 100 años (seteable)
    private boolean isOlderThan(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears() > 100;
    }
}