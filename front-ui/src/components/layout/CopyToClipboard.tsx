import { Button, Input, Snackbar } from '@mui/material';
import React, { useState } from 'react';
import useMessages from '../../i18n/hooks/messagesHook';

type CopyToClipboardButtonProps = {
  text: string,
};
function CopyToClipboardButton({ text }: CopyToClipboardButtonProps) {
  const { messages } = useMessages();
  const [open, setOpen] = useState(false);
  const handleClick = () => {
    setOpen(true);
    navigator.clipboard.writeText(text);
  };

  return (
    <>
      <div className="copy-to-clipboard-row">
        <Input aria-label="copier le lien" value={text} disabled />
        <Button onClick={handleClick}>Share</Button>
      </div>
      <Snackbar
        open={open}
        onClose={() => setOpen(false)}
        autoHideDuration={2000}
        message={messages.action.copied}
      />
    </>
  );
}

export default CopyToClipboardButton;
