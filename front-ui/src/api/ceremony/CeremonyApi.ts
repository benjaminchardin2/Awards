import {HttpMethod} from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';

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
export default class CeremonyApi {
  constructor(private readonly httpClient: ApiHttpClient) {
  }

  findHighlightedCeremonies() {
    return this
      .httpClient
      .restRequest<Ceremony[]>(HttpMethod.GET, '/ceremonies/highlighted')
      .execute();
  }
}
