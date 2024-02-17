import Typography from '@mui/material/Typography';
import React from 'react';
import SwipeableViews from 'react-swipeable-views';
import MobileStepper from '@mui/material/MobileStepper';
import { Button } from '@mui/material';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../../api/ceremony/CeremonyApi';
import Slider from '../../../../layout/Slider';
import Nominee from '../../Nominee';
import useMessages from '../../../../../i18n/hooks/messagesHook';
import useSwipeableIndex, { SwipeableIndex } from '../../../../../services/hooks/useSwipeableIndex';
import { useOnDependenciesChange } from '../../../../../lib/react-hooks-alias/ReactHooksAlias';
import NomineeSearch from '../../NomineeSearch';

type AwardsSmallScreenType = {
  awards: AwardWithNominees[],
  winnerPicks: { [key: string]: PronosticChoice },
  favoritePicks: { [key: string]: PronosticChoice },
  onPronosticChoice: (
    award: AwardWithNominees,
    type: 'FAVORITE' | 'WINNER',
    nomineeId?: number,
    nominee?: NomineeType
  ) => void,
  pickTitle: (awardId: number, type?: 'WINNER' | 'FAVORITE') => string | undefined,
  skipChoice: (awardId: string) => void,
};
export default function AwardsSmallScreen({
  awards, winnerPicks, favoritePicks, onPronosticChoice, pickTitle, skipChoice,
}: AwardsSmallScreenType) {
  const { messages } = useMessages();
  const awardsSwipeableIndex: SwipeableIndex = useSwipeableIndex(awards);
  const award: AwardWithNominees = awards[awardsSwipeableIndex.index];
  const nomineeSwipeableIndex: SwipeableIndex = useSwipeableIndex(award.nominees, award.nominees.length + 1);

  useOnDependenciesChange(() => {
    nomineeSwipeableIndex.setIndex(0);
  }, [awardsSwipeableIndex.index]);

  const hasPick: boolean = !!(award && (winnerPicks[award.awardId] || favoritePicks[award.awardId]));

  const skipChoiceAndMoveOn = () => {
    if (hasPick) {
      skipChoice(award.awardId.toString());
    }
    if (!hasPick) {
      awardsSwipeableIndex.onNext();
    }
  };

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
                        key={`${award.awardId}|${nominee.tmdbMovieId}|${nominee.tmdbPersonId || nominee.personName}`}
                        next={nomineeSwipeableIndex.onNext}
                        previous={nomineeSwipeableIndex.onPrevious}
                        style='chevron'
                      >
                        <Nominee
                          nominee={nominee}
                          isSelected={winnerPicks[award.awardId]?.nomineeId === nominee.nomineeId}
                          isFavorite={favoritePicks[award.awardId]?.nomineeId === nominee.nomineeId}
                          onClick={(type: 'FAVORITE' | 'WINNER') => onPronosticChoice(award, type, nominee.nomineeId)}
                          awardType={award.type}
                        />
                      </Slider>
                    ))
                }
                <Slider
                  key={`${award.awardId}|search`}
                  next={nomineeSwipeableIndex.onNext}
                  previous={nomineeSwipeableIndex.onPrevious}
                  style='chevron'
                >
                  <NomineeSearch
                    onClick={(nominee: NomineeType) => onPronosticChoice(award, 'FAVORITE', undefined, nominee)}
                    awardType={award.type}
                    awardId={award.awardId}
                    nominee={favoritePicks[award.awardId]?.nominee}
                    skipChoice={skipChoice}
                  />
                </Slider>
              </SwipeableViews>
                <div className="award-choice-indicator">
                  <div className="nominee-stepper">
                    <MobileStepper
                      variant="dots"
                      steps={award.nominees.length + 1}
                      position="static"
                      activeStep={nomineeSwipeableIndex.index}
                      nextButton={<></>}
                      backButton={<></>}
                    />
                  </div>
                  <div className="award-choice-text">
                  <Typography variant="subtitle1" component="h5">
                    {messages.ceremony.yourChoice(pickTitle(award.awardId, 'WINNER'))}
                  </Typography>
                  </div>
                  <div className="award-choice-text">
                  <Typography variant="subtitle1" component="h5">
                    {messages.ceremony.yourFavorite(pickTitle(award.awardId, 'FAVORITE'))}
                  </Typography>
                  </div>
                </div>
              </div>
            </div>
            <Button variant="text" onClick={skipChoiceAndMoveOn}>
              {hasPick ? messages.action.delete_choice : messages.action.skip_choice}
            </Button>
          </>
        )
      }
    </div>
  );
}
