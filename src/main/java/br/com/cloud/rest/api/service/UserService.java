package br.com.cloud.rest.api.service;


import br.com.cloud.rest.api.domain.dto.UserRequestDTO;
import br.com.cloud.rest.api.domain.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO findById(Long id);

    UserResponseDTO create(UserRequestDTO userRequestDTO);

    UserResponseDTO update(Long id, UserRequestDTO userRequestDTO);
}
