package br.com.cloud.rest.api.service;


import br.com.cloud.rest.api.domain.dto.UserRequestDTO;
import br.com.cloud.rest.api.domain.dto.UserResponseDTO;
import br.com.cloud.rest.api.domain.exception.DuplicateDataException;
import br.com.cloud.rest.api.domain.exception.ResultDataNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Implementação do serviço de usuário que fornece operações para encontrar, criar e atualizar usuários.
 * Utiliza o repositório de usuários e o ModelMapper para conversões entre entidades e DTOs.
 */

public interface UserService {

    /**
     * Busca um usuário pelo seu identificador (ID).
     *
     * @param id o identificador do usuário que será buscado.
     * @return um objeto UserResponseDTO contendo os dados do usuário encontrado.
     * @throws ResultDataNotFoundException se o usuário com o ID fornecido não for encontrado.
     */
    @Transactional(rollbackOn = Throwable.class)
    UserResponseDTO findById(Long id);

    /**
     * Cria um novo usuário a partir dos dados fornecidos no UserRequestDTO.
     * Verifica se o número da conta já existe antes de criar o usuário.
     *
     * @param userRequestDTO o DTO contendo os dados do novo usuário.
     * @return um objeto UserResponseDTO contendo os dados do usuário criado.
     * @throws DuplicateDataException se o número da conta informado já existir.
     */
    @Transactional(rollbackOn = Throwable.class)
    UserResponseDTO create(UserRequestDTO userRequestDTO);

    /**
     * Atualiza os dados de um usuário existente com base no ID e nas informações fornecidas no UserRequestDTO.
     *
     * @param id o identificador do usuário que será atualizado.
     * @param userRequestDTO o DTO contendo os novos dados do usuário.
     * @return um objeto UserResponseDTO contendo os dados do usuário atualizado.
     * @throws ResultDataNotFoundException se o usuário com o ID fornecido não for encontrado.
     */
    UserResponseDTO update(Long id, UserRequestDTO userRequestDTO);
}
