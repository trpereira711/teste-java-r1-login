package br.com.convergencia.testejavar1.controller;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.model.dto.TokenDto;
import br.com.convergencia.testejavar1.service.AuthenticationService;
import br.com.convergencia.testejavar1.service.exception.AuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AuthenticationResourceTest {

    @Mock
    AuthenticationService serviceMck;

    @InjectMocks
    AuthenticationResource authenticationResource;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationResource).build();
    }

    @Test
    @DisplayName("should authenticate user")
    void shouldAuthenticateUser() throws Exception {
        // given
        given(serviceMck.authenticate(any())).willReturn(new TokenDto("token2121212121212121"));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .content(asJsonString(new UserForm("02370453117", "gd877029POL!@#")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token2121212121212121"))
                .andExpect(jsonPath("$.type").value("Barear"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}