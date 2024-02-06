import CircularProgress from '@mui/material/CircularProgress';
import { useObservable } from 'micro-observables';
import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate, useParams } from 'react-router-dom';
import Fab from '@mui/material/Fab';
import ShareIcon from '@mui/icons-material/Share';
import CeremonyApi, { AwardWithNominees, PronosticChoice } from '../../../api/ceremony/CeremonyApi';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted, useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import CeremonyPicksService from '../../../services/ceremony/CeremonyPicksService';
import SessionService from '../../../services/session/SessionService';
import { HOME } from '../../Routes';
import Awards from './award/Awards';
import ShareModal from './share/ShareModal';
import useToggle from '../../../lib/react-hook-toggle/ReactHookToggle';

export default function Ceremony() {
  const { id } = useParams();
  const navigate: NavigateFunction = useNavigate();
  const numberId: number = Number(id);
  const ceremonyApi: CeremonyApi = getGlobalInstance(CeremonyApi);
  const ceremonyPicksService: CeremonyPicksService = getGlobalInstance(CeremonyPicksService);
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const [awards, setAwards] = useState<AwardWithNominees[] | undefined>(undefined);
  const [userPicks, setUserPicks] = useState<{ [key: string]: PronosticChoice }>({});
  const [shareLink, setShareLink] = useState<string>();
  const [open, toggle] = useToggle(false);
  const loader: LoaderState = useLoader();
  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());

  if (Number.isNaN(numberId) || loader.error) {
    navigate({ pathname: HOME });
  }

  useOnComponentMounted(() => {
    if (!Number.isNaN(numberId)) {
      loader.monitor(
        ceremonyApi.findCeremonyAwards(numberId)
          .then((awardsResult: AwardWithNominees[]) => {
            setAwards(awardsResult);
          }),
      );
    }
  });

  useOnDependenciesChange(() => {
    if (!Number.isNaN(numberId)) {
      ceremonyPicksService.findCeremonyPicks(numberId, isAuthenticated)
        .then((userChoices: { [key: string]: PronosticChoice } | undefined) => {
          setUserPicks(userChoices || {});
        });
    }
  }, [isAuthenticated, numberId]);

  const updateAwardPronosticChoice = (pronosticChoice: PronosticChoice) => {
    ceremonyPicksService
      .saveCeremonyPicks(numberId, isAuthenticated, pronosticChoice)
      .then(() => {
        setUserPicks({
          ...userPicks,
          [pronosticChoice.awardId]: pronosticChoice,
        });
      });
  };

  const getShareLink = () => {
    ceremonyPicksService
      .getShareLink(numberId, isAuthenticated)
      .then((code: string) => {
        setShareLink(code);
        toggle();
      });
  };

  return (
    <div className="ceremony">
      {(loader.isLoaded && awards)
        ? (
          <>
            <Awards
              awards={awards}
              userPicks={userPicks}
              updateAwardPronosticChoice={updateAwardPronosticChoice}
            />
            <div className="sticky-button">
              <Fab size="medium" onClick={getShareLink} disabled={Object.values(userPicks).length === 0}>
                <ShareIcon/>
              </Fab>
            </div>
            <ShareModal
              open={open}
              shareLink={shareLink!}
              toggle={toggle}
            />
          </>
        ) : (
          <CircularProgress/>
        )}
    </div>
  );
}
