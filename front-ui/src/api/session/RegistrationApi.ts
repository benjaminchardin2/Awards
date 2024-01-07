import { HttpMethod } from 'simple-http-request-builder';
import { RefreshableJwtToken } from 'browser-user-session';
import ApiHttpClient from '../ApiHttpClient';

export type Registration = {
  email: string,
  userName: string,
  password: string,
};

export default class RegistrationApi {
  constructor(private readonly httpClient: ApiHttpClient) {
  }

  register(registration: Registration) {
    return this
      .httpClient
      .restRequest<RefreshableJwtToken>(HttpMethod.POST, '/register')
      .jsonBody(registration)
      .execute();
  }
}
