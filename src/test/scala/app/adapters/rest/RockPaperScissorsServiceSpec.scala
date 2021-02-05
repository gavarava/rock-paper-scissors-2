package app.adapters.rest

import java.net.URI._
import app.adapters.rest.APIClient.{get, post, put}
import lombok.extern.slf4j.Slf4j

@Slf4j
class RockPaperScissorsServiceSpec extends Base {

  PLAYER1 + " and " + PLAYER2 should "register " in {
    put(player1Request, null)
    put(player2Request, null)

    val player1Response = get(player1Request)
    val player2Response = get(player2Request)
    assert(player1Response != null)
    assert(player1Response.contains(PLAYER1))
    assert(player2Response != null)
    assert(player2Response.contains(PLAYER2))
  }

  they should "join the same session " in {
    assert(get(player1Request).contains(PLAYER1))
    assert(get(player2Request).contains(PLAYER2))
    try {
      val startResponse = post(create(APP_CONTEXT_ROOT + "start"), "{\"player\": \"" + PLAYER1 + "\"}")
      val joinResponse = post(create(APP_CONTEXT_ROOT + "join"),
        "{\"gameId\": \"123456789L\",\"player\": \"" + PLAYER2 + "\"")
    } catch {
      case e: Exception => fail("Test failed due to exception: %s".format(e.getLocalizedMessage))
    }
  }

  they should "compete against each other " in {
    val player1Response = get(player1Request)
    val player2Response = get(player2Request)
    assert(player1Response != null)
    assert(player1Response.contains(PLAYER1))
    assert(player2Response != null)
    assert(player2Response.contains(PLAYER2))
  }
}