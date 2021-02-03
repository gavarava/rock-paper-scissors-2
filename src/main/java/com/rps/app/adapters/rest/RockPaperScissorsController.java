package com.rps.app.adapters.rest;

import com.rps.app.adapters.rest.dto.PlayerDto;
import com.rps.app.adapters.rest.dto.RockPaperScissorsRequestDto;
import com.rps.app.core.model.Player;
import com.rps.app.core.services.PlayersService;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("prod")
@RestController
@RequestMapping(
    path = "/rockpaperscissors",
    produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RockPaperScissorsController {

  PlayersService playersService;

  @PutMapping(path = "/player/{name}")
  ResponseEntity<Object> createPlayer(@PathVariable String name) {
    try {
      playersService.createPlayer(name);
      return ResponseEntity.created(URI.create("/player/" + name)).build();
    } catch (Exception e) {
      log.error("Exception createPlayer => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/player/{name}")
  ResponseEntity<Player> getPlayer(@PathVariable String name) {
    try {
      return ResponseEntity.of(playersService.getPlayer(name));
    } catch (Exception e) {
      log.error("Exception getPlayer => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/player")
  ResponseEntity<Object> updatePlayer(@RequestBody PlayerDto playerDto) {
    try {
      playersService.updatePlayer(playerDto.toDomain());
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("Exception createPlayer => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/start")
  ResponseEntity<Object> startgame(@RequestBody RockPaperScissorsRequestDto request) {
    throw new RuntimeException("NotImplemented");
  }

  @PostMapping(path = "/join")
  ResponseEntity<Object> joingame(@RequestBody RockPaperScissorsRequestDto request) {
    throw new RuntimeException("NotImplemented");
  }

  @PostMapping(path = "/play")
  ResponseEntity<Object> play(@RequestBody RockPaperScissorsRequestDto request) {
    throw new RuntimeException("NotImplemented");
  }

  @PostMapping(path = "/result")
  ResponseEntity<Object> result(@RequestBody RockPaperScissorsRequestDto request) {
    throw new RuntimeException("NotImplemented");
  }
}
