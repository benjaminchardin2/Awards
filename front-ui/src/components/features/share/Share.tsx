import CircularProgress from '@mui/material/CircularProgress';
import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate, useParams } from 'react-router-dom';
import Alert from '@mui/material/Alert';
import ImageList from '@mui/material/ImageList';
import { ImageListItemBar, Typography } from '@mui/material';
import ImageListItem from '@mui/material/ImageListItem';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import { CEREMONY, HOME } from '../../Routes';
import ShareApi, { AwardShare, CeremonyShare } from '../../../api/share/ShareApi';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';
import useMessages from '../../../i18n/hooks/messagesHook';
import useIsMobile from '../../../services/hooks/useIsMobile';

export default function Share() {
  const { messages, httpError } = useMessages();
  const { shareCode } = useParams();
  const isMobile: boolean = useIsMobile();
  const navigate: NavigateFunction = useNavigate();
  const shareApi: ShareApi = getGlobalInstance(ShareApi);
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
          }),
      );
    }
  });

  const redirectToCeremony = () => {
    navigate({ pathname: `${CEREMONY}/${ceremonyShare?.ceremonyId}` });
  };

  if (loader.error || (ceremonyShare?.awardShares && ceremonyShare.awardShares.length === 0)) {
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

  return (
    <div className="share">
      {(loader.isLoaded && ceremonyShare)
        ? (
          <>
            <div className="share-list">
            {ceremonyShare.awardShares && (
              <ImageList cols={isMobile ? 2 : 4} gap={isMobile ? 10 : 50}>
                {ceremonyShare.awardShares.map((item: AwardShare) => (
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
                      title={item.awardType === 'MOVIE' ? item.nominee.movieTitle : item.nominee.personName}
                      subtitle={item.awardType !== 'MOVIE' && (
                            <div className="winner-subtitle">
                              {item.nominee.movieTitle}
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
