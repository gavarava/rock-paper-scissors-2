import {Container} from "@material-ui/core";
import * as React from "react";
import Box from "@material-ui/core/Box";


export const TextualResponse = ({displayText}) => {
  // TODO Handle Custom Look and feel for all display responses
  return (
      <Box sx={{ display: 'grid', gridTemplateRows: 'repeat(3, 1fr)',
        textAlign: 'center',
        letterSpacing: '-1px',
        fontSize: '1.30em',
        fontFamily: '-apple-system, BlinkMacSystemFont, \'Segoe UI\', \'Roboto\', \'Oxygen\', \'Ubuntu\', \'Cantarell\', \'Fira Sans\', \'Droid Sans\', \'Helvetica Neue\', sans-serif',
        color:'#4831D4',
        fontWeight: 'bold'
      }}>
        <span>{displayText}</span>
      </Box>
  )
}