import { observable, Observable, WritableObservable } from 'micro-observables';
import { HttpError } from 'simple-http-rest-client';
import { Logger } from 'simple-logging-system';
import LegalApi, { LegalPages } from '../../api/legal/LegalApi';

const logger: Logger = new Logger('LegalService');

export default class LegalService {
  private legalPages: WritableObservable<LegalPages> = observable({});

  constructor(private readonly legalApi: LegalApi) {
  }

  public loadLegalPages() {
    this.legalApi.getLegalPages()
      .then((pages: LegalPages) => {
        this.legalPages.set(pages);
      })
      .catch((e: HttpError) => {
        logger.error('Something went wrong while fetching legal pages', e);
      });
  }

  public getLegalPages(): Observable<LegalPages> {
    return this.legalPages.readOnly();
  }
}
