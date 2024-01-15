import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { FormContainer } from 'react-hook-form-mui';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { HttpError } from 'simple-http-rest-client';
import Alert from '@mui/material/Alert';
import { ProfileType } from '../../../api/session/AccountApi';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import useMessages from '../../../i18n/hooks/messagesHook';
import PasswordApi from '../../../api/reset-password/PasswordApi';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import useTimeout from '../../../lib/plume-http-react-hook-loader/timeoutHook';

type ProfilePasswordProps = {
  profile: ProfileType,
};

export default function ProfilePassword({ profile }: ProfilePasswordProps) {
  const passwordApi: PasswordApi = getGlobalInstance(PasswordApi);
  const { messages, httpError } = useMessages();
  const [editing, setEditing] = useState<boolean>(false);
  const [showSuccess, setShowSuccess] = useState<boolean>(false);
  const loader: LoaderState = useLoader();
  const { restartTimeout: unshowSuccess } = useTimeout(() => setShowSuccess(false), 5000, true);
  const modifyPassword = ((values: { newPassword: string, oldPassword: string }) => {
    if (profile) {
      loader.monitor(passwordApi.modifyPassword(values)
        .then(() => {
          setEditing(false);
          setShowSuccess(true);
          unshowSuccess();
        })
        .catch((e: HttpError) => {
          setEditing(false);
          throw e;
        }));
    }
  });

  const formSchema: yup.ObjectSchema<any> = yup.object().shape({
    oldPassword: yup.string()
      .required(messages.rules.required(messages.users.password))
      .min(4, messages.rules.minLength(4, messages.users.password)),
    verifyPassword: yup.string()
      .required(messages.rules.required(messages.users.verify))
      .min(4, messages.rules.minLength(4, messages.users.verify))
      .oneOf([yup.ref('newPassword')], messages.rules.different),
    newPassword: yup.string()
      .required(messages.rules.required(messages.users.password))
      .min(4, messages.rules.minLength(4, messages.users.password)),
  });

  return (
    <div className="profile-password">
      {
        loader.isLoaded && !loader.error && showSuccess && (
          <>
            <Alert
              className="form-errors"
              severity="success"
            >
              {messages.register.changeOk}
            </Alert>
          </>
        )
      }
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
            <FormContainer onSuccess={modifyPassword} resolver={yupResolver(formSchema)}>
              <div className="profile-password">
                <div className="profile-password-inputs">
                  <FormField
                    inputId="oldPassword"
                  >
                    <InputText
                      label={messages.users.password_actual}
                      type="password"
                      name="oldPassword"
                      useNameAsId
                      rules={{ required: true }}
                    />
                  </FormField>
                  <FormField
                    inputId="newPassword"
                  >
                    <InputText
                      label={messages.users.password_new}
                      type="password"
                      name="newPassword"
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
                  <ActionButton actionStyle="primary">
                    {messages.action.save}
                  </ActionButton>
                  <ActionButton actionStyle="secondary" onClick={() => setEditing(false)}>
                    {messages.action.cancel}
                  </ActionButton>
                </ActionsContainer>
              </div>
            </FormContainer>
          </>
      )
        : (
          <ActionsContainer>
            <ActionButton actionStyle="primary" onClick={() => setEditing(true)}>
              {messages.action.change_password}
            </ActionButton>
          </ActionsContainer>
        )}
    </div>
  );
}
