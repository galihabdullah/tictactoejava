package com.tictactoe.tictactoe.utils;

import com.tictactoe.tictactoe.Repository.BattleRepository;
import com.tictactoe.tictactoe.Repository.RoomRepository;
import com.tictactoe.tictactoe.entity.BattleEntity;
import com.tictactoe.tictactoe.entity.RoomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class Utils {

    @Autowired
    private BattleRepository battleRepository;

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvw";

    public String generateRandomRoomName(int length) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }


}
