import React from 'react';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../api/ceremony/CeremonyApi';
import useIsMobile from '../../../../services/hooks/useIsMobile';
import AwardsSmallScreen from './screens/AwardsSmallScreen';
import AwardsBigScreen from './screens/AwardsBigScreen';

type AwardType = {
  awards: AwardWithNominees[],
  userPicks: { [key: string]: PronosticChoice },
  updateAwardPronosticChoice: (pronosticChoice: PronosticChoice) => void,
};
export default function Awards({
  awards, updateAwardPronosticChoice, userPicks,
}: AwardType) {
  const onPronosticChoice = (award: AwardWithNominees, nomineeId?: number) => {
    if (nomineeId) {
      const pronosticMade: PronosticChoice = {
        nomineeId,
        awardId: award.awardId,
      };
      updateAwardPronosticChoice(pronosticMade);
    }
  };

  const pickTitle = (awardId: number) => (userPicks[awardId]?.nomineeId
    ? awards
      .find((awd: AwardWithNominees) => awd.awardId === awardId)?.nominees
      .find((nominee: NomineeType) => nominee.nomineeId === userPicks[awardId]?.nomineeId)?.movieTitle
    : userPicks[awardId]?.nominee?.movieTitle);

  const isMobile: boolean = useIsMobile();

  return !isMobile
    ? <AwardsBigScreen
      awards={awards}
      onPronosticChoice={onPronosticChoice}
      pickTitle={pickTitle}
      userPicks={userPicks}
    />
    : <AwardsSmallScreen
      awards={awards}
      onPronosticChoice={onPronosticChoice}
      pickTitle={pickTitle}
      userPicks={userPicks}
    />;
}
