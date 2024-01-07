import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';
import { Alert } from '@mui/material';
import { FormContainer } from 'react-hook-form-mui';
import useLoader, { LoaderState } from '../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import useMessages from '../../i18n/hooks/messagesHook';
import FormField from '../theme/form/FormField';
import InputText from '../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../theme/action/Actions';
import useTimeout from '../../lib/plume-http-react-hook-loader/timeoutHook';
import { HOME } from '../Routes';
import ResetPasswordApi from '../../api/reset-password/ResetPasswordApi';

export default function ResetPassword() {
  const loader: LoaderState = useLoader();
  const navigate: NavigateFunction = useNavigate();
  const { messages } = useMessages();
  const [success, setSuccess] = useState<boolean>(false);
  const resetPasswordApi: ResetPasswordApi = getGlobalInstance(ResetPasswordApi);
  const { restartTimeout: goToHome } = useTimeout(() => navigate(HOME), 30000, true);

  const resetPassword = (values: { email : string }) => {
    loader.monitor(
      resetPasswordApi.resetPassword(values.email)
        .then(() => {
          goToHome();
          setSuccess(true);
        })
        .catch(() => {
          goToHome();
        }),
    );
  };

  return (
    <div className="login-layout">
      <div className="login-page">
        <div className="login-box">
          <div className="login-label">{messages.titles.reset_password}</div>
          {
            loader.isLoaded && (loader.error || success) && (
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
