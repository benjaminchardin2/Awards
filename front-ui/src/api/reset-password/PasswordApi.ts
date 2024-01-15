import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';
import ApiHttpClientAuthenticated from '../ApiHttpClientAuthenticated';

export type PasswordModificationRequest = {
  newPassword: string,
  token: string | null,
};

export type PasswordModificationFromUser = {
  newPassword: string,
  oldPassword: string,
};

export default class PasswordApi {
  constructor(private readonly httpClient: ApiHttpClient, private readonly httpClientAuthenticated: ApiHttpClientAuthenticated) {
  }

  changePassword(passwordModificationRequest: PasswordModificationRequest) {
    return this
      .httpClient
      .restRequest<void>(HttpMethod.POST, '/passwords')
      .jsonBody(passwordModificationRequest)
      .execute();
  }

  resetPassword(email: string, reCaptchaResponse: string) {
    return this
      .httpClient
      .restRequest<void>(HttpMethod.POST, '/passwords/reset')
      .queryParams([['email', email]])
      .jsonBody(({
        reCaptchaResponse,
      }))
      .execute();
  }

  modifyPassword(password: PasswordModificationFromUser) {
    return this
      .httpClientAuthenticated
      .restRequest<void>(HttpMethod.PUT, '/passwords/modify')
      .jsonBody(password)
      .execute();
  }
}
