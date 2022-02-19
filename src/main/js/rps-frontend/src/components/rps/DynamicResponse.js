import {Container} from "@material-ui/core";
import * as React from "react";

export const DynamicResponse = ({displayText}) => {
  // TODO Handle Custom Look and feel for all display responses
  return (
      <Container maxWidth="lg">
        <b>{displayText}</b>
      </Container>
  )
}