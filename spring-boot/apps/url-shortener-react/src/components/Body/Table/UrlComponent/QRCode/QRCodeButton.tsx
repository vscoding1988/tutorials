import React, {useState} from 'react';
import styles from './QRCodeButton.module.scss';
import {QRCodePopup} from "./QRCodePopup/QRCodePopup";

interface QRCodeProps {
  text: string;
}

export const QRCodeButton = (props: QRCodeProps): React.ReactElement => {
  const [showPopup, setShowPopup] = useState<boolean>(false);

  return (
          <div className={styles.qrCode}>
            <img src="/assets/qr_code.svg" alt="Generate QR Code"
                 onClick={() => setShowPopup(true)}/>
            {showPopup ? <QRCodePopup text={props.text} setShowPopup={setShowPopup}/> : ""}
          </div>
  )
}
