package com.tictactoe.tictactoe.controller;

import com.tictactoe.tictactoe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class MainController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public Map<String, Object> createRoom(@RequestParam(value = "name") String name){
        return roomService.createRoom(name);
    }

    @PostMapping("/{roomName}/{name}")
    public Map<String, Object> entryRoom(@PathVariable(value = "roomName") String roomName,
                                         @PathVariable(value = "name") String name
                                         ){
        return roomService.entryRoom(roomName, name);
    }

    @PostMapping("/{roomName}/{name}/{row}/{column}")
    public Map<String, Object> givePosition(@PathVariable(value = "roomName") String roomName,
                                            @PathVariable(value = "name") String name,
                                            @PathVariable(value = "row") Integer row,
                                            @PathVariable(value = "column") Integer column
                                            ){
        return roomService.position(roomName, name, row, column);
    }

}
