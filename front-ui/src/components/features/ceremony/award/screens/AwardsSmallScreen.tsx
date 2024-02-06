import Typography from '@mui/material/Typography';
import React from 'react';
import SwipeableViews from 'react-swipeable-views';
import MobileStepper from '@mui/material/MobileStepper';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../../api/ceremony/CeremonyApi';
import Slider from '../../../../layout/Slider';
import Nominee from '../../Nominee';
import useMessages from '../../../../../i18n/hooks/messagesHook';
import useSwipeableIndex, { SwipeableIndex } from '../../../../../services/hooks/useSwipeableIndex';
import { useOnDependenciesChange } from '../../../../../lib/react-hooks-alias/ReactHooksAlias';

type AwardsSmallScreenType = {
  awards: AwardWithNominees[],
  userPicks: { [key: string]: PronosticChoice },
  onPronosticChoice: (award: AwardWithNominees, nomineeId?: number) => void,
  pickTitle: (awardId: number) => string | undefined,
};
export default function AwardsSmallScreen({
  awards, userPicks, onPronosticChoice, pickTitle,
}: AwardsSmallScreenType) {
  const { messages } = useMessages();
  const awardsSwipeableIndex: SwipeableIndex = useSwipeableIndex(awards);
  const award: AwardWithNominees = awards[awardsSwipeableIndex.index];
  const nomineeSwipeableIndex: SwipeableIndex = useSwipeableIndex(award.nominees);

  useOnDependenciesChange(() => {
    nomineeSwipeableIndex.setIndex(0);
  }, [awardsSwipeableIndex.index]);

  return (
    <div className="awards">
      <div className="slider-header">
        <SwipeableViews
          index={awardsSwipeableIndex.index}
          onChangeIndex={(newIndex: number) => awardsSwipeableIndex.setIndex(newIndex)}
          enableMouseEvents
        >
          {
            awards
              .map((awardhd: AwardWithNominees) => (
                <Slider
                  key={`slider-${awardhd.awardId}`}
                  next={awardsSwipeableIndex.onNext}
                  previous={awardsSwipeableIndex.onPrevious}
                >
                  <div className="awards-slider">
                    <Typography variant="subtitle2">
                      {`${awardsSwipeableIndex.index + 1} / ${awards.length}`}
                    </Typography>
                    <Typography variant="h6" component="h6">{awardhd.awardName}</Typography>
                  </div>
                </Slider>
              ))
          }
        </SwipeableViews>
      </div>
      {
        award && (
          <>
            <div className='main-slider'>
              <div className='nominee-part'>
              <SwipeableViews
                index={nomineeSwipeableIndex.index}
                onChangeIndex={(newIndex: number) => nomineeSwipeableIndex.setIndex(newIndex)}
                enableMouseEvents
              >
                {
                  award.nominees
                    .map((nominee: NomineeType) => (
                      <Slider
                        key={`${award.awardId}|${nominee.tmdbMovieId}|${nominee.tmdbPersonId}`}
                        next={nomineeSwipeableIndex.onNext}
                        previous={nomineeSwipeableIndex.onPrevious}
                        style='chevron'
                      >
                        <Nominee
                          nominee={nominee}
                          isSelected={userPicks[award.awardId]?.nomineeId === nominee.nomineeId}
                          onClick={() => onPronosticChoice(award, nominee.nomineeId)}
                          awardType={award.type}
                        />
                      </Slider>
                    ))
                }
              </SwipeableViews>
                <div className="award-choice-indicator">
                  <div className="nominee-stepper">
                    <MobileStepper
                      variant="dots"
                      steps={award.nominees.length}
                      position="static"
                      activeStep={nomineeSwipeableIndex.index}
                      nextButton={<></>}
                      backButton={<></>}
                    />
                  </div>
                  <Typography variant="subtitle1" component="h5">
                    {messages.ceremony.yourChoice(pickTitle(award.awardId))}
                  </Typography>
                </div>
              </div>
            </div>

          </>
        )
      }
    </div>
  );
}
