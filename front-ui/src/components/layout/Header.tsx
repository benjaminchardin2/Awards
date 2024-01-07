import React from 'react';
import { NavigateFunction, useNavigate } from 'react-router-dom';
import useMessages from '../../i18n/hooks/messagesHook';
import AccountIcon from '../user/AccountIcon';
import { HOME } from '../Routes';

export default function Header() {
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
      <AccountIcon/>
    </div>
  );
}
