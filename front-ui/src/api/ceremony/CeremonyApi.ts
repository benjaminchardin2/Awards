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
  type: 'MOVIE' | 'CAST' | 'CREW',
  nominees: NomineeType[],
};

export type PronosticChoice = {
  participationId?: string,
  nomineeId: number,
  awardId: number,
  nominee?: NomineeType,
};

export type SeriliazedId = {
  id: string,
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

  linkPronosticsToUser(ceremonyId: number, pronosticChoices: PronosticChoice[]) {
    return this
      .httpClientAuthenticated
      .restRequest<SeriliazedId>(HttpMethod.POST, `/ceremonies/${ceremonyId}/pronostics/link`)
      .jsonBody(pronosticChoices)
      .execute();
  }

  saveAnonymousPronostics(ceremonyId: number, pronosticChoices: PronosticChoice[]) {
    return this
      .httpClient
      .restRequest<SeriliazedId>(HttpMethod.POST, `/ceremonies/${ceremonyId}/anonymous-pronostics`)
      .jsonBody(pronosticChoices)
      .execute();
  }

  getShareLink(ceremonyId: number, participationId?: string) {
    return this
      .httpClientAuthenticated
      .rawRequest(HttpMethod.POST, `/ceremonies/${ceremonyId}/share-link`)
      .body(participationId)
      .execute();
  }
}
