import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import React from 'react';
import { NomineeType } from '../../../api/ceremony/CeremonyApi';
import useMessages from '../../../i18n/hooks/messagesHook';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';

type NomineeProps = {
  onClick: () => void,
  nominee: NomineeType,
  isSelected?: boolean,
};

export default function Nominee({ nominee, onClick, isSelected }: NomineeProps) {
  const { messages } = useMessages();

  return (
    <div className={`nominee ${isSelected ? 'selected' : ''}`}>
      <Card>
        <CardHeader
          title={nominee.movieTitle}
          subheader={
            (nominee.movieTitle !== nominee.frenchMovieTitle)
              ? (
                <div className="nominee-alt-title">({nominee.frenchMovieTitle})</div>
              )
              : (
                <></>
              )
          }
        />
        <CardContent>
          <CardMedia
            component="img"
            alt={nominee.movieTitle}
            image={nominee.movieMediaUrl}
          />
        </CardContent>
        <CardActions>
          <ActionsContainer>
            {isSelected
              ? (
                <ActionButton actionStyle="primary" disabled cssClasses="nominee-selected">
                  {messages.ceremony.selected}
                </ActionButton>
              )
              : (
                <ActionButton actionStyle="primary" onClick={onClick}>
                  {messages.action.select}
                </ActionButton>
              )}
          </ActionsContainer>
        </CardActions>
      </Card>
    </div>
  );
}
