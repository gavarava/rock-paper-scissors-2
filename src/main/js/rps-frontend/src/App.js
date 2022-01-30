import * as React from 'react';
import './App.css';
import {Container, Grid, ImageList} from "@material-ui/core";
import RockPaperScissors from "./components/RockPaperScissors";


function App() {
  return (
      <div className="App">
          <header className="App-header">
            <Container maxWidth="sm">
              <Grid>
                <RockPaperScissors/>
              </Grid>
            </Container>
          </header>
      </div>
  );
};

export default App;
