import React from 'react';
import { getGlobalInstance } from 'plume-ts-di';
import { Alert } from '@mui/material';
import { useObservable } from 'micro-observables';
import { Link, NavigateFunction, useNavigate } from 'react-router-dom';
import { FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import useMessages from '../../../i18n/hooks/messagesHook';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import { SessionCredentials } from '../../../api/session/SessionApi';
import SessionService from '../../../services/session/SessionService';
import { HOME, REGISTER, RESET_PASSWORD } from '../../Routes';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import GoogleLogin from './GoogleLogin';
import GoogleService from '../../../services/session/GoogleService';

export default function Login() {
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const googleService: GoogleService = getGlobalInstance(GoogleService);
  const { messages, httpError } = useMessages();
  const navigate: NavigateFunction = useNavigate();

  const loader: LoaderState = useLoader();

  const formSchema: yup.ObjectSchema<any> = yup.object().shape({
    password: yup.string()
      .required(messages.rules.required(messages.users.password)),
    email: yup.string().required(messages.rules.required(messages.users.email)),
  });

  const tryAuthenticate = (credentials: SessionCredentials) => {
    loader.monitor(sessionService.authenticate(credentials));
  };

  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());
  const isGoogleInError: boolean = useObservable(googleService.getIsInError());
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
          <div className="login-label">{messages.login.title}</div>
          <FormContainer onSuccess={tryAuthenticate} resolver={yupResolver(formSchema)}>
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
            <ActionsContainer>
              <ActionButton isLoading={loader.isLoading} actionStyle="primary">
                {messages.action.authenticate}
              </ActionButton>
              {!isGoogleInError && <GoogleLogin/>}
            </ActionsContainer>

            <div className="alternative-actions">
              <Link className="login-link" to={REGISTER}>{messages.register.title}</Link>
            </div>
            <Link className="reset-link" to={RESET_PASSWORD}>{messages.register.password_forgotten}</Link>
          </FormContainer>
        </div>
      </div>
    </div>
  );
}
