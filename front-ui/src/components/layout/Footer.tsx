import React from 'react';
import { Link } from 'react-router-dom';
import useMessages from '../../i18n/hooks/messagesHook';
import { MENTIONS_LEGALES, RGPD } from '../Routes';

export default function Footer() {
  const { messages } = useMessages();

  return (
    <div className="footer">
      <Link to={RGPD}>{messages.footer.rgpd}</Link>
      <Link to={MENTIONS_LEGALES}>{messages.footer.legal}</Link>
    </div>
  );
}
