import React from 'react';
import styles from './ControlBar.module.scss';
import {CONTENT} from "../../../constants/constants";
import {UrlRequestSizeEnum} from "../../../api";

interface ControlBarProps {
  onSearch: Function;
  onSizeChange: Function;
  term: string;
}

export const ControlBar = (props: ControlBarProps): React.ReactElement => {

  function handleUserInput(e: React.ChangeEvent<HTMLInputElement>) {
    let value = e.target.value

    if (value.length === 0 && props.term.length > 0) {
      // reset the current search
      props.onSearch(value);
    } else if (value.length > 3) {
      // only search if the term is longer than 3 characters
      //
      // DEV Note
      //
      // maybe a bad idea, when short url can contain just one character,
      // but since we are searching in short and target url, searching for
      // less than 3 characters, would not produce good results
      props.onSearch(e.target.value);
    }
  }

  function handleSizeChange(e: React.ChangeEvent<HTMLSelectElement>) {
    props.onSizeChange(e.target.value);
  }

  return (
          <div className={styles.controlBar}>
            <div>
              <span>{CONTENT.TABLE.CONTROLS.SHOW}</span>
              <select name="limit" onChange={handleSizeChange}>
                {/*TODO Try to find a way to iterate*/}
                <option value={UrlRequestSizeEnum._10}>10</option>
                <option value={UrlRequestSizeEnum._25}>25</option>
                <option value={UrlRequestSizeEnum._50}>50</option>
              </select>
              <span>{CONTENT.TABLE.CONTROLS.PER_PAGE}</span>
            </div>

            <input placeholder={CONTENT.TABLE.CONTROLS.SEARCH_PLACEHOLDER}
                   onChange={handleUserInput}/>
          </div>
  )
}
