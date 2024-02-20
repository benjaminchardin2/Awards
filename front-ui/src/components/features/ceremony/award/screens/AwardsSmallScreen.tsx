import Typography from '@mui/material/Typography';
import React, { ComponentType } from 'react';
import SwipeableViews, { SwipeableViewsProps } from 'react-swipeable-views';
import { SlideRenderProps, virtualize, WithVirtualizeProps } from 'react-swipeable-views-utils';
import { mod } from 'react-swipeable-views-core';
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

const VirtualizeSwipeableViews: ComponentType<SwipeableViewsProps & WithVirtualizeProps> = virtualize(SwipeableViews);

export default function AwardsSmallScreen({
  awards, winnerPicks, favoritePicks, onPronosticChoice, pickTitle, skipChoice,
}: AwardsSmallScreenType) {
  const { messages } = useMessages();
  const awardsSwipeableIndex: SwipeableIndex = useSwipeableIndex();
  const modAwardsIndex: number = mod(awardsSwipeableIndex.index, awards.length);
  const award: AwardWithNominees = awards[modAwardsIndex];
  const nomineeSwipeableIndex: SwipeableIndex = useSwipeableIndex();
  const modNomineeIndex: number = mod(nomineeSwipeableIndex.index, (award.nominees.length + 1));

  useOnDependenciesChange(() => {
    nomineeSwipeableIndex.setIndex(0);
  }, [modAwardsIndex]);

  const hasPick: boolean = !!(award && (winnerPicks[award.awardId] || favoritePicks[award.awardId]));

  const skipChoiceAndMoveOn = () => {
    if (hasPick) {
      skipChoice(award.awardId.toString());
    }
    if (!hasPick) {
      awardsSwipeableIndex.onNext();
    }
  };

  const awardSliderRenderer = (params: SlideRenderProps) => {
    const index: number = mod(params.index, awards.length);
    const awardhd: AwardWithNominees = awards[index];
    return (<Slider
      key={params.key}
      next={awardsSwipeableIndex.onNext}
      previous={awardsSwipeableIndex.onPrevious}
    >
      <div className="awards-slider">
        <Typography variant="subtitle2">
          {`${index + 1} / ${awards.length}`}
        </Typography>
        <Typography variant="h6" component="h6">{awardhd.awardName}</Typography>
      </div>
    </Slider>);
  };

  const nomineeSliderRenderer = (params: SlideRenderProps) => {
    const index: number = mod(params.index, (award.nominees.length + 1));
    if (index !== award.nominees.length) {
      const nominee: NomineeType = award.nominees[index];
      return (
        <Slider
          key={params.key}
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
      );
    }
    return (
      <Slider
        key={params.key}
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
    );
  };

  return (
    <div className="awards">
      <div className="slider-header">
        <VirtualizeSwipeableViews
          index={awardsSwipeableIndex.index}
          onChangeIndex={(newIndex: number) => awardsSwipeableIndex.setIndex(newIndex)}
          slideRenderer={awardSliderRenderer}
          enableMouseEvents
        />
      </div>
      {
        award && (
          <>
            <div className='main-slider'>
              <div className='nominee-part'>
                <VirtualizeSwipeableViews
                index={nomineeSwipeableIndex.index}
                onChangeIndex={(newIndex: number) => nomineeSwipeableIndex.setIndex(newIndex)}
                enableMouseEvents
                slideRenderer={nomineeSliderRenderer}
                />
                <div className="award-choice-indicator">
                  <div className="nominee-stepper">
                    <MobileStepper
                      variant="dots"
                      steps={award.nominees.length + 1}
                      position="static"
                      activeStep={modNomineeIndex}
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
