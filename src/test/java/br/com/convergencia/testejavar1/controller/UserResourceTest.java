package br.com.convergencia.testejavar1.controller;

import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.UserModel;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import br.com.convergencia.testejavar1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {

    @Mock
    UserService serviceMck;

    @Mock
    UserRepository repositoryMck;

    @InjectMocks
    UserResource userResource;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }


    @Test
    @DisplayName("should register new account user")
    void shouldCreateNewAccountUser() throws Exception {
        // given
        UserDto userDto = new UserDto(1L, "02370453117", "dt213656PPO!@#$");

        given(serviceMck.registerNewUserAccount(any())).willReturn(userDto);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(asJsonString(new UserForm("02370453117", "gd877029POL!@#")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/users/1"));
    }

    @Test
    @DisplayName("should find user by id")
    void shouldFindUserById() throws Exception {
        // given
        given(repositoryMck.findById(any()))
                .willReturn(Optional.of(new UserModel(1L, "02370453117", "gd877029POL!@#")));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("02370453117"))
                .andExpect(jsonPath("$.password").value("none"));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}