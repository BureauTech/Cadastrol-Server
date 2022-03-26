package br.com.fatec.server.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.http.Cookie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.mappers.ProjectionMapper;
import br.com.fatec.server.projections.UserProjection;
import br.com.fatec.server.repositories.UserRepository;
import br.com.fatec.server.responses.SuccessResponse;
import br.com.fatec.server.security.JWTUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("UserControllerTest")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private void createUser(UserEntity user) throws JsonProcessingException, Exception {
        user.setUsePassword(new BCryptPasswordEncoder().encode(user.getUsePassword()));
        userRepository.save(user);
    }

    private Cookie performLogin(UserEntity user) throws JsonProcessingException, Exception {
        String authJson = String.format("{\"useEmail\": \"%s\", \"usePassword\": \"%s\"}",
            user.getUseEmail(), user.getUsePassword());

        return mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON).content(authJson))
            .andExpect(status().isOk()).andReturn().getResponse().getCookie(JWTUtil.COOKIE_NAME);
    }

    private UserEntity getCreatedUser(UserEntity user) {
        return userRepository.findByUseEmail(user.getUseEmail()).get();
    }

    private UserProjection.WithoutPassword getUserWithoutPassword(UserEntity user) {
        return ProjectionMapper.convertObject(UserProjection.WithoutPassword.class, user);
    }

    private String userToJson(Object user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user);
    }

    @Test
    @DisplayName("shouldCreateUser")
    public void shouldCreateUser() throws JsonProcessingException, Exception {
        UserEntity user = new UserEntity();
        user.setUseEmail("shouldCreateUser@testing.com");
        user.setUseName("coolname");
        user.setUsePassword("strongpassword");
        user.setUsePhone("shouldCreateUser");

        MvcResult result = mockMvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("shouldCreateUser@testing.com")))
            .andReturn();
        String userCreatedJson = userToJson(getUserWithoutPassword(getCreatedUser(user)));
        assertThat(result.getResponse().getContentAsString(), containsString(userCreatedJson));
    }

    @Test
    @DisplayName("shouldGetUser_whenUserIsCreated")
    public void shouldGetUser_whenUserIsCreated() throws JsonProcessingException, Exception {
        UserEntity user = new UserEntity();
        user.setUseEmail("shouldGetUser_whenUserIsCreated@testing.com");
        user.setUseName("coolname");
        user.setUsePassword("strongpassword");
        user.setUsePhone("shouldGetUser_whenUserIsCreated");

        createUser(user);
        user.setUsePassword("strongpassword");

        Cookie jwtCookie = performLogin(user);
        MvcResult result = mockMvc.perform(get(String.format("/user/%d", user.getUseCod()))
            .cookie(jwtCookie)).andExpect(status().isOk()).andReturn();

        String userCreatedJson = userToJson(getUserWithoutPassword(user));
        assertThat(result.getResponse().getContentAsString(), containsString(userCreatedJson));
    }

    @Test
    @DisplayName("shouldUpdateUser_whenUserIsCreated")
    public void shouldUpdateUser_whenUserIsCreated() throws JsonProcessingException, Exception {
        UserEntity user = new UserEntity();
        user.setUseEmail("shouldUpdateUser_whenUserIsCreated@testing.com");
        user.setUseName("coolname");
        user.setUsePassword("strongpassword");
        user.setUsePhone("shouldUpdateUser_whenUserIsCreated");

        createUser(user);
        user.setUsePassword("strongpassword");
        Cookie jwtCookie = performLogin(user);
        user.setUseName("new cool name");
        MvcResult result = mockMvc.perform(put(String.format("/user/%d", user.getUseCod()))
            .content(userToJson(user))
            .contentType(MediaType.APPLICATION_JSON)
            .cookie(jwtCookie))
            .andExpect(status().isOk()).andReturn();

        assertThat(result.getResponse().getContentAsString(), containsString("new cool name"));
    }

    @Test
    @DisplayName("shouldDeleteUser_whenUserIsCreated")
    public void shouldDeleteUser_whenUserIsCreated() throws JsonProcessingException, Exception {
        UserEntity user = new UserEntity();
        user.setUseEmail("shouldDeleteUser_whenUserIsCreated@testing.com");
        user.setUseName("coolname");
        user.setUsePassword("strongpassword");
        user.setUsePhone("shouldDeleteUser_whenUserIsCreated");

        createUser(user);
        user.setUsePassword("strongpassword");
        Cookie jwtCookie = performLogin(user);
        MvcResult result = mockMvc.perform(delete(String.format("/user/%d", user.getUseCod()))
            .cookie(jwtCookie)).andExpect(status().isOk()).andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
    }

    @Test
    @DisplayName("shouldListUsers")
    public void shouldListUsers() throws JsonProcessingException, Exception {
        UserEntity user = new UserEntity();
        for (int i = 0; i < 10; i++) {
            user.setUseEmail(String.format("email_%d_@testing.com", i));
            user.setUseName("coolname");
            user.setUsePassword("strongpassword");
            user.setUsePhone(String.format("123456772%d", i));
            createUser(user);
            user.setUsePassword("strongpassword");
        }

        Cookie jwtCookie = performLogin(user);
        MvcResult result = mockMvc.perform(get(String.format("/user?page=%d", 0))
            .cookie(jwtCookie)).andExpect(status().isOk()).andReturn();

        List<UserProjection.WithoutPassword> userList = userRepository.findAllProjectedByOrderByUseCodAsc(PageRequest.of(0, 10)).getContent();
        SuccessResponse successResponse = new SuccessResponse(userList);
        String expectedJson = objectMapper.writeValueAsString(successResponse);
        assertThat(result.getResponse().getContentAsString(), containsString(expectedJson));
    }
}
