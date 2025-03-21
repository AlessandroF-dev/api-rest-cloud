package br.com.cloud.rest.api.service.impl;

import br.com.cloud.rest.api.domain.dto.UserRequestDTO;
import br.com.cloud.rest.api.domain.dto.UserResponseDTO;
import br.com.cloud.rest.api.domain.exception.DuplicateDataException;
import br.com.cloud.rest.api.domain.exception.ResultDataNotFoundException;
import br.com.cloud.rest.api.domain.model.User;
import br.com.cloud.rest.api.domain.repository.UserRepository;
import br.com.cloud.rest.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação do serviço de usuário que fornece operações para encontrar, criar e atualizar usuários.
 * Utiliza o repositório de usuários e o ModelMapper para conversões entre entidades e DTOs.
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    /**
     * Busca um usuário pelo seu identificador (ID).
     *
     * @param id o identificador do usuário que será buscado.
     * @return um objeto UserResponseDTO contendo os dados do usuário encontrado.
     * @throws ResultDataNotFoundException se o usuário com o ID fornecido não for encontrado.
     */
    @Override
    public UserResponseDTO findById(Long id) {
        Optional<User> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            return mapper.map(opUser.get(), UserResponseDTO.class);
        }

        throw new ResultDataNotFoundException("User not found");
    }

    /**
     * Cria um novo usuário a partir dos dados fornecidos no UserRequestDTO.
     * Verifica se o número da conta já existe antes de criar o usuário.
     *
     * @param userRequestDTO o DTO contendo os dados do novo usuário.
     * @return um objeto UserResponseDTO contendo os dados do usuário criado.
     * @throws DuplicateDataException se o número da conta informado já existir.
     */
    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByAccountNumber(userRequestDTO.getAccount().getNumber())) {
            throw new DuplicateDataException("This Account number already exists.");
        }

        User entity = userRepository.save(mapper.map(userRequestDTO, User.class));
        return mapper.map(entity, UserResponseDTO.class);
    }

    /**
     * Atualiza os dados de um usuário existente com base no ID e nas informações fornecidas no UserRequestDTO.
     *
     * @param id o identificador do usuário que será atualizado.
     * @param userRequestDTO o DTO contendo os novos dados do usuário.
     * @return um objeto UserResponseDTO contendo os dados do usuário atualizado.
     * @throws ResultDataNotFoundException se o usuário com o ID fornecido não for encontrado.
     */
    @Override
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        Optional<User> entity = userRepository.findById(id);

        if (entity.isPresent()) {
            entity.get().setName(userRequestDTO.getName());
            entity.get().setAccount(userRequestDTO.getAccount());
            entity.get().setCard(userRequestDTO.getCard());
            entity.get().setNews(userRequestDTO.getNews());
            entity.get().setFeatures(userRequestDTO.getFeatures());

            return mapper.map(userRepository.save(entity.get()), UserResponseDTO.class);
        }

        throw new ResultDataNotFoundException("User not found");
    }
}