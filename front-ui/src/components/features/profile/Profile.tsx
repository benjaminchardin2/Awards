import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import { useOnComponentMounted } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import AccountApi, { ProfileType } from '../../../api/session/AccountApi';
import useMessages from '../../../i18n/hooks/messagesHook';
import ProfileUsername from './ProfileUsername';
import ProfilePassword from './ProfilePassword';
import ProfileDeleteAccount from './ProfileDeleteAccount';

export default function Profile() {
  const accountApi: AccountApi = getGlobalInstance(AccountApi);
  const { messages } = useMessages();
  const [profile, setProfile] = useState<ProfileType | undefined>(undefined);

  useOnComponentMounted(() => {
    accountApi.getProfile()
      .then((profileResult: ProfileType) => {
        setProfile(profileResult);
      });
  });

  return (
    <div className="profile">
      {profile && (
        <Card>
          <CardHeader
            title={messages.titles.account}
          />
          <CardContent>
            <ProfileUsername profile={profile} setProfile={setProfile}/>
            <div>
              <span>{messages.users.email}<span>&nbsp;:&nbsp;</span><b>{profile.email}</b></span>
            </div>
            {!profile.isGoogle && <ProfilePassword profile={profile} />}
            <ProfileDeleteAccount profile={profile} />
          </CardContent>
        </Card>
      )}
    </div>
  );
}
