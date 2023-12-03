package com.ml.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.accountservice.dto.*;
import com.ml.accountservice.model.TokenType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAccountByNotExistsEmail() throws Exception {
        AccountRequest request = new AccountRequest("meir@gmail.com", AccountField.EMAIL);
        mockMvc.perform(get("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.path").value("/api/v1/account"))
                .andExpect(jsonPath("$.message").value("Failed to find account"))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(2)
    void getAccountByNotExistsPhoneNumber() throws Exception {
        AccountRequest request = new AccountRequest("480-617-6699", AccountField.PHONE_NUMBER);
        mockMvc.perform(get("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.path").value("/api/v1/account"))
                .andExpect(jsonPath("$.message").value("Failed to find account"))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(3)
    void registration() throws Exception {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("Meir")
                .setLastName("Lustig")
                .setEmail("meir@gmail.com")
                .setPhoneNumber("480-617-6699")
                .setCreationDate(LocalDateTime.now());

        mockMvc.perform(post("/api/v1/account/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Meir"))
                .andExpect(jsonPath("$.lastName").value("Lustig"))
                .andExpect(jsonPath("$.email").value("meir@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("480-617-6699"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty())
                .andExpect(jsonPath("$.version").value("0"))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(4)
    void tryRegistrationWithDuplicateData() throws Exception {
        AccountInfo accountInfo = new AccountInfo()
                .setFirstName("Meir")
                .setLastName("Lustig")
                .setEmail("meir@gmail.com")
                .setPhoneNumber("480-617-6699")
                .setCreationDate(LocalDateTime.now());

        mockMvc.perform(post("/api/v1/account/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInfo)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.path").value("/api/v1/account/registration"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.statusCode").value(500))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(5)
    void getAccountByEmail() throws Exception {
        AccountRequest request = new AccountRequest("meir@gmail.com", AccountField.EMAIL);
        mockMvc.perform(get("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Meir"))
                .andExpect(jsonPath("$.lastName").value("Lustig"))
                .andExpect(jsonPath("$.email").value("meir@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("480-617-6699"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty())
                .andExpect(jsonPath("$.version").value("0"))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(6)
    void getAccountByPhoneNumber() throws Exception {
        AccountRequest request = new AccountRequest("480-617-6699", AccountField.PHONE_NUMBER);
        mockMvc.perform(get("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Meir"))
                .andExpect(jsonPath("$.lastName").value("Lustig"))
                .andExpect(jsonPath("$.email").value("meir@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("480-617-6699"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty())
                .andExpect(jsonPath("$.version").value("0"))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(7)
    void updateToken() throws Exception {
        TokenInfo tokenInfo = new TokenInfo("TOKENXX001", "bank-service",
                TokenType._2FA, false, LocalDateTime.now());
        TokenUpdateRequest request = new TokenUpdateRequest("480-617-6699",
                AccountField.PHONE_NUMBER, tokenInfo);
        mockMvc.perform(patch("/api/v1/account/update-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Meir"))
                .andExpect(jsonPath("$.lastName").value("Lustig"))
                .andExpect(jsonPath("$.email").value("meir@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("480-617-6699"))
                .andExpect(jsonPath("$.updateDate").isNotEmpty())
                .andExpect(jsonPath("$.creationDate").isNotEmpty())
                .andExpect(jsonPath("$.version").value("1"))
                .andDo(document("{method-name}"));
    }

    @Test
    @Order(8)
    void deleteAccount() throws Exception {
        AccountRequest request = new AccountRequest("meir@gmail.com", AccountField.EMAIL);
        mockMvc.perform(delete("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("{method-name}"));
    }
}