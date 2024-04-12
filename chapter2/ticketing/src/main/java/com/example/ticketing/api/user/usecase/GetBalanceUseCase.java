package com.example.ticketing.api.user.usecase;

import com.example.ticketing.domain.user.component.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBalanceUseCase {

    private final UserReader userReader;

    public int execute(String userUUID) {
        return userReader.findBalance(userUUID);
    }
}
