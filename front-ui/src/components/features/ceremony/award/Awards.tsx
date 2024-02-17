import React from 'react';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../api/ceremony/CeremonyApi';
import useIsMobile from '../../../../services/hooks/useIsMobile';
import AwardsSmallScreen from './screens/AwardsSmallScreen';
import AwardsBigScreen from './screens/AwardsBigScreen';

type AwardType = {
  awards: AwardWithNominees[],
  winnerPicks: { [key: string]: PronosticChoice },
  favoritePicks: { [key: string]: PronosticChoice },
  updateAwardPronosticChoice: (pronosticChoice: PronosticChoice) => void,
  skipChoice: (awardId: string) => void,
};
export default function Awards({
  awards, updateAwardPronosticChoice, winnerPicks, favoritePicks, skipChoice,
}: AwardType) {
  const onPronosticChoice = (
    award: AwardWithNominees,
    type: 'WINNER' | 'FAVORITE',
    nomineeId?: number,
    nominee?: NomineeType,
  ) => {
    if (nomineeId) {
      const pronosticMade: PronosticChoice = {
        nomineeId,
        awardId: award.awardId,
        type,
      };
      updateAwardPronosticChoice(pronosticMade);
    } else if (nominee) {
      const pronosticMade: PronosticChoice = {
        awardId: award.awardId,
        type,
        nominee,
      };
      updateAwardPronosticChoice(pronosticMade);
    }
  };

  const pickTitle = (awardId: number, type?: 'FAVORITE' | 'WINNER') => {
    const award: AwardWithNominees | undefined = awards
      .find((awd: AwardWithNominees) => awd.awardId === awardId);
    const picks: { [key: string]: PronosticChoice } = (type === 'FAVORITE') ? favoritePicks : winnerPicks;
    if (!award) {
      return undefined;
    }
    if (award.type === 'MOVIE') {
      return (picks[awardId]?.nomineeId
        ? awards
          .find((awd: AwardWithNominees) => awd.awardId === awardId)?.nominees
          .find((nominee: NomineeType) => nominee.nomineeId === picks[awardId]?.nomineeId)?.movieTitle
        : picks[awardId]?.nominee?.movieTitle);
    }
    return (picks[awardId]?.nomineeId
      ? awards
        .find((awd: AwardWithNominees) => awd.awardId === awardId)?.nominees
        .find((nominee: NomineeType) => nominee.nomineeId === picks[awardId]?.nomineeId)?.personName
      : picks[awardId]?.nominee?.personName);
  };

  const pickSubTitle = (awardId: number) => {
    const award: AwardWithNominees | undefined = awards
      .find((awd: AwardWithNominees) => awd.awardId === awardId);
    if (!award) {
      return undefined;
    }
    if (award.type === 'MOVIE') {
      return undefined;
    }
    return (winnerPicks[awardId]?.nomineeId
      ? awards
        .find((awd: AwardWithNominees) => awd.awardId === awardId)?.nominees
        .find((nominee: NomineeType) => nominee.nomineeId === winnerPicks[awardId]?.nomineeId)?.movieTitle
      : winnerPicks[awardId]?.nominee?.movieTitle);
  };

  const isMobile: boolean = useIsMobile();

  return !isMobile
    ? <AwardsBigScreen
      awards={awards}
      onPronosticChoice={onPronosticChoice}
      pickTitle={pickTitle}
      pickSubTitle={pickSubTitle}
      favoritePicks={favoritePicks}
      winnerPicks={winnerPicks}
      skipChoice={skipChoice}
    />
    : <AwardsSmallScreen
      awards={awards}
      onPronosticChoice={onPronosticChoice}
      pickTitle={pickTitle}
      favoritePicks={favoritePicks}
      winnerPicks={winnerPicks}
      skipChoice={skipChoice}
    />;
}
