import Typography from '@mui/material/Typography';
import React, { useState } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Button from '@mui/material/Button/Button';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../../api/ceremony/CeremonyApi';
import Nominee from '../../Nominee';
import GradientIcon from '../../GradientIcon';
import useMessages from '../../../../../i18n/hooks/messagesHook';
import NomineeSearch from '../../NomineeSearch';

type AwardBigScreenType = {
  awards: AwardWithNominees[],
  winnerPicks: { [key: string]: PronosticChoice },
  favoritePicks: { [key: string]: PronosticChoice },
  onPronosticChoice: (
    award: AwardWithNominees,
    type: 'WINNER' | 'FAVORITE',
    nomineeId?: number,
    nominee?: NomineeType
  ) => void,
  pickTitle: (awardId: number) => string | undefined,
  pickSubTitle: (awardId: number) => string | undefined,
  skipChoice: (awardId: string) => void,
};
export default function AwardsBigScreen({
  awards, winnerPicks, favoritePicks, onPronosticChoice, pickTitle, pickSubTitle, skipChoice,
}: AwardBigScreenType) {
  const [expanded, setExpanded] = useState<number | undefined>(undefined);
  const { messages } = useMessages();
  const onAccordionClick = (awardId: number) => {
    if (awardId === expanded) {
      setExpanded(undefined);
    } else {
      setExpanded(awardId);
    }
  };

  const hasPick: boolean = !!(expanded && (winnerPicks[expanded] || favoritePicks[expanded]));

  const skipChoiceAndMoveOn = () => {
    if (hasPick) {
      skipChoice(expanded!.toString());
    }
    if (!hasPick) {
      setExpanded(undefined);
    }
  };

  return (
    <>
      {
        awards
          .map((award: AwardWithNominees) => (
            <div key={award.awardId} className={`award ${(expanded === award.awardId) ? 'expanded' : ''}`}>
              <Accordion expanded={expanded === award.awardId} onChange={() => onAccordionClick(award.awardId)}>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon/>}
                  aria-controls="panel1-content"
                  id={`${award.awardId}`}
                >
                  <div className="award-title">
                    <GradientIcon/>
                    <Typography variant="h2" component="h2">{award.awardName} </Typography>
                  </div>
                  {winnerPicks[award.awardId] && <div className="award-pick-preview">
                    <Typography variant="h4" component="h4" className="title">{pickTitle(award.awardId)}</Typography>
                    {award.type !== 'MOVIE'
                      && <Typography
                        variant="h4"
                        component="h4"
                        className="subtitle"
                      >
                        {`- ${pickSubTitle(award.awardId)}`}
                      </Typography>
                    }
                  </div>}
                </AccordionSummary>
                <AccordionDetails>
                  <div className="nominee-row">
                    {
                      award.nominees
                        ?.map((nominee: NomineeType) => (
                          <Nominee
                            key={`
                            ${award.awardId}|
                            ${nominee.tmdbMovieId}|
                            ${nominee.tmdbPersonId}|
                            ${nominee.personName}
                            `}
                            nominee={nominee}
                            isSelected={winnerPicks[award.awardId]?.nomineeId === nominee.nomineeId}
                            isFavorite={favoritePicks[award.awardId]?.nomineeId === nominee.nomineeId}
                            onClick={(type: 'WINNER' | 'FAVORITE') => onPronosticChoice(award, type, nominee.nomineeId)}
                            awardType={award.type}
                          />
                        ))
                    }
                    <NomineeSearch
                      onClick={(nominee: NomineeType) => onPronosticChoice(award, 'FAVORITE', undefined, nominee)}
                      awardType={award.type}
                      awardId={award.awardId}
                      nominee={favoritePicks[award.awardId]?.nominee}
                      skipChoice={skipChoice}
                    />
                  </div>
                  <Button variant="text" onClick={skipChoiceAndMoveOn}>
                    {hasPick ? messages.action.delete_choice : messages.action.skip_choice}
                  </Button>
                </AccordionDetails>
              </Accordion>
            </div>
          ))
      }
    </>
  );
}
