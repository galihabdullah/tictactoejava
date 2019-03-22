package com.tictactoe.tictactoe.service;

import org.springframework.stereotype.Service;

import java.util.Map;


public interface RoomService {
    Map<String, Object> createRoom(String name);
    Map<String, Object> entryRoom(String roomName, String name);
    Map<String, Object> position(String roomName, String name, Integer row, Integer column);
    Map<String, Object> status(String roomName);
    Map<String, Object> calculateWinner(String roomName, String xPlayer, String oPlayer);
}
