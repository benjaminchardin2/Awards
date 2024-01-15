import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';
import { Alert } from '@mui/material';
import { FormContainer } from 'react-hook-form-mui';
import { useObservable } from 'micro-observables';
import ReCAPTCHA from 'react-google-recaptcha';
import { HttpError } from 'simple-http-rest-client';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import useMessages from '../../../i18n/hooks/messagesHook';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useTimeout from '../../../lib/plume-http-react-hook-loader/timeoutHook';
import { HOME } from '../../Routes';
import PasswordApi from '../../../api/reset-password/PasswordApi';
import useGoogleReCaptcha from '../../../services/hooks/useGoogleReCaptcha';
import GoogleService from '../../../services/session/GoogleService';
import { GoogleConfiguration } from '../../../api/configuration/ConfigurationApi';

export default function ResetPassword() {
  const loader: LoaderState = useLoader();
  const navigate: NavigateFunction = useNavigate();
  const { messages, httpError } = useMessages();
  const [success, setSuccess] = useState<boolean>(false);
  const resetPasswordApi: PasswordApi = getGlobalInstance(PasswordApi);
  const { restartTimeout: goToHome } = useTimeout(() => navigate(HOME), 30000, true);
  const googleService: GoogleService = getGlobalInstance(GoogleService);
  const {
    reCaptchaRef,
    reCaptchaError,
    setReCaptchaError,
    onTokenChange,
  } = useGoogleReCaptcha();
  const googleConfiguration: GoogleConfiguration | undefined = useObservable(googleService.getGoogleConfiguration());

  const resetPassword = (values: { email: string }) => {
    const reCaptchaResponse: string | null | undefined = reCaptchaRef.current?.getValue();
    if (reCaptchaResponse) {
      loader.monitor(
        resetPasswordApi.resetPassword(values.email, reCaptchaResponse)
          .then(() => {
            goToHome();
            setSuccess(true);
          })
          .catch((error: HttpError) => {
            if (error.errorCode === 'RECAPTCHA_ERROR') {
              // eslint-disable-next-line @typescript-eslint/ban-ts-comment
              // @ts-ignore
              window.grecaptcha.reset();
              throw error;
            } else {
              setSuccess(true);
              goToHome();
            }
          }),
      );
    } else {
      setReCaptchaError(true);
    }
  };

  return (
    <div className="login-layout">
      <div className="login-page">
        <div className="login-box">
          <div className="login-label">{messages.titles.reset_password}</div>
          {
            loader.isLoaded && (success) && (
              <>
                <Alert
                  className="form-errors"
                  severity="success"
                >
                  {messages.register.resetOk}
                </Alert>
              </>
            )
          }
          {
            loader.error && (
              <>
                <Alert
                  className="form-errors"
                  severity="error"
                >
                  {httpError(loader.error)}
                </Alert>
              </>
            )
          }
          <FormContainer onSuccess={resetPassword}>
            <FormField inputId="email">
              <InputText
                label={messages.users.email}
                type="email"
                name="email"
                useNameAsId
                rules={{ required: true }}
              />
            </FormField>
            <ActionsContainer>
              {googleConfiguration
                && <div className="recaptcha-container">
                  <ReCAPTCHA
                    ref={reCaptchaRef}
                    sitekey={googleConfiguration.reCaptchaPublicKey}
                    theme="dark"
                    onChange={onTokenChange}
                  />
                </div>
              }
              {reCaptchaError && <div className="recaptcha-error">{messages.google.recaptcha}</div>}
              <ActionButton isLoading={loader.isLoading} actionStyle="primary">
                {messages.action.reset_password}
              </ActionButton>
            </ActionsContainer>
          </FormContainer>
        </div>
      </div>
    </div>
  );
}
