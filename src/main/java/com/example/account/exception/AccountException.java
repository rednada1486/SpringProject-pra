package com.example.account.exception;

import com.example.account.type.ErrorCode;
import lombok.*;
import org.springframework.stereotype.Service;

// 그냥 Exception은 checked Exception이라서 메서드의 시그니쳐에 계속 붙이고 다녀야하는 불편함이 있음
// 또한 트랜잭션의 대상이 되지 않기 때문에 RuntimeException을 상속함.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountException extends RuntimeException {
    private ErrorCode errorCode;
    private String ErrorMessage;

    public AccountException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.ErrorMessage = errorCode.getDescription();
    }
}
