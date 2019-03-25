package com.tictactoe.tictactoe.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_battle")
public class BattleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String roomName;

    @NotNull
    private String player;

    @NotNull
    private int rowSquare;

    @NotNull
    private int columnSquare;


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowSquare() {
        return rowSquare;
    }

    public void setRowSquare(int rowSquare) {
        this.rowSquare = rowSquare;
    }

    public int getColumnSquare() {
        return columnSquare;
    }

    public void setColumnSquare(int columnSquare) {
        this.columnSquare = columnSquare;
    }

}
