package br.com.fatec.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fatec.server.dtos.UserDto;
import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.exceptions.ResourceNotFoundException;
import br.com.fatec.server.mappers.ProjectionMapper;
import br.com.fatec.server.mappers.UserMapper;
import br.com.fatec.server.projections.UserProjection;
import br.com.fatec.server.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper mapper;

    public <T> T createUser(UserEntity user, Class<T> projection) {
        user.setUsePassword(passwordEncoder.encode(user.getUsePassword()));
        UserEntity newUser = userRepository.save(user);
        return ProjectionMapper.convertObject(projection, newUser);
    }

    public List<UserProjection.WithoutPassword> getAllUsers(Integer page) {
        Page<UserProjection.WithoutPassword> users = userRepository.findAllProjectedByOrderByUseCodAsc(PageRequest.of(page, 10));
        return users.getContent();
    }

    public <T> T getUserById(Long useCod, Class<T> projection) {
        T user = userRepository.findByUseCod(useCod, projection);
        if (user == null) {
            throw new ResourceNotFoundException("User not found in database");
        }
        return user;
    }

    public <T> T updateUser(Long useCod, UserDto newUser, Class<T> projection) {
        UserEntity user = userRepository.findByUseCod(useCod);
        if (user == null) {
            throw new ResourceNotFoundException("User not found in database");
        }
        if (newUser.getUsePassword() != null)
            newUser.setUsePassword(passwordEncoder.encode(newUser.getUsePassword()));
        mapper.updateUserFromDto(newUser, user);
        UserEntity updatedUser = userRepository.save(user);
        return ProjectionMapper.convertObject(projection, updatedUser);
    }

    public <T> T deleteUserById(Long useCod, Class<T> projection) {
        T user = userRepository.findByUseCod(useCod, projection);
        if (user == null) {
            throw new ResourceNotFoundException("User not found in database");
        }
        userRepository.deleteById(useCod);
        return user;
    }
}
