package com.example.account.controller;

import com.example.account.domain.Transaction;
import com.example.account.dto.*;
import com.example.account.service.TransactionService;
import com.example.account.type.TransactionResultType;
import com.example.account.type.TransactionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.example.account.type.TransactionResultType.S;
import static com.example.account.type.TransactionType.USE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("잔액 사용 성공 케이스")
    void successUseBalance() throws Exception {
        //given
        given(transactionService.useBalance(anyLong(),anyString(),anyLong()))
                .willReturn(TransactionDto.builder()
                        .accountNumber("1234567890")
                        .transactedAt(LocalDateTime.now())
                        .amount(1000L)
                        .transactionId("transactionId")
                        .transactionResultType(S)
                        .build()
                );

        //when
        mockMvc.perform(post("/transaction/use")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new UseBalance.Request(1L,"1234567890",1000L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.transactionResult").value("S"))
                .andExpect(jsonPath("$.transactionId").value("transactionId"))
                .andExpect(jsonPath("$.amount").value(1000))
                .andDo(print());


        //then
    }


    @Test
    @DisplayName("잔액 사용 취소 성공 케이스")
    void successCancelBalance() throws Exception {
        //given
        given(transactionService.cancelBalance(anyString(),anyString(),anyLong()))
                .willReturn(TransactionDto.builder()
                        .accountNumber("1234567890")
                        .transactedAt(LocalDateTime.now())
                        .amount(54321L)
                        .transactionId("transactionIdForClass")
                        .transactionResultType(S)
                        .build()
                );

        //when
        mockMvc.perform(post("/transaction/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CancelBalance.Request("transactionId","1234567890",1000L)
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.transactionResult").value("S"))
                .andExpect(jsonPath("$.transactionId").value("transactionIdForClass"))
                .andExpect(jsonPath("$.amount").value(54321))
                .andDo(print());


        //then
    }

    @Test
    void successQueryTransaction () throws Exception {
        //given

        given(transactionService.queryTransaction(anyString()))
                .willReturn(TransactionDto.builder()
                        .accountNumber("1234567890")
                        .transactionType(USE)
                        .transactedAt(LocalDateTime.now())
                        .amount(54321L)
                        .transactionId("transactionIdForClass")
                        .transactionResultType(S)
                        .build());

        //when
        mockMvc.perform(get("/transaction/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                // 여기는 응답부
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.transactionResult").value("S"))
                .andExpect(jsonPath("$.transactionType").value("USE"))
                .andExpect(jsonPath("$.transactionId").value("transactionIdForClass"))
                .andExpect(jsonPath("$.amount").value(54321))
                .andDo(print());

        //then
    }





}