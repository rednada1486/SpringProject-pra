package com.example.account.service;

import com.example.account.dto.UseBalance;
import com.example.account.exception.AccountException;
import com.example.account.type.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.account.type.ErrorCode.ACCOUNT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LockAopAspectTest {
    @Mock
    LockService lockService;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    private LockAopAspect lockAopAspect;



    @Test
    void lockAndUnlock() throws Throwable {
        //given
        ArgumentCaptor<String> lockArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> unLockArgumentCaptor = ArgumentCaptor.forClass(String.class);

        UseBalance.Request request =
                new UseBalance.Request(1234L,"1234",1000L);
        //when
        lockAopAspect.aroundMethod(proceedingJoinPoint,request);
        //then

        verify(lockService,times(1)).lock(lockArgumentCaptor.capture());
        verify(lockService,times(1)).unLock(unLockArgumentCaptor.capture());

        assertEquals("1234",lockArgumentCaptor.getValue());
        assertEquals("1234",unLockArgumentCaptor.getValue());


    }

    @Test
    void lockAndUnlock_evenIfThrow() throws Throwable {
        //given
        ArgumentCaptor<String> lockArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> unLockArgumentCaptor = ArgumentCaptor.forClass(String.class);

        UseBalance.Request request =
                new UseBalance.Request(1234L,"54321",1000L);
        //when
        given(proceedingJoinPoint.proceed())
                .willThrow(new AccountException(ACCOUNT_NOT_FOUND));

        assertThrows(AccountException.class, ()->
                lockAopAspect.aroundMethod(proceedingJoinPoint,request));

        //then

        verify(lockService,times(1)).lock(lockArgumentCaptor.capture());
        verify(lockService,times(1)).unLock(unLockArgumentCaptor.capture());

        assertEquals("54321",lockArgumentCaptor.getValue());
        assertEquals("54321",unLockArgumentCaptor.getValue());


    }


}