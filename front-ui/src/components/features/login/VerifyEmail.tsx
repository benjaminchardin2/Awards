import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import {
  Link, NavigateFunction, useNavigate, useSearchParams,
} from 'react-router-dom';
import { Alert, CircularProgress } from '@mui/material';
import { FormContainer } from 'react-hook-form-mui';
import VerifyEmailApi from '../../../api/verify-email/VerifyEmailApi';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import useMessages from '../../../i18n/hooks/messagesHook';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useTimeout from '../../../lib/plume-http-react-hook-loader/timeoutHook';
import { HOME, LOGIN } from '../../Routes';

export default function VerifyEmail() {
  const [searchParams] = useSearchParams();
  const loader: LoaderState = useLoader();
  const loaderResend: LoaderState = useLoader();
  const navigate: NavigateFunction = useNavigate();
  const [resended, setResended] = useState<boolean>(false);
  const { messages, httpError } = useMessages();
  const token: string | null = searchParams.get('token');
  const verifyEmailApi: VerifyEmailApi = getGlobalInstance(VerifyEmailApi);
  const { restartTimeout: goToLogIn } = useTimeout(() => navigate(LOGIN), 10000, true);
  const { restartTimeout: goToHome } = useTimeout(() => navigate(HOME), 30000, true);

  useOnComponentMounted(() => {
    if (token) {
      loader.monitor(
        verifyEmailApi.verifyEmail(token)
          .then(() => {
            goToLogIn();
          }),
      );
    }
  });

  const resentEmail = (values: { email : string }) => {
    loaderResend.monitor(
      verifyEmailApi.resendEmail(values.email)
        .then(() => {
          setResended(true);
          goToHome();
        })
        .catch(() => {
          setResended(true);
          goToHome();
        }),
    );
  };

  return (
    <div className="login-layout">
      <div className="login-page">
        <div className="login-box">
          <div className="login-label">{messages.titles.verify_email}</div>

            {
              loader.error
              && !resended
              && (
                <div>
                <Alert
                  className="form-errors"
                  severity="error"
                >
                  {httpError(loader.error)}
                </Alert>
                  <FormContainer onSuccess={resentEmail}>
                    <FormField inputId="email">
                      <InputText
                        label={messages.users.email}
                        type="text"
                        name="email"
                        useNameAsId
                        rules={{ required: true }}
                      />
                    </FormField>
                    <ActionsContainer>
                      <ActionButton isLoading={loader.isLoading} actionStyle="primary">
                        {messages.action.resend}
                      </ActionButton>
                    </ActionsContainer>
                  </FormContainer>
                </div>
              )
            }
            {
              loader.isLoaded && !loader.error && (
                <>
                  <Alert
                    className="form-errors"
                    severity="success"
                  >
                    {messages.register.validationOk}
                  </Alert>
                  <Link to={LOGIN} className="login-link">{messages.login.title}</Link>
                </>
              )
            }
            {
              (resended) && (
                <Alert
                  className="form-errors"
                  severity="success"
                >
                  {messages.register.resendOk}
                </Alert>
              )
            }
            {
              loader.isLoading && (
                <div className="loading-progress">
                  <CircularProgress size="100%" color="inherit" />
                </div>
              )
            }
        </div>
      </div>
    </div>
  );
}
