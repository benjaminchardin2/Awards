import { useObservable } from 'micro-observables';
import { getGlobalInstance } from 'plume-ts-di';
import React from 'react';
import dompurify from 'dompurify';
import { LegalPages } from '../../api/legal/LegalApi';
import LegalService from '../../services/legal/LegalService';

type LegalPageProps = {
  page: string,
};

export default function Legal({ page }: LegalPageProps) {
  const legalService: LegalService = getGlobalInstance(LegalService);
  const legalPages: LegalPages = useObservable(legalService.getLegalPages());
  const html: string = dompurify.sanitize(legalPages[page], { USE_PROFILES: { html: true } });
  return (
    <div className="legal">
      <div dangerouslySetInnerHTML={{ __html: html }} />
    </div>
  );
}
