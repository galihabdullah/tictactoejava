package com.tictactoe.tictactoe.service.impl;

import com.tictactoe.tictactoe.Repository.BattleRepository;
import com.tictactoe.tictactoe.Repository.RoomRepository;
import com.tictactoe.tictactoe.ResourceNotFoundException;
import com.tictactoe.tictactoe.entity.BattleEntity;
import com.tictactoe.tictactoe.entity.RoomEntity;
import com.tictactoe.tictactoe.service.RoomService;
import com.tictactoe.tictactoe.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import javax.swing.text.AbstractDocument.BranchElement;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    Utils utils;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BattleRepository battleRepository;

    @Override
    public Map<String, Object> createRoom(String name) {
        String roomName = utils.generateRandomRoomName(5);
        RoomEntity ren = new RoomEntity();
        ren.setXplayer(name);
        ren.setRoomName(roomName);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        roomRepository.save(ren);
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("roomName", ren.getRoomName());
        returnValue.put("X Player", ren.getXplayer());
        returnValue.put("create at", timestamp);
        return returnValue;
    }

    @Override
    public Map<String, Object> entryRoom(String roomName, String name) {
        RoomEntity ren = roomRepository.findByRoomName(roomName);
        if (ren == null)
            throw new ResourceNotFoundException("invalid room");
        if (ren.getOplayer() != null)
            throw new ResourceNotFoundException("battle has been started");
        ren.setOplayer(name);
        roomRepository.save(ren);
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("roomName", ren.getRoomName());
        returnValue.put("X Player", ren.getXplayer());
        returnValue.put("O Player", ren.getOplayer());
        returnValue.put("create at", ren.getCreateAt());
        return returnValue;
    }

    @Override
    public Map<String, Object> position(String roomName, String name, Integer row, Integer column) {
        RoomEntity ren = roomRepository.findByRoomName(roomName);
        if (ren == null)
            throw new ResourceNotFoundException("invalid room");
        if (ren.getXplayer().equals(name) && ren.getOplayer() == null)
            throw new ResourceNotFoundException("O player not ready");
        if (!ren.getXplayer().equals(name) && !ren.getOplayer().equals(name)) {
            throw new ResourceNotFoundException("you don't have access to this battle");
        }
        if (ren.getWinner() != null)
            throw new ResourceNotFoundException(
                    calculateWinner(roomName, ren.getXplayer(), ren.getOplayer()).toString());

        if (row > 2 || column > 2)
            throw new ResourceNotFoundException("Position invalid");
        List<BattleEntity> be = new ArrayList<>(battleRepository.findByRoomName(roomName));
        if (be.isEmpty() && ren.getOplayer().equals(name))
            throw new ResourceNotFoundException("Player X First");
        for (int i = 0; i < be.size(); i++) {
            if (be.get(be.size() - 1).getPlayer().equals(name))
                throw new ResourceNotFoundException("lawan belum memberikan langkah");
            if (be.get(i).getRowSquare() == row && be.get(i).getColumnSquare() == column)
                throw new ResourceNotFoundException("Please!! take another position");
        }

        BattleEntity be1 = new BattleEntity();
        be1.setRoomName(roomName);
        be1.setPlayer(name);
        be1.setRowSquare(row);
        be1.setColumnSquare(column);
        battleRepository.save(be1);
        Map<String, Object> status = calculateWinner(roomName, ren.getXplayer(), ren.getOplayer());
        return status;
    }

    private Map<String, Object> calculateWinner(String roomName, String xPlayer, String oPlayer) {
        List<BattleEntity> be = battleRepository.findByRoomName(roomName);
        RoomEntity ren = roomRepository.findByRoomName(roomName);
        String status = "on battle";
        String winner = "none";
        if (be.size() == 9) {
            status = "Finished";
            winner = "draw";
        }
        String[][] square = { { "-", "-", "-" }, { "-", "-", "-" }, { "-", "-", "-" } };
        for (int j = 0; j < be.size(); j++) {
            if (be.get(j).getPlayer().equals(xPlayer)) {
                square[be.get(j).getRowSquare()][be.get(j).getColumnSquare()] = "X";
            } else if (be.get(j).getPlayer().equals(oPlayer)) {
                square[be.get(j).getRowSquare()][be.get(j).getColumnSquare()] = "O";
            }
        }

        String dominate = getDominate(square);
        switch (dominate) {
        case "X":
            winner = xPlayer;
            status = "Finished";
            break;
        case "O":
            winner = oPlayer;
            status = "Finished";
            break;
        }
        if (!winner.equals("none") && !winner.equals("") && ren.getWinner() == null) {
            ren.setWinner(winner);
            roomRepository.save(ren);
        }
        Map<String, Object> returnValue = new HashMap<>();
        returnValue.put("status", status);
        returnValue.put("winner", winner);
        returnValue.put("move position", Arrays.deepToString(square));
        return returnValue;
    }

    private String getDominate(String[][] square) {
        String dominate = "";
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square.length; j++) {
                if (!square[i][j].equals("-")) {
                    if (square[i][0].equals(square[i][0]) && square[i][1].equals(square[i][0])
                            && square[i][2].equals(square[i][0])) {
                        dominate = square[i][0];
                    } else if (square[0][i].equals(square[0][i]) && square[1][i].equals(square[0][i])
                            && square[2][i].equals(square[0][i])) {
                        dominate = square[0][i];
                    } else if (square[0][0].equals(square[0][0]) && square[1][1].equals(square[0][0])
                            && square[2][2].equals(square[0][0])) {
                        dominate = square[2][2];
                    } else if (square[0][2].equals(square[0][2]) && square[1][1].equals(square[0][2])
                            && square[2][0].equals(square[0][2])) {
                        dominate = square[2][0];
                    }
                }
            }
        }
        return dominate;
    }

    @Override
    public Map<String, Object> getStatus(String roomName) {
        RoomEntity ren = roomRepository.findByRoomName(roomName);
        Map<String, Object> returnValue = calculateWinner(roomName, ren.getXplayer(), ren.getOplayer());
        return returnValue;
    }
    
}
