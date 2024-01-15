import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import Alert from '@mui/material/Alert';
import { NavigateFunction, useNavigate } from 'react-router';
import { HttpError } from 'simple-http-rest-client';
import AccountApi, { ProfileType } from '../../../api/session/AccountApi';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import useMessages from '../../../i18n/hooks/messagesHook';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { ACCOUNT_DELETED } from '../../Routes';
import SessionService from '../../../services/session/SessionService';

type ProfilePasswordProps = {
  profile: ProfileType,
};

export default function ProfileDeleteAccount({ profile }: ProfilePasswordProps) {
  const accountApi: AccountApi = getGlobalInstance(AccountApi);
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const { messages, httpError } = useMessages();
  const [editing, setEditing] = useState<boolean>(false);
  const loader: LoaderState = useLoader();
  const navigate: NavigateFunction = useNavigate();
  const deleteAccount = ((values: { password: string }) => {
    if (profile) {
      loader.monitor(accountApi.deleteAccount(values.password)
        .then(() => {
          sessionService.disconnect();
          navigate(ACCOUNT_DELETED);
        })
        .catch((e: HttpError) => {
          throw e;
        }),
      );
    }
  });

  const deleteGoogleAccount = () => {
    if (profile) {
      loader.monitor(accountApi.deleteAccount()
        .then(() => {
          sessionService.disconnect();
          navigate(ACCOUNT_DELETED);
        })
        .catch((e: HttpError) => {
          throw e;
        }),
      );
    }
  };

  const formSchema: yup.ObjectSchema<any> = yup.object().shape({
    password: yup.string()
      .required(messages.rules.required(messages.users.password))
      .min(4, messages.rules.minLength(4, messages.users.password)),
    verifyPassword: yup.string()
      .required(messages.rules.required(messages.users.verify))
      .min(4, messages.rules.minLength(4, messages.users.verify))
      .oneOf([yup.ref('password')], messages.rules.different),
  });

  return (
    <div className="profile-delete">
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
      {editing ? (
          <>
            {profile.isGoogle
              ? (
                <div>
                  <span>{messages.action.account_will_be_deleted}</span>
                  <ActionsContainer>
                    <ActionButton actionStyle="error" onClick={deleteGoogleAccount}>
                      {messages.action.delete_account}
                    </ActionButton>
                    <ActionButton actionStyle="secondary" onClick={() => setEditing(false)}>
                      {messages.action.cancel}
                    </ActionButton>
                  </ActionsContainer>
                </div>
              )
              : (
                <FormContainer onSuccess={deleteAccount} resolver={yupResolver(formSchema)}>
                  <div className="profile-password">
                    <span>{messages.action.confirm_password}</span>
                    <div className="profile-password-inputs">
                      <FormField
                        inputId="password"
                      >
                        <InputText
                          label={messages.users.password}
                          type="password"
                          name="password"
                          useNameAsId
                          rules={{ required: true }}
                        />
                      </FormField>
                      <FormField
                        inputId="verifyPassword"
                      >
                        <InputText
                          label={messages.users.verify}
                          type="password"
                          name="verifyPassword"
                          useNameAsId
                          rules={{ required: true }}
                        />
                      </FormField>
                    </div>
                    <ActionsContainer>
                      <ActionButton actionStyle="error">
                        {messages.action.delete_account}
                      </ActionButton>
                      <ActionButton actionStyle="secondary" onClick={() => setEditing(false)}>
                        {messages.action.cancel}
                      </ActionButton>
                    </ActionsContainer>
                  </div>
                </FormContainer>
              )}
          </>
      )
        : (
          <ActionsContainer>
            <ActionButton actionStyle="error" onClick={() => setEditing(true)}>
              {messages.action.delete_account}
            </ActionButton>
          </ActionsContainer>
        )}
    </div>
  );
}
