package com.rps.app.adapters.rest;

import static java.time.OffsetDateTime.now;

import com.rps.app.adapters.rest.dto.GameDto;
import com.rps.app.adapters.rest.dto.PlayerDto;
import com.rps.app.adapters.rest.dto.RockPaperScissorsRequestDto;
import com.rps.app.core.model.Game;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Move.Type;
import com.rps.app.core.model.Player;
import com.rps.app.core.services.PlayersService;
import com.rps.app.core.services.RockPaperScissorsService;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
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
  RockPaperScissorsService rockPaperScissorsService;

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
      log.error("Exception updatePlayer => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/start")
  ResponseEntity<Object> startGame(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      log.info("request startGame => " + request);
      var playerOptional = playersService.getPlayer(request.getPlayer());
      var startedGame = rockPaperScissorsService.start(playerOptional.get());
      log.info("startedGame => {}", startedGame);
      return ResponseEntity.ok(GameDto.fromDomain(startedGame));
    } catch (Exception e) {
      log.error("Exception startGame => {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(path = "/join")
  ResponseEntity<Object> joinGame(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      log.info("request joinGame => " + request);
      // TODO Handle Not found Exception
      var playerOptional = playersService.getPlayer(request.getPlayer());
      Game joinedGame = rockPaperScissorsService.join(playerOptional.get(), request.getGameId());
      log.info("joinedGame => {}", joinedGame);
      return ResponseEntity.ok(GameDto.fromDomain(joinedGame));
    } catch (Exception e) {
      log.error("Exception joinGame => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/play")
  ResponseEntity<Object> play(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      var playerOptional = playersService.getPlayer(request.getPlayer());
      var move = new Move(Type.of(request.getMove()), playerOptional.get(), now());
      return ResponseEntity.accepted().body(
          rockPaperScissorsService.play(request.getGameId(), move));
    } catch (Exception e) {
      log.error("Exception play => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/result")
  ResponseEntity<Object> result(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      return ResponseEntity.accepted().body(
          rockPaperScissorsService.result(request.getGameId()));
    } catch (Exception e) {
      log.error("Exception result => {}", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
