import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';

export type PasswordModificationRequest = {
  newPassword: string,
  token: string | null,
};
export default class ResetPasswordApi {
  constructor(private readonly httpClient: ApiHttpClient) {
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
}
