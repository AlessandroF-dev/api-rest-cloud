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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public UserResponseDTO findById(Long id) {
        Optional<User> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            return mapper.map(opUser.get(), UserResponseDTO.class);
        }

        throw new ResultDataNotFoundException("User not found");
    }

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByAccountNumber(userRequestDTO.getAccount().getNumber())) {
            throw new DuplicateDataException("This Account number already exists.");
        }

        User entity = userRepository.save(mapper.map(userRequestDTO, User.class));
        return mapper.map(entity, UserResponseDTO.class);
    }

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
