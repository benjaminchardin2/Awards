import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';

export type GoogleConfiguration = {
  reCaptchaPublicKey: string,
  clientId: string,
};

export default class ConfigurationApi {
  constructor(private readonly httpClient: ApiHttpClient) {
  }

  getGoogleConfiguration() {
    return this
      .httpClient
      .restRequest<GoogleConfiguration>(HttpMethod.GET, '/configuration/google')
      .execute();
  }
}
