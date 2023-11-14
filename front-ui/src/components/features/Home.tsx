import React, { useState } from 'react';
import { getGlobalInstance } from 'plume-ts-di';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import { Avatar, Button, CardActions, CardHeader, CardMedia, Chip, Link } from '@mui/material';
import { Panel } from '../theme/layout/Panel';
import useMessages from '../../i18n/hooks/messagesHook';
import CeremonyApi, { Ceremony } from '../../api/ceremony/CeremonyApi';
import useLoader, { LoaderState } from '../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted } from '../../lib/react-hooks-alias/ReactHooksAlias';
import dayjs from "dayjs";

export default function Home() {
  const { messages } = useMessages();
  const ceremonyApi: CeremonyApi = getGlobalInstance(CeremonyApi);

  const loader: LoaderState = useLoader();
  const [ceremonies, setCeremonies] = useState<Ceremony[] | undefined>(undefined);

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
    <Panel>
      {ceremonies
        ? <div className="highlight-row">
        {ceremonies
          .map((ceremony: Ceremony) => (
          <Card key={ceremony.id} className="highlight">
              <CardHeader
                avatar={
                  <Avatar alt={`award-icon-${ceremony.shortName}`} src={ceremony.avatarUrl} />
                }
                title={ceremony.name}
                subheader={
                <>
                  {(dayjs(ceremony.ceremonyDate).diff(dayjs()) >= 0)
                    && <Chip
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
                <Link underline="hover">
                  {messages.home.pronostic}
                </Link>
              </CardActions>
              </div>
              <div className="highlight-right">

                <CardMedia
                  component="img"
                  alt={ceremony.shortName}
                  image={ceremony.pictureUrl}
                />
              </div>
            </CardContent>
          </Card>
          ))}
      </div> : <></>
      }
    </Panel>
    </div>
  );
}
