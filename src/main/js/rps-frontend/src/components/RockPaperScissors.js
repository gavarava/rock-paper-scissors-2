import {
  Button, Grid,
  TextField
} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {makeStyles} from "@material-ui/core/styles";
import BackspaceIcon from '@material-ui/icons/Backspace';

export const RockPaperScissors = () => {

  const useStyles = makeStyles((theme) => ({
    paper: {
      marginTop: theme.spacing(8),
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    },
    avatar: {
      margin: theme.spacing(1),
      backgroundColor: theme.palette.secondary.main,
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(1),
    },
    submit: {
      margin: theme.spacing(3, 0, 2),
    },
  }));

  const classes = useStyles();
  const [textValue, setTextValue] = useState('')
  const [getPlayerValue, setGetPlayerValue] = useState(false)
  const [submitValue, setSubmitValue] = useState(false)

  useEffect(() => { // Similar to componentDidMount and componentDidUpdate:
    //console.log(textValue)
    if (submitValue) {
      // console.log(textValue)
      axios.put('http://localhost:8080/rockpaperscissors/player/' + textValue)
      .then(response => {
        console.log(response.status)
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
      setSubmitValue(false)
    }

    if (getPlayerValue) {
      axios.get('http://localhost:8080/rockpaperscissors/player/' + textValue)
      .then(response => {
        console.log(JSON.stringify(response))
      })
      // Always reset the state that you dont want to stay
      setGetPlayerValue(false)
    }
  }, [submitValue, textValue, getPlayerValue]);

  return (
      <Grid container spacing={10}>
        <form className={classes.form} noValidate>
          <Grid item xs={10}>
            <TextField
                variant="outlined"
                label={'Player Name'}
                value={textValue}
                onChange={args => setTextValue(args.target.value)}
            /> <Button
              color="primary"
              onClick={() => {
                if (textValue !== '') {
                  setTextValue('')
                }
              }}><BackspaceIcon/></Button>
            <Grid item xs={10}>
              <Button
                  variant="outlined"
                  color="secondary"
                  onClick={() => setSubmitValue(true)}>CREATE</Button>
              <Button
                  variant="outlined"
                  color="secondary"
                  onClick={() => setGetPlayerValue(true)}>Get Player</Button>
            </Grid>
          </Grid>
        </form>
      </Grid>
  )

}

export default RockPaperScissors;
