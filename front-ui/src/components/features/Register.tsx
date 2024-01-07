import React, { useState } from 'react';
import { getGlobalInstance } from 'plume-ts-di';
import { Alert } from '@mui/material';
import { useObservable } from 'micro-observables';
import { Link, NavigateFunction, useNavigate } from 'react-router-dom';
import { CheckboxElement, FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import useMessages from '../../i18n/hooks/messagesHook';
import useLoader, { LoaderState } from '../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnDependenciesChange } from '../../lib/react-hooks-alias/ReactHooksAlias';
import SessionService from '../../services/session/SessionService';
import { HOME, LOGIN } from '../Routes';
import FormField from '../theme/form/FormField';
import InputText from '../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../theme/action/Actions';
import RegistrationApi, { Registration } from '../../api/session/RegistrationApi';
import useTimeout from '../../lib/plume-http-react-hook-loader/timeoutHook';

export default function Register() {
  const registrationApi: RegistrationApi = getGlobalInstance(RegistrationApi);
  const sessionService: SessionService = getGlobalInstance(SessionService);
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
    rgpd: yup.string()
      .equals([true, 'true'], messages.rules.rgpd),
  });

  const loader: LoaderState = useLoader();
  const [isCreationOk, setCreationOk] = useState<boolean>(false);

  const register = (account: Registration) => {
    loader.monitor(registrationApi.register(account)
      .then(() => {
        setCreationOk(true);
        restartTimeout();
      }));
  };

  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());
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
              <ActionButton isLoading={loader.isLoading} actionStyle="primary">
                {messages.action.register}
              </ActionButton>
            </ActionsContainer>
            {isCreationOk && <div className="login-confirmation">{messages.register.creationOK}</div>}
            <Link to={LOGIN} className="login-link">{messages.login.title}</Link>
          </FormContainer>
        </div>
      </div>
    </div>
  );
}
