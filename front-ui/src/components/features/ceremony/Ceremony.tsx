import CircularProgress from '@mui/material/CircularProgress';
import { useObservable } from 'micro-observables';
import { getGlobalInstance } from 'plume-ts-di';
import React, { useState } from 'react';
import { NavigateFunction, useNavigate, useParams } from 'react-router-dom';
import CeremonyApi, { AwardWithNominees, PronosticChoice } from '../../../api/ceremony/CeremonyApi';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { useOnComponentMounted, useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import CeremonyPicksService from '../../../services/ceremony/CeremonyPicksService';
import SessionService from '../../../services/session/SessionService';
import { HOME } from '../../Routes';
import Awards from './award/Awards';

export default function Ceremony() {
  const { id } = useParams();
  const navigate: NavigateFunction = useNavigate();
  const numberId: number = Number(id);
  const ceremonyApi: CeremonyApi = getGlobalInstance(CeremonyApi);
  const ceremonyPicksService: CeremonyPicksService = getGlobalInstance(CeremonyPicksService);
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const [awards, setAwards] = useState<AwardWithNominees[] | undefined>(undefined);
  const [userPicks, setUserPicks] = useState<{ [key: string]: PronosticChoice }>({});
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

  return (
    <div className="ceremony">
      {(loader.isLoaded && awards)
        ? <Awards
          awards={awards}
          userPicks={userPicks}
          updateAwardPronosticChoice={updateAwardPronosticChoice}
        /> : (
          <CircularProgress/>
        )}
    </div>
  );
}
