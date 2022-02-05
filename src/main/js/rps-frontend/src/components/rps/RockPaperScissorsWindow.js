import * as React from 'react';
import {Container, TextField} from "@material-ui/core";
import {Action} from "./Action";
import {ResponseWindow} from "./ResponseWindow";

export const RockPaperScissorsWindow = () => {
  const [stage, setStage] = useState('introAndDisplayRegistration')
  const handleChangeStage = (toStage) => {
    // Here we will do other changes that will reflect what we did
    setStage(toStage)
  }

  return (
      <div>
      {(() => {
          switch (stage) {
            case 'introAndDisplayRegistration':
              return (
                  <Container maxWidth="xl">
                    <ResponseWindow
                        displayText={"Hello there! Let's play a game of Rock Paper Scissors today!"}/>
                    <Action description={"Register"} onActionRun={() => handleChangeStage("displayRegistrationForm")}/>
                  </Container>
              )
            case 'displayRegistrationForm':
              // TODO Create a form here
              return (
                  <Container maxWidth="xl">
                    <Action description={"Go Back"} onActionRun={() => handleChangeStage("introAndDisplayRegistration")}/>
                  </Container>
              )
            case 'displayWelcomeAndDisplayStartGame':
              return (
                  <Container maxWidth="xl">
                    <ResponseWindow
                        displayText={"Welcome stranger!"}/>
                    <Action description={"Go Back"} onActionRun={() => handleChangeStage("introAndDisplayRegistration")}/>
                  </Container>
              )
            default:
              throw Error('Stage Not Set')
          }
        })()}
      </div>
  )
}