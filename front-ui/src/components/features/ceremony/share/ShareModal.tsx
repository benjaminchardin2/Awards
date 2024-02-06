import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import { getGlobalInstance } from 'plume-ts-di';
import { useObservable } from 'micro-observables';
import React from 'react';
import useMessages from '../../../../i18n/hooks/messagesHook';
import SessionService from '../../../../services/session/SessionService';
import CopyToClipboardButton from '../../../layout/CopyToClipboard';

type ShareModalProps = {
  shareLink: string,
  open: boolean,
  toggle: () => void,
};
export default function ShareModal({ shareLink, open, toggle }: ShareModalProps) {
  const { messages } = useMessages();
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());

  return (
    <div className="share-modal">
      <Modal
        open={open}
        onClose={toggle}
        aria-labelledby={messages['share-modal'].title}
        aria-describedby={messages['share-modal'].description}
      >
        <Box>
          <div className="share-modal-title">
            <Typography variant="h6" component="h2">
              {messages['share-modal'].title}
            </Typography>
          </div>
          <div className="share-modal-content">
            <Typography>
              {
                isAuthenticated
                  ? messages['share-modal'].content_connected
                  : messages['share-modal'].content_not_connected
              }
            </Typography>
            <CopyToClipboardButton text={shareLink}/>
          </div>
        </Box>
      </Modal>
    </div>
  );
}
