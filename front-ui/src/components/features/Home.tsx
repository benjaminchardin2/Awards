import React, { useState } from 'react';
import { getGlobalInstance } from 'plume-ts-di';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import {
  Avatar, CardActions, CardHeader, CardMedia, Chip,
} from '@mui/material';
import dayjs from 'dayjs';
import { Link as RouterLink } from 'react-router-dom';
import useMessages from '../../i18n/hooks/messagesHook';
import CeremonyApi, { Ceremony } from '../../api/ceremony/CeremonyApi';
import useLoader, { LoaderState } from '../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted } from '../../lib/react-hooks-alias/ReactHooksAlias';
import useIsMobile from '../../services/hooks/useIsMobile';
import { CEREMONY } from '../Routes';

export default function Home() {
  const { messages } = useMessages();
  const ceremonyApi: CeremonyApi = getGlobalInstance(CeremonyApi);

  const loader: LoaderState = useLoader();
  const [ceremonies, setCeremonies] = useState<Ceremony[] | undefined>(undefined);
  const isMobile: boolean = useIsMobile();

  useOnComponentMounted(() => {
    loader.monitor(
      ceremonyApi
        .findHighlightedCeremonies()
        .then((highlights: Ceremony[]) => {
          setCeremonies(highlights);
        }),
    );
  });

  return (
    <div className="home">
      {ceremonies
        ? <div className="highlight-row">
          {ceremonies
            .map((ceremony: Ceremony) => (
              <Card key={ceremony.id} className="highlight">
                <CardHeader
                  avatar={
                    isMobile ? undefined : <Avatar alt={`award-icon-${ceremony.shortName}`} src={ceremony.avatarUrl}/>
                  }
                  title={ceremony.name}
                  subheader={
                    <>
                      {isMobile && <CardMedia
                        component="img"
                        alt={ceremony.shortName}
                        image={ceremony.pictureUrl}
                      />}
                      {(dayjs(ceremony.ceremonyDate).diff(dayjs()) >= 0)
                        && <Chip
                          size={isMobile ? 'small' : 'medium'}
                          label={messages.home.in(dayjs(ceremony.ceremonyDate))}
                        />
                      }
                    </>
                  }
                />
                <CardContent>
                  <div className="highlight-left">
                    <span className="description">{`${ceremony.description}`}</span>
                    <CardActions>
                        <RouterLink to={`${CEREMONY}/${ceremony.id}`}>
                          {messages.home.pronostic}
                        </RouterLink>
                    </CardActions>
                  </div>
                  {!isMobile && <div className="highlight-right">
                    <CardMedia
                      component="img"
                      alt={ceremony.shortName}
                      image={ceremony.pictureUrl}
                    />
                  </div>}
                </CardContent>
              </Card>
            ))}
        </div> : <></>
      }
    </div>
  );
}
