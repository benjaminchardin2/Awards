import Typography from '@mui/material/Typography';
import React, { useState } from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { AwardWithNominees, NomineeType, PronosticChoice } from '../../../../../api/ceremony/CeremonyApi';
import Nominee from '../../Nominee';
import GradientIcon from '../../GradientIcon';

type AwardBigScreenType = {
  awards: AwardWithNominees[],
  userPicks: { [key: string]: PronosticChoice },
  onPronosticChoice: (award: AwardWithNominees, nomineeId?: number) => void,
  pickTitle: (awardId: number) => string | undefined,
};
export default function AwardsBigScreen({
  awards, userPicks, onPronosticChoice, pickTitle,
}: AwardBigScreenType) {
  const [expanded, setExpanded] = useState<number | undefined>(undefined);

  const onAccordionClick = (awardId: number) => {
    if (awardId === expanded) {
      setExpanded(undefined);
    } else {
      setExpanded(awardId);
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
                  {userPicks[award.awardId] && <div className="award-pick-preview">
                    <Typography variant="h4" component="h4">{pickTitle(award.awardId)}</Typography>
                  </div>}
                </AccordionSummary>
                <AccordionDetails>
                  <div className="nominee-row">
                    {
                      award.nominees
                        ?.map((nominee: NomineeType) => (
                          <Nominee
                            key={`${award.awardId}|${nominee.tmdbMovieId}|${nominee.tmdbPersonId}`}
                            nominee={nominee}
                            isSelected={userPicks[award.awardId]?.nomineeId === nominee.nomineeId}
                            onClick={() => onPronosticChoice(award, nominee.nomineeId)}
                            awardType={award.type}
                          />
                        ))
                    }
                  </div>
                </AccordionDetails>
              </Accordion>
            </div>
          ))
      }
    </>
  );
}
