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
      <App>
        <Button variant="contained">Hello World</Button>;
      </App>
  );
}

export default App;
