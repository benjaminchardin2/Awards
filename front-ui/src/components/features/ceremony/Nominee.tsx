import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import FavoriteIcon from '@mui/icons-material/Favorite';
import React from 'react';
import { NomineeType } from '../../../api/ceremony/CeremonyApi';
import useMessages from '../../../i18n/hooks/messagesHook';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';

type NomineeProps = {
  onClick: (type: 'WINNER' | 'FAVORITE') => void,
  nominee: NomineeType,
  isSelected?: boolean,
  isFavorite?: boolean,
  awardType: 'MOVIE' | 'CAST' | 'CREW',
};

export default function Nominee({
  nominee, onClick, isSelected, isFavorite, awardType,
}: NomineeProps) {
  const { messages } = useMessages();

  const hasSubheader: boolean = (nominee.movieTitle !== nominee.frenchMovieTitle)
    || (awardType === 'CREW')
    || (awardType === 'CAST');

  return (
    <div className={`nominee ${isSelected ? 'selected' : ''}`}>
      <Card>
        <CardHeader
          title={awardType === 'MOVIE' ? nominee.frenchMovieTitle : nominee.personName}
          subheader={
            hasSubheader
              ? (
                <div className="nominee-alt-title">({nominee.movieTitle})</div>
              )
              : (
                <></>
              )
          }
        />
        <CardContent>
          <div className="nominee-images">
          <CardMedia
            component="img"
            className={(awardType === 'CAST') ? 'secondary-image' : 'main-image'}
            alt={nominee.movieTitle}
            image={nominee.movieMediaUrl}
          />
          {
            (awardType === 'CAST')
            && <CardMedia
              component="img"
              className="main-image"
              alt={nominee.personName}
              image={nominee.personMediaUrl}
            />
          }
          </div>
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
                <ActionButton actionStyle="primary" onClick={() => onClick('WINNER')}>
                  {messages.action.select}
                </ActionButton>
              )}
            {isFavorite
              ? (
                <ActionButton actionStyle="secondary" icon={<FavoriteIcon/>} disabled cssClasses="favorite-selected">
                  {messages.ceremony.favorite}
                </ActionButton>
              )
              : (
                <ActionButton actionStyle="primary" icon={<FavoriteIcon/>} cssClasses="favorite"
                              onClick={() => onClick('FAVORITE')}>
                  {messages.action.favorite}
                </ActionButton>
              )}
          </ActionsContainer>
        </CardActions>
      </Card>
    </div>
  );
}
