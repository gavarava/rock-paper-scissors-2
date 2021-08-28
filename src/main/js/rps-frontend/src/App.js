import logo from './logo.svg';
import './App.css';
import RockPaperScissors from "./components/RockPaperScissors";

function App() {


  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <RockPaperScissors></RockPaperScissors>
      </header>
    </div>
  );
}

export default App;
