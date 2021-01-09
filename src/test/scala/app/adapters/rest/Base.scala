package app.adapters.rest

import java.net.URI
import java.net.URI.create

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class Base extends AnyFlatSpec with should.Matchers {

  val APP_CONTEXT_ROOT = "http://localhost:8080/rockpaperscissors/";
  val PLAYER_ENDPOINT = "player/";

  val PLAYER1 = "Alice";
  val PLAYER2 = "Bob";

  val player1Request: URI = create(APP_CONTEXT_ROOT + PLAYER_ENDPOINT + PLAYER1)
  val player2Request: URI = create(APP_CONTEXT_ROOT + PLAYER_ENDPOINT + PLAYER2)
}
