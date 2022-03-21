package br.com.fatec.server.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.server.dtos.UserDto;
import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.exceptions.ResourceAlreadyExistsException;
import br.com.fatec.server.exceptions.ResourceNotFoundException;
import br.com.fatec.server.mappers.ProjectionMapper;
import br.com.fatec.server.mappers.UserMapper;
import br.com.fatec.server.projections.UserProjection;
import br.com.fatec.server.repositories.UserRepository;
import br.com.fatec.server.responses.SuccessResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<SuccessResponse> create(@RequestBody UserEntity user) {
        user.setUsePassword(passwordEncoder.encode(user.getUsePassword()));
        Optional<UserEntity> userExists = userRepository.findByUseEmail(user.getUseEmail());
        System.out.println(userExists.isEmpty());
        if (userExists.isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        UserEntity newUser = userRepository.save(user);
        SuccessResponse response = new SuccessResponse(ProjectionMapper.convertObject(UserProjection.WithoutPassword.class, newUser));
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        Page<UserProjection.WithoutPassword> user = userRepository.findAllProjectedBy(PageRequest.of(page, 25));
        SuccessResponse response = new SuccessResponse(user.getContent());
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> get(@PathVariable Long useCod) {
        UserProjection.WithoutPassword user = userRepository.findByUseCod(useCod, UserProjection.WithoutPassword.class);
        SuccessResponse response = new SuccessResponse(user);
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long useCod, @RequestBody UserDto newUser) {
        UserEntity user = userRepository.findByUseCod(useCod);
        if (user == null) {
            throw new ResourceNotFoundException("User not found in database");
        }
        mapper.updateUserFromDto(newUser, user);
        UserEntity updatedUser = userRepository.save(user);
        SuccessResponse response = new SuccessResponse(ProjectionMapper.convertObject(UserProjection.WithoutPassword.class, updatedUser));
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long useCod) {
        UserProjection.WithoutPassword user = userRepository.findByUseCod(useCod, UserProjection.WithoutPassword.class);
        userRepository.deleteById(useCod);
        SuccessResponse response = new SuccessResponse(user);
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }
}
