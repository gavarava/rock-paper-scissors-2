import * as React from 'react';
import {useState} from 'react';
import {
  Container,
  Table,
  TableBody,
  TableCell,
  TableRow,
  TextField
} from "@material-ui/core";
import {Action} from "./Action";
import {DynamicResponse} from "./DynamicResponse";
import axios from "axios";
import {useInterval} from "../support/useInterval";

const POLL_INTERVAL = 1000;
export const RockPaperScissorsWindow = () => {
  const [stage, setStage] = useState('introAndDisplayRegistration')
  const [playerName, setPlayerName] = useState('')
  const [opponentName, setOpponentName] = useState('')
  const [formError, setFormError] = useState(false)
  const [inputHelperText, setInputHelperText] = useState('')
  const [inviteCode, setInviteCode] = useState('')

  useInterval(() => {
    if (stage === 'showInvitationAndPollForNextPlayer') {
      console.log("Poll for Next Player")
    }
  }, POLL_INTERVAL)

  const resetStates = () => {
    setPlayerName('')
    setFormError(false)
    setInputHelperText('')
  }
  const handleChangeStage = (toStage) => {
    // Here we will do other changes that will reflect what we did
    setStage(toStage)
  }

  const handleStartGame = (name) => {
    axios.post("http://localhost:8080/rockpaperscissors/start",
        {"player": name})
    .then((response) => {
      let gameId = response.data.gameId;
      console.log("Responding when generateInviteCode: " + gameId)
      setInviteCode(gameId)
      handleChangeStage('showInvitationAndPollForNextPlayer')
    }, (error) => {
      console.warn(error)
      throw error
    })
  }

  const handleJoinGame = (name, inviteCode) => {
    axios.post("http://localhost:8080/rockpaperscissors/join",
        {"gameId": inviteCode, "player": name})
    .then((response) => {
      resetStates()
      let gameId = response.data.gameId;
      console.log("Responding when generateInviteCode: " + gameId)
      setInviteCode(gameId)
      // THIS is BAD Business Logic guessing player1 and 2 instead of api returning the right stuff
      let array = response.data.players.replace('[','').replace(']','').split(',');
      setOpponentName(array.toString())
      handleChangeStage('joinGameAndPollForBothPlayersReady')
    }, (error) => {
      setFormError(true)
      setInputHelperText("Invite code not found!")
      throw error
    })
  }

  const doRegisterPlayer = async (name) => {
    axios.put("http://localhost:8080/rockpaperscissors/player/" + name)
    .then((response) => {
      resetStates()
      setPlayerName(name)
      handleChangeStage("displayWelcomeAndDisplayStartGame")
    }, (error) => {
      console.warn(error)
      setFormError(true)
      setInputHelperText("Try something else")
    })
  }

  const getPlayerWithName = async (name) => {
    axios.get("http://localhost:8080/rockpaperscissors/player/" + name)
    .then((response) => {
      console.log("Found player: " + response.data.name)
      setPlayerName(response.data.name)
      handleChangeStage("displayWelcomeAndDisplayStartGame")
    }, (error) => {
      console.warn(error)
      setFormError(true)
      setInputHelperText("Player not found!")
    })
  }

  return (
      <div>
        {(() => {
          switch (stage) {
            case 'introAndDisplayRegistration':
              return (
                  <Container maxWidth="xl">
                    <DynamicResponse
                        displayText={"Hello there! Let's play a game of Rock Paper Scissors today!"}/>
                    <Action description={"Start here!"}
                            onActionRun={() => {
                              resetStates()
                              handleChangeStage("displayRegistrationForm")
                            }}/>
                  </Container>
              )
            case 'displayRegistrationForm':
              return (
                  <Container maxWidth="xl">
                    <TextField label={"Play as"}
                               error={formError}
                               variant="outlined"
                               helperText={inputHelperText}
                               onChangeCapture={(event) => setPlayerName(
                                   event.target.value)}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Go Back"}
                                    onActionRun={() => handleChangeStage(
                                        "introAndDisplayRegistration")}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"Register me!"}
                                    onActionRun={() => doRegisterPlayer(
                                        playerName)}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"Existing player"}
                                    onActionRun={() => getPlayerWithName(
                                        playerName)}/>
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Container>
              )
            case 'displayWelcomeAndDisplayStartGame':
              return (
                  <Container maxWidth="xl">
                    <DynamicResponse
                        displayText={"Welcome " + playerName + "!"}/>
                    <DynamicResponse
                        displayText={"Generate an invite or enter a joining code."}/>
                    <TextField label={"Joining Code"}
                               error={formError}
                               variant="outlined"
                               helperText={inputHelperText}
                               onChangeCapture={(event) => setInviteCode(
                                   event.target.value)}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Go Back"}
                                    onActionRun={() => handleChangeStage(
                                        "introAndDisplayRegistration")}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"Generate Invite!"}
                                    onActionRun={() => handleStartGame(
                                        playerName)}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"Join game!"}
                                    onActionRun={() => handleJoinGame(
                                        playerName, inviteCode)}/>
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Container>
              )
            case 'showInvitationAndPollForNextPlayer':
              return (
                  <Container maxWidth="xl">
                    <DynamicResponse
                        displayText={"Hey " + playerName + "!"}/>
                    <DynamicResponse
                        displayText={"Share this code with your opponent: "
                        + inviteCode}/>
                    <Action description={"Go Back"}
                            onActionRun={() => handleChangeStage(
                                "introAndDisplayRegistration")}/>
                  </Container>
              )
            case 'joinGameAndPollForBothPlayersReady':
              return (
                  <Container maxWidth="xl">
                    <DynamicResponse
                        displayText={"Hey " + playerName + "!"}/>
                    <DynamicResponse
                        displayText={"Waiting for " + opponentName
                        + " to be ready!"}/>
                    <Action description={"Go Back"}
                            onActionRun={() => handleChangeStage(
                                "introAndDisplayRegistration")}/>
                  </Container>
              )
            default:
              throw Error('Stage Not Set')
          }
        })()}
      </div>
  )
}