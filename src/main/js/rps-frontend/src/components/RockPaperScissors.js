import {
  Button, Grid,
  GridList,
  GridListTile, TextField
} from "@material-ui/core";
import {useEffect, useState} from "react";
import axios from "axios";

export const RockPaperScissors = () => {
  const [textValue, setTextValue] = useState('')
  const [submitValue, setSubmitValue] = useState(false)

  useEffect(() => { // Similar to componentDidMount and componentDidUpdate:
    console.log(textValue)
    if (submitValue) {
      console.log(textValue)
      axios.put('http://localhost:8080/rockpaperscissors/player/'+ textValue)
      .then(response => {
        console.log(response.status)
      })
      .catch(error => {
        console.error('There was an error!', error);
      });
      setSubmitValue(false)
    }
  }, [submitValue, textValue]);

  return (
      <Grid>
        <GridList>
          <GridListTile>
            <TextField
                label={'Player Name'}
                value={textValue}
                onChange={args => {
                  setTextValue(args.target.value)
                }}
            />
          </GridListTile>
          <GridListTile>
            <Button
                onClick={() => setSubmitValue(true)}>Confirm</Button>
            <Button onClick={() => {
              if (textValue !== '') {
                setTextValue('')
              }
            }}>Clear</Button>
          </GridListTile>
        </GridList>
      </Grid>
  )

}

export default RockPaperScissors;
