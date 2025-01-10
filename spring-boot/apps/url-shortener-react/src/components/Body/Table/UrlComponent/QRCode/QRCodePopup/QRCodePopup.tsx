import React from 'react';
import styles from './QRCodePopup.module.scss';
import QRCode from "react-qr-code";

interface QRCodePopupProps {
  text: string;
  setShowPopup: Function;
}

export const QRCodePopup = (props: QRCodePopupProps): React.ReactElement => {

  return (
          <div className={styles.qrCodeOverlay} onClick={() => props.setShowPopup(false)}>
            <div className={styles.qrCodePopup}>
              <QRCode value={props.text}/>
              <button onClick={() => props.setShowPopup(false)} className={styles.close}>&times;</button>
            </div>
          </div>

  )
}
