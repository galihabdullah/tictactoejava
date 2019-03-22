package com.tictactoe.tictactoe.Repository;

import com.tictactoe.tictactoe.entity.BattleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<BattleEntity, Long> {
    BattleEntity findByRoomNameAndRowSquareAndColumnSquare(String roomName, Integer rowSquare, Integer columnSquare);
    List<BattleEntity> findByRoomName(String roomName);
}
