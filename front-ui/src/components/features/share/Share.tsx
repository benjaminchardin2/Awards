import CircularProgress from '@mui/material/CircularProgress';
import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate, useParams } from 'react-router-dom';
import Alert from '@mui/material/Alert';
import ImageList from '@mui/material/ImageList';
import FavoriteIcon from '@mui/icons-material/Favorite';
import {
  ImageListItemBar, ToggleButton, ToggleButtonGroup, Typography,
} from '@mui/material';
import ImageListItem from '@mui/material/ImageListItem';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import { CEREMONY, HOME } from '../../Routes';
import ShareApi, { AwardShare, CeremonyShare } from '../../../api/share/ShareApi';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useMessages from '../../../i18n/hooks/messagesHook';
import useIsMobile from '../../../services/hooks/useIsMobile';
import GradientIcon from '../ceremony/GradientIcon';

export default function Share() {
  const { messages, httpError } = useMessages();
  const { shareCode } = useParams();
  const isMobile: boolean = useIsMobile();
  const navigate: NavigateFunction = useNavigate();
  const shareApi: ShareApi = getGlobalInstance(ShareApi);
  const [type, setType] = useState<'FAVORITE' | 'WINNER'>('WINNER');
  const [ceremonyShare, setCeremonyShare] = useState<CeremonyShare | undefined>(undefined);
  const loader: LoaderState = useLoader();

  if (!shareCode) {
    navigate({ pathname: HOME });
  }

  useOnComponentMounted(() => {
    if (shareCode) {
      loader.monitor(
        shareApi.getCeremonyShare(shareCode)
          .then((ceremonyShareResult: CeremonyShare) => {
            setCeremonyShare(ceremonyShareResult);
            if (ceremonyShareResult.winnerAwardShares.length) {
              setType('WINNER');
            } else {
              setType('FAVORITE');
            }
          }),
      );
    }
  });

  const redirectToCeremony = () => {
    navigate({ pathname: `${CEREMONY}/${ceremonyShare?.ceremonyId}` });
  };

  if (loader.error || (
    ceremonyShare?.winnerAwardShares
    && ceremonyShare.winnerAwardShares.length === 0
    && ceremonyShare?.favoriteAwardShares
    && ceremonyShare.favoriteAwardShares.length === 0
  )) {
    return (
      <div className="share">
        <Alert
          className="form-errors"
          severity="error"
        >
          {loader.error ? httpError(loader.error) : messages['http-errors'].SHARE_NOT_FOUND}
        </Alert>
        <ActionsContainer>
          <ActionButton actionStyle="primary" onClick={() => navigate({ pathname: HOME })}>
            {messages.action.go_home}
          </ActionButton>
        </ActionsContainer>
      </div>
    );
  }

  const awardShares: AwardShare[] | undefined = (type === 'WINNER')
    ? ceremonyShare?.winnerAwardShares
    : ceremonyShare?.favoriteAwardShares;

  const handleChange = (_: React.MouseEvent<HTMLElement>, nextType: 'WINNER' | 'FAVORITE' | undefined) => {
    if (nextType) {
      setType(nextType);
    } else if (type === 'WINNER') {
      setType('FAVORITE');
    } else {
      setType('WINNER');
    }
  };

  const hasChoice: boolean = ceremonyShare?.winnerAwardShares?.length !== 0
    && ceremonyShare?.favoriteAwardShares?.length !== 0;

  return (
    <div className="share">
      {(loader.isLoaded && ceremonyShare)
        ? (
          <>
            <Typography component="h5" variant="h5">{ceremonyShare.ceremonyTitle}</Typography>
            {hasChoice && <ToggleButtonGroup
              value={type}
              exclusive
              onChange={handleChange}
            >
              <ToggleButton value="WINNER" aria-label="list" className="winner">
                <GradientIcon activated={type === 'FAVORITE'}/>
                <Typography variant="subtitle1" component="h5">
                  {messages.share.winner}
                </Typography>
              </ToggleButton>
              <ToggleButton value="FAVORITE" aria-label="module" className="favorites">
                <FavoriteIcon />
                <Typography variant="subtitle1" component="h5">
                  {messages.share.favorites}
                </Typography>
              </ToggleButton>
            </ToggleButtonGroup>}
            <div className="share-list">
            {awardShares && (
              <ImageList cols={isMobile ? 2 : 4} gap={isMobile ? 10 : 50}>
                {awardShares.map((item: AwardShare) => (
                  <ImageListItem key={`${item.awardTitle}|${item.nominee.tmdbMovieId}|${item.nominee.tmdbPersonId}`}>
                    <div className="share-images">
                      <div className="award-tile">
                        <div className="award-tile-content">
                        <div className="award-title">
                          <Typography component="h4">{item.awardTitle}</Typography>
                        </div>
                        <img
                          src={item.nominee.movieMediaUrl}
                          className={(item.awardType === 'CAST') ? 'secondary-image' : 'main-image'}
                          alt={item.nominee.movieTitle}
                        />
                        {item.awardType === 'CAST' && (
                          <img
                            src={item.nominee.personMediaUrl}
                            alt={item.nominee.personName}
                            className='main-image'
                          />
                        )}
                        </div>
                      </div>
                    </div>
                    <ImageListItemBar
                      title={item.awardType === 'MOVIE' ? item.nominee.frenchMovieTitle : item.nominee.personName}
                      subtitle={item.awardType !== 'MOVIE' && (
                            <div className="winner-subtitle">
                              {item.nominee.frenchMovieTitle}
                            </div>
                      )
                      }
                      position="below"
                    />
                  </ImageListItem>
                ))}
              </ImageList>
            )}
            </div>
            {ceremonyShare.ceremonyId && (
              <ActionsContainer>
                <ActionButton actionStyle="primary" onClick={redirectToCeremony}>
                  {messages.action.make_own_pronostics}
                </ActionButton>
              </ActionsContainer>
            )}
          </>
        ) : (
          <CircularProgress/>
        )}
    </div>
  );
}
