import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';
import { NomineeType } from '../ceremony/CeremonyApi';

export type AwardShare = {
  awardTitle: string,
  awardType: 'CREW' | 'CAST' | 'MOVIE',
  nominee: NomineeType,
};

export type CeremonyShare = {
  ceremonyTitle: string,
  ceremonyId: string,
  winnerAwardShares: AwardShare[],
  favoriteAwardShares: AwardShare[],
};

export default class ShareApi {
  constructor(
    private readonly httpClient: ApiHttpClient,
  ) {
  }

  getCeremonyShare(shareCode: string) {
    return this
      .httpClient
      .restRequest<CeremonyShare>(HttpMethod.GET, `/share/${shareCode}/`)
      .execute();
  }
}
