package com.tictactoe.tictactoe.Repository;

import com.tictactoe.tictactoe.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    RoomEntity findByRoomName(String RoomName);
    RoomEntity findByRoomNameAndXplayerOrOplayer(String roomName, String xPlayer, String oPlayer);
}
