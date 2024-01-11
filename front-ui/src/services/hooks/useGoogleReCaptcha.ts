import React, { createRef, RefObject, useState } from 'react';
import ReCAPTCHA from 'react-google-recaptcha';

type UseGoogleRecatcha = {
  reCaptchaRef: RefObject<ReCAPTCHA>,
  reCaptchaError: boolean,
  setReCaptchaError: React.Dispatch<React.SetStateAction<boolean>>,
  onTokenChange: (token: string | null) => void,
};

const useGoogleReCaptcha = (): UseGoogleRecatcha => {
  const reCaptchaRef: RefObject<ReCAPTCHA> = createRef();
  const [reCaptchaError, setReCaptchaError] = useState<boolean>(false);

  const onTokenChange = (token: string | null) => {
    if (token) {
      setReCaptchaError(false);
    }
  };

  return ({
    reCaptchaRef,
    reCaptchaError,
    setReCaptchaError,
    onTokenChange,
  });
};

export default useGoogleReCaptcha;
