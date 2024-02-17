import { HttpMethod } from 'simple-http-request-builder';
import ApiHttpClient from '../ApiHttpClient';
import { NomineeType } from '../ceremony/CeremonyApi';

export type SearchResult = {
  id: string,
  name: string,
};

export type NomineeRequest = {
  awardsType: 'MOVIE' | 'CAST' | 'CREW',
  personId?: string,
  movieId: string,
};

export type AdditionalResults = {
  searchResults: SearchResult[],
  hasMoreResults: boolean,
  nextPage: number,
};

export default class SearchApi {
  constructor(
    private readonly httpClient: ApiHttpClient,
  ) {
  }

  searchMovies(query: string, page: number) {
    return this
      .httpClient
      .restRequest<SearchResult[]>(HttpMethod.GET, '/search/movies')
      .queryParams([['query', query], ['page', page]])
      .execute();
  }

  searchPersons(query: string, page: number) {
    return this
      .httpClient
      .restRequest<SearchResult[]>(HttpMethod.GET, '/search/persons')
      .queryParams([['query', query], ['page', page]])
      .execute();
  }

  searchMoviePersons(awardId: number, page: number, movieId: string) {
    return this
      .httpClient
      .restRequest<AdditionalResults>(HttpMethod.GET, `/search/movies/${movieId}/persons`)
      .queryParams([['awardId', awardId], ['page', page]])
      .execute();
  }

  searchPersonCredits(awardId: number, page: number, personId: string) {
    return this
      .httpClient
      .restRequest<AdditionalResults>(HttpMethod.GET, `/search/persons/${personId}/credits`)
      .queryParams([['awardId', awardId], ['page', page]])
      .execute();
  }

  convertSearchToNominee(nomineeRequest: NomineeRequest) {
    return this
      .httpClient
      .restRequest<NomineeType>(HttpMethod.POST, '/search/nominee')
      .jsonBody(nomineeRequest)
      .execute();
  }
}
