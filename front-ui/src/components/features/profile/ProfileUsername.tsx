import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { Check, Close, Edit } from '@mui/icons-material';
import IconButton from '@mui/material/IconButton';
import { FieldError, FormContainer } from 'react-hook-form-mui';
import AccountApi, { ProfileType } from '../../../api/session/AccountApi';
import FormField from '../../theme/form/FormField';
import InputText from '../../theme/form/fields/InputText';
import useMessages from '../../../i18n/hooks/messagesHook';

type ProfileUsernameProps = {
  profile: ProfileType,
  setProfile: React.Dispatch<React.SetStateAction<ProfileType | undefined>>,
};
export default function ProfileUsername({ profile, setProfile }: ProfileUsernameProps) {
  const accountApi: AccountApi = getGlobalInstance(AccountApi);
  const { messages } = useMessages();
  const [editing, setEditing] = useState<boolean>(false);

  const modifyUsername = ((values: { username: string }) => {
    if (profile) {
      accountApi.modifyUsername(values.username)
        .then((response: Response) => response
          .text()
          .then((userHashtag: string) => {
            setEditing(false);
            setProfile({
              ...profile,
              userName: values.username,
              userHashtag,
            });
          }),
        )
        .catch(() => setEditing(false));
    }
  });

  return (
    <div className="profile-header">
      {editing ? (
          <>
            <FormContainer onSuccess={modifyUsername} defaultValues={({ username: profile.userName })}>
              <div className="profile-username-form">
                <div className="profile-username-hashtag">
                  <FormField
                    inputId="username"
                    displayError
                    errorMessageMapping={(e: FieldError) => {
                      if (e.type === 'maxLength') {
                        return messages.rules.maxLengthNoField(255);
                      }
                      return messages.rules.requiredNoField;
                    }}
                  >
                    <InputText
                      type="text"
                      name="username"
                      useNameAsId
                      rules={{ required: true, maxLength: 255 }}
                    />
                  </FormField>
                  <h2>{profile.userHashtag}</h2>
                </div>
                <div className="profile-username-icons">
                  <IconButton
                    type="submit"
                  >
                    <Check/>
                  </IconButton>
                  <IconButton
                    onClick={() => setEditing(false)}
                  >
                    <Close/>
                  </IconButton>
                </div>
              </div>
            </FormContainer>
          </>
      )
        : (
          <>
            <h2>{profile.userName}{profile.userHashtag}</h2>
            <IconButton
              onClick={() => setEditing(true)}
            >
              <Edit/>
            </IconButton>
          </>
        )}
    </div>
  );
}
