import React from 'react';
import './Header.module.scss';
import {Banner} from "./Banner/Banner";
import {Introduction} from "./Introduction/Introduction";

export const Header = (): React.ReactElement => {
  return (
          <header>
            <Banner/>
            <Introduction/>
          </header>
  )
}
