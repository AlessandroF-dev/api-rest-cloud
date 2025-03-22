package br.com.cloud.rest.api.service;

import br.com.cloud.rest.api.domain.dto.UserRequestDTO;
import br.com.cloud.rest.api.domain.dto.UserResponseDTO;
import br.com.cloud.rest.api.domain.exception.DuplicateDataException;
import br.com.cloud.rest.api.domain.exception.ResultDataNotFoundException;
import br.com.cloud.rest.api.domain.model.*;
import br.com.cloud.rest.api.domain.repository.UserRepository;
import br.com.cloud.rest.api.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final String ACCOUNT_NUMBER = "001";
    private static final String USER_NAME = "Alessandro Fernandes";

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(USER_NAME);
        userRequestDTO.setAccount(new Account("12345", ACCOUNT_NUMBER));
        userRequestDTO.setCard(new Card("98765", new BigDecimal(5000)));

        userRequestDTO.setNews(
                Arrays.asList(
                        new News("Url Icon", "News Description"),
                        new News("Url Icon 2", "News Description 2")
                )
        );

        userRequestDTO.setFeatures(
                Arrays.asList(
                        new Feature("Url Icon", "Feature Description"),
                        new Feature("Url Icon 2", "Feature Description 2")
                )
        );

        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setName(USER_NAME);

        user = new User();
        user.setName(USER_NAME);

    }

    @Test
    void shouldFoundUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(USER_NAME, result.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(mapper, times(1)).map(user, UserResponseDTO.class);
    }

    @Test
    void shouldNotFoundUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResultDataNotFoundException.class, () -> userService.findById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldFoundUserByEmailAndThrowsException() {
        when(userRepository.existsByAccountNumber(ACCOUNT_NUMBER)).thenReturn(true);

        assertThrows(DuplicateDataException.class, () -> userService.create(userRequestDTO));
        verify(userRepository, times(1)).existsByAccountNumber(ACCOUNT_NUMBER);
    }

    @Test
    void shouldCreateNewUser() {
        when(userRepository.existsByAccountNumber(ACCOUNT_NUMBER)).thenReturn(false);
        when(mapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.create(userRequestDTO);

        assertNotNull(result);
        assertEquals(USER_NAME, result.getName());
        verify(userRepository, times(1)).existsByAccountNumber(ACCOUNT_NUMBER);
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).map(userRequestDTO, User.class);
        verify(mapper, times(1)).map(user, UserResponseDTO.class);
    }

    @Test
    void shouldNotFoundUserToUpdateAndThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResultDataNotFoundException.class, () -> userService.update(1L, userRequestDTO));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        UserResponseDTO result = userService.update(1L, userRequestDTO);

        assertNotNull(result);
        assertEquals(USER_NAME, result.getName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(mapper, times(1)).map(user, UserResponseDTO.class);
    }
}
