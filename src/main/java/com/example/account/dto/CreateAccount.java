package com.example.account.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateAccount {


    @Getter
    @Setter
    public static class Request{
        @NotNull //validation
        @Min(1) //validation
        private Long userId;

        @NotNull //validation
        @Min(100) //validation
        private Long initialBalance;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long userId;
        private String accountNumber;
        private LocalDateTime registeredAt;
    }




}
