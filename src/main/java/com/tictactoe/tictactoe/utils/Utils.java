package com.tictactoe.tictactoe.utils;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {


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
