import React from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';
import { getGlobalInstance } from 'plume-ts-di';
import { useObservable } from 'micro-observables';
import useMessages from '../../i18n/hooks/messagesHook';
import AccountIcon from '../user/AccountIcon';
import { HOME } from '../Routes';
import ConfigurationService from '../../services/configuration/ConfigurationService';

export default function Header() {
  const configurationService: ConfigurationService = getGlobalInstance(ConfigurationService);
  const isAccountEnabled: boolean = useObservable(configurationService.getIsAccountEnabled());
  const { messages } = useMessages();
  const navigate: NavigateFunction = useNavigate();

  const goToHomePage = () => {
    navigate(HOME);
  };

  return (
    <div className="header">
      <div onClick={goToHomePage}>
        <h1>{messages.app.name}</h1>
      </div>
      {isAccountEnabled && <AccountIcon/>}
    </div>
  );
}
