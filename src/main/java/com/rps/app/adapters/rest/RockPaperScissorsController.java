package com.rps.app.adapters.rest;

import static java.time.OffsetDateTime.now;

import com.rps.app.adapters.rest.dto.SessionDto;
import com.rps.app.adapters.rest.dto.PlayerDto;
import com.rps.app.adapters.rest.dto.RockPaperScissorsRequestDto;
import com.rps.app.core.model.Session;
import com.rps.app.core.model.Move;
import com.rps.app.core.model.Move.Type;
import com.rps.app.core.model.Player;
import com.rps.app.core.services.PlayersService;
import com.rps.app.core.services.RockPaperScissorsService;
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
  RockPaperScissorsService rockPaperScissorsService;

  @PutMapping(path = "/player/{name}")
  public ResponseEntity<Object> createPlayer(@PathVariable String name) {
    try {
      playersService.createPlayer(name);
      return ResponseEntity.created(URI.create("/player/" + name)).build();
    } catch (Exception e) {
      log.error("Exception createPlayer => {}", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/player/{name}")
  public ResponseEntity<Player> getPlayer(@PathVariable String name) {
    try {
      return ResponseEntity.of(playersService.getPlayer(name));
    } catch (Exception e) {
      log.error("Exception getPlayer => ", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/player")
  public ResponseEntity<Object> updatePlayer(@RequestBody PlayerDto playerDto) {
    try {
      playersService.updatePlayer(playerDto.toDomain());
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("Exception updatePlayer => ", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/start")
  public ResponseEntity<Object> startGame(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      log.info("request startGame => " + request);
      var playerOptional = playersService.getPlayer(request.getPlayer());
      var startedGame = rockPaperScissorsService.start(playerOptional.get());
      log.info("startedGame => {}", startedGame);
      return ResponseEntity.ok(SessionDto.fromDomain(startedGame));
    } catch (Exception e) {
      log.error("Exception startGame => ", e);
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(path = "/join")
  public ResponseEntity<Object> joinGame(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      log.info("request joinGame => " + request);
      // TODO Handle Not found Exception
      var playerOptional = playersService.getPlayer(request.getPlayer());
      Session joinedSession = rockPaperScissorsService.join(playerOptional.get(), request.getGameId());
      log.info("joinedGame => {}", joinedSession);
      return ResponseEntity.ok(SessionDto.fromDomain(joinedSession));
    } catch (Exception e) {
      log.error("Exception joinGame => ", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/play")
  public ResponseEntity<Object> play(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      var playerOptional = playersService.getPlayer(request.getPlayer());
      var move = new Move(Type.of(request.getMove()), playerOptional.get(), now());
      var playedGame = rockPaperScissorsService.play(request.getGameId(), move);
      return ResponseEntity.ok(SessionDto.fromDomain(playedGame));
    } catch (Exception e) {
      log.error("Exception play => ", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(path = "/result")
  public ResponseEntity<Object> result(@RequestBody RockPaperScissorsRequestDto request) {
    try {
      // TODO Handle Not found Exception
      var result = rockPaperScissorsService.result(request.getGameId());
      return ResponseEntity.ok(SessionDto.fromDomain(result));
    } catch (Exception e) {
      log.error("Exception result => ", e);
      return ResponseEntity.badRequest().build();
    }
  }
}
