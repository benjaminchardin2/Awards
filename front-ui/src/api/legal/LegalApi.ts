import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';

export type LegalPages = { [key: string]: string };

export default class LegalApi {
  constructor(private readonly httpClient: ApiHttpClient) {
  }

  getLegalPages() {
    return this
      .httpClient
      .restRequest<LegalPages>(HttpMethod.GET, '/legal')
      .execute();
  }
}
