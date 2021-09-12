import './App.css';
import RockPaperScissors from "./components/RockPaperScissors";
import Counter from "./components/Counter";
import {Button, Grid} from "@material-ui/core";
import {useState} from "react";

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

  let mainWindow = ''
  let buttonText = 'Play Rock-Paper-Scissors'

  if (state.displayWindow) {
    mainWindow = (
        <Grid item xs={10} justify = "center">
          <Counter></Counter>
        </Grid>
    )
    buttonText = 'CLOSE X'
  }

  return (
      <div className="App">
        <header className="App-header">
          <Grid container spacing={10} justify = "center">
            <Grid item xs={5}>
              <Grid item xs={3} justify = "center">
                <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => displayWindow()}>{buttonText}</Button>
              </Grid>
            </Grid>
            {mainWindow}
          </Grid>
        </header>
      </div>
  );
}

export default App;
