package com.tictactoe.tictactoe.service;


import java.util.Map;


public interface RoomService {
    Map<String, Object> createRoom(String name);
    Map<String, Object> entryRoom(String roomName, String name);
    Map<String, Object> position(String roomName, String name, Integer row, Integer column);
    Map<String, Object> getStatus(String roomName);
}
