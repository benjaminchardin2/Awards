import React, { useState } from 'react';
import { getGlobalInstance } from 'plume-ts-di';
import { Alert } from '@mui/material';
import { useObservable } from 'micro-observables';
import { Link, NavigateFunction, useNavigate } from 'react-router-dom';
import { CheckboxElement, FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import ReCAPTCHA from 'react-google-recaptcha';
import { HttpError } from 'simple-http-rest-client';
import useMessages from '../../../i18n/hooks/messagesHook';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import SessionService from '../../../services/session/SessionService';
import { HOME, LOGIN } from '../../Routes';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import RegistrationApi, { Registration } from '../../../api/session/RegistrationApi';
import useTimeout from '../../../lib/plume-http-react-hook-loader/timeoutHook';
import GoogleLogin from './GoogleLogin';
import GoogleService from '../../../services/session/GoogleService';
import { GoogleConfiguration } from '../../../api/configuration/ConfigurationApi';
import useGoogleReCaptcha from '../../../services/hooks/useGoogleReCaptcha';

export default function Register() {
  const registrationApi: RegistrationApi = getGlobalInstance(RegistrationApi);
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const googleService: GoogleService = getGlobalInstance(GoogleService);
  const {
    reCaptchaRef,
    reCaptchaError,
    setReCaptchaError,
    onTokenChange,
  } = useGoogleReCaptcha();
  const { messages, httpError } = useMessages();
  const navigate: NavigateFunction = useNavigate();
  const { restartTimeout } = useTimeout(() => navigate(LOGIN), 30000, true);

  const formSchema: yup.ObjectSchema<any> = yup.object().shape({
    password: yup.string()
      .required(messages.rules.required(messages.users.password))
      .min(4, messages.rules.minLength(4, messages.users.password)),
    verifyPassword: yup.string()
      .required(messages.rules.required(messages.users.verify))
      .min(4, messages.rules.minLength(4, messages.users.verify))
      .oneOf([yup.ref('password')], messages.rules.different),
    userName: yup.string().required(messages.rules.required(messages.users.userName)),
    email: yup.string().required(messages.rules.required(messages.users.email)),
    rgpd: yup.boolean()
      .equals([true], messages.rules.rgpd),
  });

  const loader: LoaderState = useLoader();
  const [isCreationOk, setCreationOk] = useState<boolean>(false);

  const register = (account: Registration) => {
    if (reCaptchaRef.current?.getValue()) {
      loader.monitor(registrationApi.register(account, reCaptchaRef.current?.getValue())
        .then(() => {
          setCreationOk(true);
          restartTimeout();
        })
        .catch((error: HttpError) => {
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-ignore
          window.grecaptcha.reset();
          throw error;
        }));
    } else {
      setReCaptchaError(true);
    }
  };

  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());
  const isGoogleInError: boolean = useObservable(googleService.getIsInError());
  const googleConfiguration: GoogleConfiguration | undefined = useObservable(googleService.getGoogleConfiguration());
  useOnDependenciesChange(() => {
    if (isAuthenticated) {
      navigate({ pathname: HOME });
    }
  }, [isAuthenticated, navigate]);

  return (
    <div className="login-layout">
      <div className="login-page">
        <div className="login-box">
          {
            loader.error
            && (
              <Alert
                className="form-errors"
                severity="error"
              >
                {httpError(loader.error)}
              </Alert>
            )
          }
          <div className="login-label">{messages.register.title}</div>
          <FormContainer onSuccess={register} resolver={yupResolver(formSchema)}>
            <FormField inputId="userName">
              <InputText
                label={messages.users.userName}
                type="text"
                name="userName"
                useNameAsId
              />
            </FormField>
            <FormField inputId="email">
              <InputText
                label={messages.users.email}
                type="email"
                name="email"
                useNameAsId
              />
            </FormField>
            <FormField inputId="password">
              <InputText
                label={messages.users.password}
                type="password"
                name="password"
                autoComplete="off"
                useNameAsId
              />
            </FormField>
            <FormField inputId="verifyPassword">
              <InputText
                label={messages.users.verify}
                type="password"
                name="verifyPassword"
                autoComplete="off"
                useNameAsId
              />
            </FormField>
            <FormField inputId="rgpd" displayError>
              <CheckboxElement
                label={messages.users.rgpd}
                labelProps={{
                  labelPlacement: 'start',
                }}
                name="rgpd"
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
                {messages.action.register}
              </ActionButton>
            </ActionsContainer>
            {isCreationOk && <div className="login-confirmation">{messages.register.creationOK}</div>}
            <div className="alternative-actions">
              {!isGoogleInError && <GoogleLogin/>}
              <Link to={LOGIN} className="login-link">{messages.login.title}</Link>
            </div>
          </FormContainer>
        </div>
      </div>
    </div>
  );
}
