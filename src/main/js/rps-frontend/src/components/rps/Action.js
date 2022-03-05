import {Container} from "@material-ui/core";
import * as React from "react";
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
  root: {
    background: 'linear-gradient(45deg, #4831D1 30%, #9C8EF0 90%)',
    border: 0,
    borderRadius: 100,
    boxShadow: '5px 5px 15px 5px #4831D1',
    color: '#CCF381',
    height: 48,
    padding: '0 30px',
    fontWeight: 'bold',
    fontFamily: 'Chalkduster, fantasy',
  },
});
/**
 * Do an action.
 * Handle Errors.
 * Style the Button.
 */
export const Action = ({description, onActionRun}) => {
  const classes = useStyles();
  return (
      <Container maxWidth="sm">
        <Button className={classes.root} variant={"contained"} value={description} onClick={onActionRun}>{description}</Button>
      </Container>
  )
}