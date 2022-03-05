import * as React from 'react';
import { makeStyles } from "@material-ui/core/styles";
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
import {TextualResponse} from "./TextualResponse";
import axios from "axios";
import {useInterval} from "../support/useInterval";

const POLL_INTERVAL = 1000;

function getOpponentNamesArray(response) {
  let array = response.data.players.replace('[', '').replace(']', '').split(
      ',');
  return array;
}

const useStyles = makeStyles((theme) => ({
  container: {
    display: "inline-block",
    flexWrap: "wrap"
  },
  textField: {
    marginRight: theme.spacing(1)
  },
  label: {
    "&$disabled": {
      color: "black"
    }
  },
  inputRoot: {
    "&$disabled": {
      color: "red"
    }
  },
  disabled: {}
}));

export const RockPaperScissorsWindow = () => {
  const classes = useStyles();
  const [stage, setStage] = useState('introAndDisplayRegistration')
  const [playerName, setPlayerName] = useState('')
  const [opponentNames, setOpponentNames] = useState([])
  const [winner, setWinner] = useState('')
  const [lastMove, setLastMove] = useState('')
  const [formError, setFormError] = useState(false)
  const [inputHelperText, setInputHelperText] = useState('')
  const [sessionCode, setSessionCode] = useState('')

  useInterval(() => {
    if (stage === 'showInvitationAndPollForNextPlayer') {
      console.log("Poll for Next Player")
      pollResult(sessionCode, playerName)
      console.log(opponentNames)
      if(opponentNames.length === 2) {
        handleChangeStage('joinGameAndPollForBothPlayersReady')
      }
    }
    if (stage === 'pollResult') {
      console.log("Polling for winner")
      pollResult(sessionCode, playerName)
    }
  }, POLL_INTERVAL)

  const resetStates = () => {
    setPlayerName('')
    setOpponentNames([])
    setLastMove('')
    setSessionCode('')
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
      setSessionCode(gameId)
      handleChangeStage('showInvitationAndPollForNextPlayer')
    }, (error) => {
      console.warn(error)
      throw error
    })
  }

  const handleJoinGame = (name, sessionCode) => {
    axios.post("http://localhost:8080/rockpaperscissors/join",
        {"gameId": sessionCode, "player": name})
    .then((response) => {
      setFormError(false)
      let gameId = response.data.gameId;
      console.log("Responding when generateInviteCode: " + gameId)
      setSessionCode(gameId)
      // THIS is BAD Business Logic guessing player1 and 2 instead of api returning the right stuff
      let array = getOpponentNamesArray(response);
      setOpponentNames(array)
      handleChangeStage('joinGameAndPollForBothPlayersReady')
    }, (error) => {
      setFormError(true)
      setInputHelperText(
          "Session not found! Start Over with New game or another code.")
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
      setInputHelperText("Try something else. Maybe you are already registered?")
    })
  }

  const handlePlay = async (gameId, player, move) => {
    setLastMove(move)
    if (player === undefined || player === '') {
      throw Error('player cannot be undefined')
    }
    axios.post("http://localhost:8080/rockpaperscissors/play",
        {"gameId": gameId, "player": player, "move": move})
    .then((response) => {
      console.log("handlePlay response: " + response)
      handleChangeStage("pollResult")
    }, (error) => {
      console.warn(error)
    })
  }

  const pollResult = async (gameId, player) => {
    if (player === undefined || player === '') {
      throw Error('player cannot be undefined')
    }
    axios.post("http://localhost:8080/rockpaperscissors/result",
        {"gameId": gameId, "player": player})
    .then((response) => {
      if(response.data.winner) {
        console.info("winner IS " + response.data.winner)
        setWinner(response.data.winner)
        handleChangeStage('showWinner')
      }
      setOpponentNames(getOpponentNamesArray(response))
    }, (error) => {
      console.warn(error)
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
                    <TextualResponse
                        displayText={"Hi there!"}/>
                    <TextualResponse
                        displayText={"Challenge someone to a game of Rock Paper Scissors!"}/>
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
                               className={classes.container}
                               error={formError}
                               variant="outlined"
                               helperText={inputHelperText}
                               onChangeCapture={(event) => setPlayerName(
                                   event.target.value)}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Start Over"}
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
                    <TextualResponse
                        displayText={"Welcome " + playerName + "!"}/>
                    <TextualResponse
                        displayText={"Generate an invite or enter a joining code."}/>
                    <TextField label={"Joining Code"}
                               error={formError}
                               variant="outlined"
                               helperText={inputHelperText}
                               onChangeCapture={(event) => setSessionCode(
                                   event.target.value)}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Start Over"}
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
                                        playerName, sessionCode)}/>
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Container>
              )
            case 'showInvitationAndPollForNextPlayer':
              return (
                  <Container maxWidth="xl">
                    <TextualResponse
                        displayText={"Hey " + playerName + "!"}/>
                    <TextualResponse
                        displayText={"Share this code with your opponent: "
                        + sessionCode}/>
                    <Action description={"Start Over"}
                            onActionRun={() => handleChangeStage(
                                "introAndDisplayRegistration")}/>
                  </Container>
              )
            case 'joinGameAndPollForBothPlayersReady':
              return (
                  <Container maxWidth="xl">
                    <TextualResponse
                        displayText={"Hey " + playerName + "! Are you ready?"}/>
                    <TextualResponse
                        displayText={opponentNames[0] + " VS "
                        + opponentNames[1]}/>
                    <TextualResponse
                        displayText={"ROCK! PAPER! SCISSORS!"}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Chickening out"}
                                    onActionRun={() => handleChangeStage(
                                        "introAndDisplayRegistration")}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"Ready"}
                                    onActionRun={() => handleChangeStage(
                                        "showMovesAndWait")}/>
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Container>
              )
            case 'showMovesAndWait':
              return (
                  <Container maxWidth="xl">
                    <TextualResponse
                        displayText={"Come on " + playerName + "!"}/>
                    <TextualResponse
                        displayText={"ROCK! PAPER! SCISSORS!"}/>
                    <Table>
                      <TableBody>
                        <TableRow>
                          <TableCell>
                            <Action description={"Start Over"}
                                    onActionRun={() => handleChangeStage(
                                        "introAndDisplayRegistration")}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"ROCK"}
                                    onActionRun={() => handlePlay(sessionCode,
                                        playerName, 'rock')}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"PAPER"}
                                    onActionRun={() => handlePlay(sessionCode,
                                        playerName, 'paper')}/>
                          </TableCell>
                          <TableCell>
                            <Action description={"SCISSORS"}
                                    onActionRun={() => handlePlay(sessionCode,
                                        playerName, 'scissors')}/>
                          </TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Container>
              )
            case 'pollResult':
              return (
                  <Container maxWidth="xl">
                    <TextualResponse
                        displayText={"Polling Response"}/>
                  </Container>
              )
            case 'showWinner':
              return (
                  <Container maxWidth="xl">
                    <TextualResponse
                        displayText={"Hey " + playerName+"! You played " + lastMove + "!"}/>
                    <TextualResponse
                        displayText={winner + " has won the game"}/>
                    <Action description={"Play Again"}
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