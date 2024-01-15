import { Alert } from '@mui/material';
import React from 'react';
import useMessages from '../../../i18n/hooks/messagesHook';

export default function DeletedAccount() {
  const { messages } = useMessages();

  return (
    <div>
      <Alert
        className="form-errors"
        severity="success"
      >
        {messages.register.deletedOk}
      </Alert>
    </div>
  );
}
