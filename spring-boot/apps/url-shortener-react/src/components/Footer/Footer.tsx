import React from 'react';
import './Footer.module.scss';
import {CONTENT} from "../../constants/constants";

export const Footer = (): React.ReactElement => {
  return (
          <footer>
            <div className="contentWidth">
              <p>{CONTENT.APP_FOOTER}</p>
            </div>
          </footer>
  )
}
