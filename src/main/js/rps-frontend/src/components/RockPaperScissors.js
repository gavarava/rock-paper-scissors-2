import {
  Button, Grid,
  TextField
} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";
import {makeStyles} from "@material-ui/core/styles";
import BackspaceIcon from '@material-ui/icons/Backspace';

/**
 Stateless function components don't have state.
 They can be used, if you don't need state and lifecycle methods, etc.
 Furthermore they can be turned into stateFUL function components by using hooks.
 Hooks are predefined functions that can be used inside a function component to enhance it with state,
 life cycle management, etc. But that's a broader topic.
 Without hooks you can use stateless components instead of class components to keep your
 code small (function instead of class, no this, no render function, just return,
 reuse code more easily by importing functions and using it inside the component)
 To improve performance you'd need hooks (useMemo() hook, e.g.) again.
 It takes some time to get used to the concept of hooks, but it's worth it.
 I prefer them over class components now, mainly because the code is cleaner.
 */
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
      <Grid container spacing={10} justify = "center">
        <form className={classes.form} noValidate>
          <Grid item xs={10}>
            <TextField
                variant="outlined"
                label={'Player Name'}
                value={textValue}
                onChange={args => setTextValue(args.target.value)}
            /> <Button
              color="primary"
              type={"submit"}
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
            </Grid>
          </Grid>
        </form>
      </Grid>
  )

}

export default RockPaperScissors;
