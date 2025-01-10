import React from 'react';
import styles from './Table.module.scss';
import {UrlComponent} from "./UrlComponent/UrlComponent";
import {CONTENT} from "../../../constants/constants";
import {UrlDTO} from "../../../api";

interface TableProps {
  urls: UrlDTO[];
}

export const Table = (data: TableProps): React.ReactElement => {

  return (
          <div className={styles.table+" contentWidth"}>
            <div className={styles.tableHeader}>
              <div className={styles.tableColumn}>
                <p>{CONTENT.TABLE.TABLE_HEADERS.SHORTENED_URL}</p>
                <p className={styles.longUrlLabel}>{CONTENT.TABLE.TABLE_HEADERS.ORIGINAL_URL}</p>
              </div>
              <div className={styles.tableColumn}>
                <p>{CONTENT.TABLE.TABLE_HEADERS.CREATION_DATE}</p>
              </div>
              <div className={styles.tableColumn}>
                <p>{CONTENT.TABLE.TABLE_HEADERS.CALLS}</p>
              </div>
              <div className={styles.tableColumn}>
                <p>{CONTENT.TABLE.TABLE_HEADERS.ACTIONS}</p>
              </div>
            </div>
            <div className={styles.tableBody}>
              {data.urls.map((url) =>
                      <UrlComponent key={url.shortUrl} url={url}/>
              )}
            </div>
          </div>
  )
}
