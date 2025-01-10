import React from 'react';
import {CONTENT} from "../../../constants/constants";

export const Introduction = (): React.ReactElement => {
  return (
          <div className="contentWidth">
            <h1>{CONTENT.APP_TITLE}</h1>
            <p>{CONTENT.APP_DESCRIPTION}</p>
          </div>
  )
}
