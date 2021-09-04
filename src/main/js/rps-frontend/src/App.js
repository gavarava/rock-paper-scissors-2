import './App.css';
import RockPaperScissors from "./components/RockPaperScissors";
import {Button, Grid} from "@material-ui/core";
import {useState} from "react";
import SignIn from "./components/SignIn";

function App() {
  const [state, setState] = useState({
    displayWindow: false,
    displaySignIn: false
  })

  const displayWindow = () => {
    setState({
      displayWindow: !state.displayWindow,
      displaySignIn: state.displaySignIn
    })
  }

  const displaySignIn = () => {
    setState({
      displayWindow: state.displayWindow,
      displaySignIn: !state.displaySignIn
    })
  }

  let mainWindow = ''
  let buttonText = 'Play Rock-Paper-Scissors'
  let signInText = 'Sign In!'

  if (state.displayWindow) {
    mainWindow = (
        <Grid item xs={10}>
          <RockPaperScissors></RockPaperScissors>
        </Grid>
    )
    buttonText = 'CLOSE X'
  }

  if (state.displaySignIn) {
    mainWindow = (
        <Grid item xs={10}>
          <SignIn/>
        </Grid>
    )
    signInText = 'CLOSE X'
  }

  return (
      <div className="App">
        <header className="App-header">
          <Grid container spacing={10}>
            <Grid item xs={5}>
              <Grid item xs={3}>
                <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => displayWindow()}>{buttonText}</Button>
              </Grid>
              <Grid item xs={3}>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={() => displaySignIn()}>{signInText}</Button>
              </Grid>
            </Grid>
            {mainWindow}
          </Grid>
        </header>
      </div>
  );
}

export default App;
