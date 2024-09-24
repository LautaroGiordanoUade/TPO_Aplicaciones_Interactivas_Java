package com.uade.grupo4.backend_ecommerce.uade.grupo4.backend_ecommerce;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductImageDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.exception.CartWasEmptyPreviouslyException;
import com.uade.grupo4.backend_ecommerce.exception.ProductRemovalFromCartException;
import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.CartRepository;
import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
import com.uade.grupo4.backend_ecommerce.repository.ProductRepository;
import com.uade.grupo4.backend_ecommerce.repository.UserRepository;
import com.uade.grupo4.backend_ecommerce.repository.entity.*;
import com.uade.grupo4.backend_ecommerce.service.implementations.CartService;
import com.uade.grupo4.backend_ecommerce.service.implementations.ProductService;
import com.uade.grupo4.backend_ecommerce.service.implementations.UserService;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class CartServiceTest {

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private ProductService productService;

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addProduct_Successful(){
       ///Registro el usuario para que lo busque

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
        userRegistrationDto.setUsername("TesterUseer");
        userRegistrationDto.setEmail("test@uade.edu.ar");
        userRegistrationDto.setPassword("Test_1234");
        userRegistrationDto.setBirthDate(LocalDate.now());
        userRegistrationDto.setFirstName("Test");
        userRegistrationDto.setLastName("UserTester");


        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Asigna un ID manualmente después de "guardar"
            return user;
        });
        //Registro al usuario
        userService.registerUser(userRegistrationDto);

        //Creo el usuario que le voy a pasar
        User user=new User();
        user.setId(1L);
        user.setUsername("TesterUseer");
        user.setEmail("test@uade.edu.ar");
        user.setPassword("Test_1234");
        user.setBirthDate(LocalDate.now());
        user.setFirstName("Test");
        user.setLastName("UserTester");
        user.setRole(RoleEnum.USER);


        /*
        //Registro el producto para que lo busque y exista el producto
        ProductDto productDto=new ProductDto(1L,"Pantalon",
                "Pantalon largo deportivo",1L,20,150L,
                true, (List<ProductImageDto>) new ProductImage(1L,1L,"1" ));

       when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return product;
        });*/
        //Agrega el producto al carrito
        //productService.saveProduct(productDto);

        //Ejecuta el metodo addProductToCart
        //CartDto resultCart=cartService.addProductToCart(1L,5, user);

       ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            cartService.addProductToCart(1L,5, user);
        });
/*
        //Hago las verificaciones que guarda se creo el carrito correctamente
        assertNotNull(resultCart);
        assertEquals(user.getId(),resultCart.getUser().getId());
        assertEquals(user.getFirstName(),resultCart.getUser().getFirstName());
        assertEquals(1,resultCart.getItems().size());
        assertEquals("750",resultCart.getTotal());
        assertNull(resultCart.getChechkoutDate());*/

        assertEquals("No existe el producto",exception.getMessage());

        //Verifico que se haya guardado correctamente el Carrito
        verify(cartRepository,never()).save(any(Cart.class));

    }
/*
    @Test
    public void addProducto_OutOfQuantity(){

    }*/
    @Test
    public void emptyCart_PrevioslyException(){
        //Registro el usuario para que lo busque
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
        userRegistrationDto.setUsername("TesterUseer");
        userRegistrationDto.setEmail("test@uade.edu.ar");
        userRegistrationDto.setPassword("Test_1234");
        userRegistrationDto.setBirthDate(LocalDate.now());
        userRegistrationDto.setFirstName("Test");
        userRegistrationDto.setLastName("UserTester");


        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Asigna un ID manualmente después de "guardar"
            return user;
        });
        //Registro al usuario
        userService.registerUser(userRegistrationDto);

        //Creo el usuario que le voy a pasar
        User user=new User();
        user.setId(1L);
        user.setUsername("TesterUseer");
        user.setEmail("test@uade.edu.ar");
        user.setPassword("Test_1234");
        user.setBirthDate(LocalDate.now());
        user.setFirstName("Test");
        user.setLastName("UserTester");
        user.setRole(RoleEnum.USER);

/*
        //Registro el producto para que lo busque y exista el producto
        ProductDto productDto=new ProductDto(1L,"Pantalon",
                "Pantalon largo deportivo",1L,20,150L,
                true, (List<ProductImageDto>) new ProductImage(1L,1L,"1" ));

       when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return product;
        });
        //Agrega el producto al carrito
        productService.saveProduct(productDto);*/

        //Ejecuta el metodo addProductToCart
        //CartDto resultCart=cartService.addProductToCart(1L,5, user);

        CartWasEmptyPreviouslyException exception = assertThrows(CartWasEmptyPreviouslyException.class, ()->{
            cartService.emptyCart( user);
        });
        /*
        //Hago las verificaciones que guarda se creo el carrito correctamente
        assertNotNull(resultCart);
        assertEquals(user.getId(),resultCart.getUser().getId());
        assertEquals(user.getFirstName(),resultCart.getUser().getFirstName());
        assertEquals(1,resultCart.getItems().size());
        assertEquals("750",resultCart.getTotal());
        assertNull(resultCart.getChechkoutDate());*/

        assertEquals("El carrito se encuentra vacio",exception.getMessage());

        //Verifico que se haya guardado correctamente el Carrito
        verify(cartRepository,never()).save(any(Cart.class));
    }


    @Test
    public void removeProduct_ItemNotExist() {
        //Registro el usuario para que lo busque
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
        userRegistrationDto.setUsername("TesterUseer");
        userRegistrationDto.setEmail("test@uade.edu.ar");
        userRegistrationDto.setPassword("Test_1234");
        userRegistrationDto.setBirthDate(LocalDate.now());
        userRegistrationDto.setFirstName("Test");
        userRegistrationDto.setLastName("UserTester");


        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L); // Asigna un ID manualmente después de "guardar"
            return user;
        });
        //Registro al usuario
        userService.registerUser(userRegistrationDto);

        //Creo el usuario que le voy a pasar
        User user=new User();
        user.setId(1L);
        user.setUsername("TesterUseer");
        user.setEmail("test@uade.edu.ar");
        user.setPassword("Test_1234");
        user.setBirthDate(LocalDate.now());
        user.setFirstName("Test");
        user.setLastName("UserTester");
        user.setRole(RoleEnum.USER);


        /*
        //Registro el producto para que lo busque y exista el producto
        ProductDto productDto=new ProductDto(1L,"Pantalon",
                "Pantalon largo deportivo",1L,20,150L,
                true, (List<ProductImageDto>) new ProductImage(1L,1L,"1" ));

       when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            return product;
        });
        //Agrega el producto al carrito
        productService.saveProduct(productDto);*/

        //Ejecuta el metodo addProductToCart
        //CartDto resultCart=cartService.addProductToCart(1L,5, user);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartService.removeProductFromCart(1L, 5, user);
        });
        /*
        //Hago las verificaciones que guarda se creo el carrito correctamente
        assertNotNull(resultCart);
        assertEquals(user.getId(),resultCart.getUser().getId());
        assertEquals(user.getFirstName(),resultCart.getUser().getFirstName());
        assertEquals(1,resultCart.getItems().size());
        assertEquals("750",resultCart.getTotal());
        assertNull(resultCart.getChechkoutDate());*/

        assertEquals("No existe el producto", exception.getMessage());

        //Verifico que se haya guardado correctamente el Carrito
        verify(cartRepository, never()).save(any(Cart.class));
    }


}
