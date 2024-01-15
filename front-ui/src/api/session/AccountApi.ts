import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClientAuthenticated from '../ApiHttpClientAuthenticated';

export type ProfileType = {
  userName: string,
  email: string,
  userHashtag: string,
  isGoogle: boolean,
};

export default class AccountApi {
  constructor(private readonly httpClient: ApiHttpClientAuthenticated) {
  }

  getProfile() {
    return this
      .httpClient
      .restRequest<ProfileType>(HttpMethod.GET, '/account')
      .execute();
  }

  modifyUsername(username: string) {
    return this.httpClient
      .rawRequest(HttpMethod.PUT, '/account/username')
      .body(username)
      .execute();
  }

  deleteAccount(password?: string) {
    return this.httpClient
      .restRequest(HttpMethod.POST, '/account/delete')
      .body(password)
      .execute();
  }
}
