import React from 'react';
import styles from './Banner.module.scss';
import {CONTENT} from "../../../constants/constants";

export const Banner = (): React.ReactElement => {
  return (
          <div className={styles.banner}>
            <img className={"image"} src="/assets/bee_logo.png" alt={CONTENT.BANNER_ALT}/>
          </div>
  )
}
