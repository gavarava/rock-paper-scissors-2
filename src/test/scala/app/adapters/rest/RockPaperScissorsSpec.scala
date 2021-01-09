package app.adapters.rest

import java.net.URI
import java.net.URI._

import app.adapters.rest.APIClient.{get, post, put}
import org.scalatest.flatspec._
import org.scalatest.matchers._

class RockPaperScissorsSpec extends AnyFlatSpec with should.Matchers {

  val APP_CONTEXT_ROOT = "http://localhost:8080/rockpaperscissors/";
  val PLAYER_ENDPOINT = "player/";

  val player1Name = "Alice";
  val player2Name = "Bob";

  val player1Request: URI = create(APP_CONTEXT_ROOT + PLAYER_ENDPOINT + player1Name)
  val player2Request: URI = create(APP_CONTEXT_ROOT + PLAYER_ENDPOINT + player2Name)

  player1Name + " and " + player2Name should "register " in {
    put(player1Request, null)
    put(player2Request, null)

    val player1Response = get(player1Request)
    val player2Response = get(player2Request)
    assert(player1Response != null)
    assert(player1Response.contains(player1Name))
    assert(player2Response != null)
    assert(player2Response.contains(player2Name))
  }

  they should "join the same session " in {
    assert(get(player1Request).contains(player1Name))
    assert(get(player2Request).contains(player2Name))
    try {
      val startResponse = post(create(APP_CONTEXT_ROOT + "start"), "{\"player\": \"" + player1Name + "\"}")
      val joinResponse = post(create(APP_CONTEXT_ROOT + "join"),
        "{\"session\": \"123456789L\",\"player\": \"" + player2Name + "\"")
    } catch {
      case e: Exception => fail("Test failed due to exception: %s".format(e.getLocalizedMessage))
    }
  }

  they should "compete against each other " in {
    val player1Response = get(player1Request)
    val player2Response = get(player2Request)
    assert(player1Response != null)
    assert(player1Response.contains(player1Name))
    assert(player2Response != null)
    assert(player2Response.contains(player2Name))
  }
}