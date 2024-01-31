import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';
import ApiHttpClientAuthenticated from '../ApiHttpClientAuthenticated';

export type Ceremony = {
  id: number,
  name: string,
  pictureUrl: string,
  avatarUrl: string,
  hasNominees: string,
  isHighlighted: string,
  shortName: string,
  description: string,
  ceremonyDate: string,
};

export type NomineeType = {
  nomineeId: number,
  movieTitle: string,
  frenchMovieTitle: string,
  movieMediaUrl: string,
  personMediaUrl?: string,
  personName?: string,
  tmdbMovieId: number,
  tmdbPersonId: number | null,
};

export type AwardWithNominees = {
  awardId: number,
  awardName: string,
  type: 'MOVIE' | 'CAST' | 'CREW' | 'PERSON',
  nominees: NomineeType[],
};

export type PronosticChoice = {
  nomineeId: number,
  awardId: number,
  nominee?: NomineeType,
};

export default class CeremonyApi {
  constructor(
    private readonly httpClient: ApiHttpClient,
    private readonly httpClientAuthenticated: ApiHttpClientAuthenticated,
  ) {
  }

  findHighlightedCeremonies() {
    return this
      .httpClient
      .restRequest<Ceremony[]>(HttpMethod.GET, '/ceremonies/highlighted')
      .execute();
  }

  findCeremonyAwards(ceremonyId: number) {
    return this
      .httpClient
      .restRequest<AwardWithNominees[]>(HttpMethod.GET, `/ceremonies/${ceremonyId}/awards`)
      .execute();
  }

  findCeremonyUserPronostics(ceremonyId: number) {
    return this
      .httpClientAuthenticated
      .restRequest<{ [key: string] : PronosticChoice }>(HttpMethod.GET, `/ceremonies/${ceremonyId}/pronostics`)
      .execute();
  }

  saveUserPronostic(ceremonyId: number, pronosticChoice: PronosticChoice) {
    return this
      .httpClientAuthenticated
      .restRequest<void>(HttpMethod.POST, `/ceremonies/${ceremonyId}/pronostics`)
      .jsonBody(pronosticChoice)
      .execute();
  }
}
