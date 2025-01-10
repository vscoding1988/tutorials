import React, {useState} from 'react';
import styles from './CreationForm.module.scss';
import {CONTENT} from "../../../constants/constants";
import {shortUrlApi} from "../../../constants/api";

interface ControlBarProps {
  onCreation: Function;
}

export const CreationForm = (props: ControlBarProps): React.ReactElement => {
  const [showError, setShowError] = useState<boolean>(false);
  let shortUrlRef = React.createRef<HTMLInputElement>();
  let targetUrlRef = React.createRef<HTMLInputElement>();


  function onSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    setShowError(false);
    shortUrlApi.create({
      urlCreationRequest: {
        targetUrl: targetUrlRef.current?.value || '',
        shortUrl: shortUrlRef.current?.value || ''
      }
    }).then((response) => {
      props.onCreation(response);
    }).catch((error) => {
      setShowError(true);
    });
  }

  function getError() {
    if (showError) {
      return <div className={styles.formGroup + " " + styles.error}>
        <p>{CONTENT.FORM.ERROR_MESSAGE}</p>
      </div>
    }
    return "";
  }

  return (
          <div className={styles.form}>
            <form className="contentWidth" onSubmit={onSubmit}>
              {getError()}
              <div className={styles.formGroup}>
                <label>{CONTENT.FORM.LONG_URL_LABEL}</label>
                <input
                        placeholder={CONTENT.FORM.LONG_URL_PLACEHOLDER}
                        name="targetUrl"
                        type="url"
                        ref={targetUrlRef}
                />
              </div>
              <div className={styles.formGroup}>
                <label>{CONTENT.FORM.SHORT_URL_LABEL}</label>
                <input
                        placeholder={CONTENT.FORM.SHORT_URL_PLACEHOLDER}
                        name="shortUrl"
                        ref={shortUrlRef}
                />
              </div>
              <div className={styles.formGroup}>
                <button type="submit">{CONTENT.FORM.SUBMIT_BUTTON_TEXT}</button>
              </div>
            </form>
          </div>
  )
}
