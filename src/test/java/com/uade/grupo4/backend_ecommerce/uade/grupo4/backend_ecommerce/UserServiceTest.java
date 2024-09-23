package com.uade.grupo4.backend_ecommerce.uade.grupo4.backend_ecommerce;

import com.uade.grupo4.backend_ecommerce.controller.dto.UserDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.User;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUser_Successful() {
        //no existe un usuario con el mismo email o username
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Crea un DTO de registro con todos los datos requeridos
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUsername("testuser");
        registrationDto.setEmail("test@example.com");
        registrationDto.setPassword("password123");
        registrationDto.setFirstName("Test");
        registrationDto.setLastName("User");
        registrationDto.setBirthDate(LocalDate.now());

        // Simula el comportamiento de guardar el usuario y asignar un ID
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Asigna un ID manualmente después de "guardar"
            return user;
        });

        // Ejecuta el método registerUser del servicio
        UserDto result = userService.registerUser(registrationDto);

        // Verifica que el usuario se creó correctamente y el ID no es nulo
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());

        // Verifica que se haya guardado correctamente el usuario
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void registerUser_EmailAlreadyExists() {
        // Simular que ya existe un usuario con el mismo email
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Crear un objeto DTO para el registro
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUsername("testuser");
        registrationDto.setEmail("test@example.com");
        registrationDto.setPassword("password123");
        registrationDto.setFirstName("Test");
        registrationDto.setLastName("User");
        registrationDto.setBirthDate(LocalDate.now());

        // Ejecutar el registro y esperar una excepción
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("The email is already used.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class)); //verifica que no haya cambios, luego de correr los test
    }

    @Test
    public void registerUser_UsernameAlreadyExists() {
        // Simula que ya existe un usuario con el mismo username
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setUsername("testuser");
        registrationDto.setEmail("test@example.com");
        registrationDto.setPassword("password123");
        registrationDto.setFirstName("Test");
        registrationDto.setLastName("User");
        registrationDto.setBirthDate(LocalDate.now());


        //esperar una excepción
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("The username is already taken.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void registerUser_MissingData() {
        // Crear un objeto DTO sin todos los campos necesarios
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("test@example.com"); // Falta username, password, etc.

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(registrationDto);
        });

        assertEquals("There is missed user basic data.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
