import {Container} from "@material-ui/core";
import * as React from "react";
import Button from "@material-ui/core/Button";

/**
 * Do an action.
 * Handle Errors.
 * Style the Button.
 */
export const Action = ({description, onActionRun}) => {
  return (
      <Container maxWidth="sm">
        <Button variant={"contained"} value={description} onClick={onActionRun}>{description}</Button>
      </Container>
  )
}