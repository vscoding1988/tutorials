import React from 'react';
import styles from './CopyComponent.module.scss';

interface CopyComponentProps {
  text: string;
}

export const CopyComponent = (props: CopyComponentProps): React.ReactElement => {
  let copySuccess = React.createRef<HTMLSpanElement>();


  function copyToClipboard() {
    navigator.clipboard.writeText(props.text).then(() => {
      let current = copySuccess.current;

      if (current) {
        current.style.opacity = '1';
        window.setTimeout(() => {
          if (current) {
            current.style.opacity = '0';
          }
        }, 1000);

      }
    });
  }

  return (

          <div className={styles.copyButton}>
            <img src="/assets/copy.png" alt="Copy"
                 onClick={copyToClipboard}/>
            <span
                    className={styles.copySuccess}
                    ref={copySuccess}
            >&#x2713;</span>
          </div>
  )
}
