package br.com.cloud.rest.api.controller;

import br.com.cloud.rest.api.domain.dto.UserRequestDTO;
import br.com.cloud.rest.api.domain.dto.UserResponseDTO;
import br.com.cloud.rest.api.domain.model.Account;
import br.com.cloud.rest.api.domain.model.Card;
import br.com.cloud.rest.api.domain.model.Feature;
import br.com.cloud.rest.api.domain.model.News;
import br.com.cloud.rest.api.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "Alessandro Fernandes";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static MockMvc mockMvc;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName(USER_NAME);
        userRequestDTO.setAccount(new Account("12345", "001"));
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
        userResponseDTO.setId(USER_ID);
        userResponseDTO.setName(USER_NAME);
    }

    @Test
    void shouldReturnUserWhenFindById() {
        when(userService.findById(USER_ID)).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.findById(USER_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_NAME, response.getBody().getName());
        verify(userService, times(1)).findById(USER_ID);
    }

    @Test
    void shouldReturnCreatedWhenCreateNewUser() throws Exception {
        when(userService.create(userRequestDTO)).thenReturn(userResponseDTO);

        MvcResult mvcResult = performPostRequest("/users", userRequestDTO);

        verify(userService, times(1)).create(userRequestDTO);

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());

        String locationHeader = mvcResult.getResponse().getHeader("Location");
        assertNotNull(locationHeader);
        assertTrue(locationHeader.contains("/users/1"));

        UserResponseDTO responseBody = getUserResponseDTO(mvcResult);
        assertEquals(userResponseDTO.getId(), responseBody.getId());
        assertEquals(userResponseDTO.getName(), responseBody.getName());
    }

    @Test
    void shouldReturnOkWhenUpdateUser() {
        when(userService.update(USER_ID, userRequestDTO)).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.update(USER_ID, userRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(USER_NAME, response.getBody().getName());
        verify(userService, times(1)).update(USER_ID, userRequestDTO);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForUpdate() {
        when(userService.update(USER_ID, userRequestDTO)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> userController.update(USER_ID, userRequestDTO));
        verify(userService, times(1)).update(USER_ID, userRequestDTO);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundForFindById() {
        when(userService.findById(USER_ID)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> userController.findById(USER_ID));
        verify(userService, times(1)).findById(USER_ID);
    }

    private static UserResponseDTO getUserResponseDTO(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), UserResponseDTO.class
        );
    }

    private static MvcResult performPostRequest(String url, Object requestBody) throws Exception {
        String jsonBody = new ObjectMapper().writeValueAsString(requestBody);
        return mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andReturn();
    }
}
