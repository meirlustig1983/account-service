package com.ml.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.accountservice.dto.AccountField;
import com.ml.accountservice.dto.AccountInfo;
import com.ml.accountservice.dto.AccountRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(jsonPath("$.message").value("Write operation error on server localhost:27017. Write error: WriteError{code=11000, message='E11000 duplicate key error collection: testDb.account index: email_1 dup key: { email: \"meir@gmail.com\" }', details={}}."))
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
}