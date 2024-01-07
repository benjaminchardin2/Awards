import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';

export default class VerifyEmailApi {
  constructor(private readonly httpClient: ApiHttpClient) {
  }

  verifyEmail(token: string) {
    return this
      .httpClient
      .restRequest<void>(HttpMethod.POST, '/verify-email')
      .queryParams([['token', token]])
      .execute();
  }

  resendEmail(email: string) {
    return this
      .httpClient
      .restRequest<void>(HttpMethod.POST, '/verify-email/resend')
      .queryParams([['email', email]])
      .execute();
  }
}
