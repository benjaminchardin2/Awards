import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import {
  Link, NavigateFunction, useNavigate, useSearchParams,
} from 'react-router-dom';
import { Alert } from '@mui/material';
import { FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { HttpError } from 'simple-http-rest-client';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import useMessages from '../../../i18n/hooks/messagesHook';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useTimeout from '../../../lib/plume-http-react-hook-loader/timeoutHook';
import { HOME, LOGIN } from '../../Routes';
import ResetPasswordApi from '../../../api/reset-password/ResetPasswordApi';

export default function ChangePassword() {
  const [searchParams] = useSearchParams();
  const loader: LoaderState = useLoader();
  const navigate: NavigateFunction = useNavigate();
  const { messages, httpError } = useMessages();
  const [success, setSuccess] = useState<boolean>(false);
  const token: string | null = searchParams.get('token');
  const resetPasswordApi: ResetPasswordApi = getGlobalInstance(ResetPasswordApi);
  const { restartTimeout: goToLogIn } = useTimeout(() => navigate(LOGIN), 10000, true);
  const { restartTimeout: goToHome } = useTimeout(() => navigate(HOME), 30000, true);

  const formSchema: yup.ObjectSchema<any> = yup.object().shape({
    password: yup.string()
      .required(messages.rules.required(messages.users.password))
      .min(4, messages.rules.minLength(4, messages.users.password)),
    verifyPassword: yup.string()
      .required(messages.rules.required(messages.users.verify))
      .min(4, messages.rules.minLength(4, messages.users.verify))
      .oneOf([yup.ref('password')], messages.rules.different),
  });

  const changePassword = (values: { password : string }) => {
    loader.monitor(
      resetPasswordApi.changePassword({
        newPassword: values.password,
        token,
      })
        .then(() => {
          goToLogIn();
          setSuccess(true);
        })
        .catch((e: HttpError) => {
          goToHome();
          throw e;
        }),
    );
  };

  return (
    <div className="login-layout">
      <div className="login-page">
        <div className="login-box">
          <div className="login-label">{messages.titles.change_password}</div>
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
          {
            loader.isLoaded && !loader.error && success && (
              <>
                <Alert
                  className="form-errors"
                  severity="success"
                >
                  {messages.register.changeOk}
                </Alert>
                <Link to={LOGIN} className="login-link">{messages.login.title}</Link>
              </>
            )
          }
          <FormContainer onSuccess={changePassword} resolver={yupResolver(formSchema)}>
            <FormField inputId="password">
              <InputText
                label={messages.users.password}
                type="password"
                name="password"
                useNameAsId
                rules={{ required: true }}
              />
            </FormField>
            <FormField inputId="verifyPassword">
              <InputText
                label={messages.users.verify}
                type="password"
                name="verifyPassword"
                useNameAsId
                rules={{ required: true }}
              />
            </FormField>
            <ActionsContainer>
              <ActionButton isLoading={loader.isLoading} actionStyle="primary">
                {messages.action.change_password}
              </ActionButton>
            </ActionsContainer>
          </FormContainer>
        </div>
      </div>
    </div>
  );
}
