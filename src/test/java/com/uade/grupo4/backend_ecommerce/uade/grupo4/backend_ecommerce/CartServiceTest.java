package com.uade.grupo4.backend_ecommerce.uade.grupo4.backend_ecommerce;

import com.uade.grupo4.backend_ecommerce.controller.dto.CartDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.ProductImageDto;
import com.uade.grupo4.backend_ecommerce.controller.dto.UserRegistrationDto;
import com.uade.grupo4.backend_ecommerce.exception.CartWasEmptyPreviouslyException;
import com.uade.grupo4.backend_ecommerce.exception.ProductRemovalFromCartException;
import com.uade.grupo4.backend_ecommerce.exception.ResourceNotFoundException;
import com.uade.grupo4.backend_ecommerce.exception.ValidationException;
import com.uade.grupo4.backend_ecommerce.repository.*;
import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

/*
    @Test
    public void addProduct_Successful() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        User user = mock(User.class);
        Product product = mock(Product.class);
        Cart cart = mock(Cart.class);
        CartItem cartItem = mock(CartItem.class);
        Category category = mock(Category.class);
        when(cart.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);


        when(category.getId()).thenReturn(1L);
        //when(savedProduct.getCategory()).thenReturn(category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserAndCheckoutDate(user,null)).thenReturn(Optional.of(cart));
        when(product.getQuantity()).thenReturn(20);
        //when(cartRepository.save(any(Product.class))).thenReturn(product);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cart.getItems()).thenReturn((List<CartItem>) cartItem);
        when(cartItem.getQuantity()).thenReturn(1);

        //Ejecuta el metodo addProductToCart
        CartDto resultCart=cartService.addProductToCart(1L,5, user);

        //Hago las verificaciones que guarda se creo el carrito correctamente
        assertNotNull(resultCart);
        assertEquals(user.getId(),resultCart.getUser().getId());
        assertEquals(user.getFirstName(),resultCart.getUser().getFirstName());
        assertEquals(1,resultCart.getItems().size());
        assertEquals("750",resultCart.getTotal());
        assertNull(resultCart.getChechkoutDate());

        //Verifico que se haya guardado correctamente el Carrito
        verify(cartRepository,never()).save(any(Cart.class));

    }

    @Test
    public void removeProduct_Successful(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        User user = mock(User.class);
        Product product = mock(Product.class);
        Cart cart = mock(Cart.class);
        CartItem cartItem = mock(CartItem.class);
        Category category = mock(Category.class);
        when(cart.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);


        when(category.getId()).thenReturn(1L);
        //when(savedProduct.getCategory()).thenReturn(category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserAndCheckoutDate(user,null)).thenReturn(Optional.of(cart));
        when(product.getQuantity()).thenReturn(20);
        //when(cartRepository.save(any(Product.class))).thenReturn(product);
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);
        when(cart.getItems()).thenReturn((List<CartItem>) cartItem);
        when(cartItem.getQuantity()).thenReturn(1);

        //Ejecuta el metodo addProductToCart
        CartDto resultCart=cartService.removeProductFromCart(1L,5, user);

        //Hago las verificaciones que guarda se creo el carrito correctamente
        assertNotNull(resultCart);
        assertEquals(user.getId(),resultCart.getUser().getId());
        assertEquals(user.getFirstName(),resultCart.getUser().getFirstName());
        assertEquals(1,resultCart.getItems().size());
        assertEquals("750",resultCart.getTotal());
        assertNull(resultCart.getChechkoutDate());

        //Verifico que se haya guardado correctamente el Carrito
        verify(cartRepository,never()).save(any(Cart.class));

    }
*/

}
