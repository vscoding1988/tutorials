import React from 'react';
import styles from './UrlComponent.module.scss';
import {UrlDTO} from "../../../../api";
import {BACKEND_BASE_URL} from "../../../../constants/api";
import {CopyComponent} from "./CopyShortUrl/CopyComponent";
import {QRCodeButton} from "./QRCode/QRCodeButton";

interface UrlComponentProps {
  url: UrlDTO;
}

export const UrlComponent = (data: UrlComponentProps): React.ReactElement => {
  const {url} = data;
  const fullShortUrl = BACKEND_BASE_URL + '/' + url.shortUrl;

  function getDate(): string | undefined {
    return url.creationDate?.toDateString();
  }

  return (
          <div className={styles.tableRow}>
            <div className={styles.tableColumn}>
              <p>
                <a href={fullShortUrl} target="_blank">{fullShortUrl}</a>
              </p>
              <p className={styles.longUrl}>
                <a href={url.targetUrl} className={styles.longUrl} target="_blank">{url.targetUrl}</a>
              </p>
            </div>
            <div className={styles.tableColumn}>
              <p>{getDate()}</p>
            </div>
            <div className={styles.tableColumn}>
              <p>{url.calls}</p>
            </div>
            <div className={styles.tableColumn}>
              <div className={styles.buttonContainer}>
                <CopyComponent text={fullShortUrl}/>
                <QRCodeButton text={fullShortUrl}/>
              </div>
            </div>
          </div>
  )
}
