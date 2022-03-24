package br.com.fatec.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.fatec.server.projections.UserProjection;
import br.com.fatec.server.responses.SuccessResponse;
import br.com.fatec.server.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> create(@RequestBody UserEntity user) {
        SuccessResponse response = new SuccessResponse(
                userService.createUser(user, UserProjection.WithoutPassword.class));
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        SuccessResponse response = new SuccessResponse(userService.getAllUsers(page));
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> get(@PathVariable Long useCod) {
        UserProjection.WithoutPassword user = userService.getUserById(useCod, UserProjection.WithoutPassword.class);
        SuccessResponse response = new SuccessResponse(user);
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Long useCod, @RequestBody UserDto newUser) {
        SuccessResponse response = new SuccessResponse(userService.updateUser(useCod, newUser,
                UserProjection.WithoutPassword.class));
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{useCod}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long useCod) {
        UserProjection.WithoutPassword user = userService.deleteUserById(useCod, UserProjection.WithoutPassword.class);
        SuccessResponse response = new SuccessResponse(user);
        return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }
}
