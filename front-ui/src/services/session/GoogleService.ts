import { observable, WritableObservable } from 'micro-observables';
import { HttpError } from 'simple-http-rest-client';
import { Logger } from 'simple-logging-system';
import { Ref } from 'react';
import ConfigurationApi, { GoogleConfiguration } from '../../api/configuration/ConfigurationApi';
import SessionService from './SessionService';

const logger: Logger = new Logger('LegalService');

type GoogleResponse = {
  credential: string,
};

export default class GoogleService {
  private isLoaded: WritableObservable<boolean> = observable(false);

  private isInError: WritableObservable<boolean> = observable(false);

  private googleConfiguration: WritableObservable<GoogleConfiguration | undefined> = observable(undefined);

  constructor(private readonly configurationApi: ConfigurationApi, private readonly sessionService: SessionService) {
  }

  public static displayButton(ref: Ref<HTMLDivElement>) {
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    window?.google.accounts.id.renderButton(ref.current!, {
      theme: 'filled_black',
      size: 'medium',
      width: '240',
      locale: 'fr_FR',
    });
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    window?.google.accounts.id.prompt();
  }

  public loadGoogleClient() {
    this.configurationApi.getGoogleConfiguration()
      .then((config: GoogleConfiguration) => {
        this.googleConfiguration.set(config);
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        window?.google?.accounts?.id?.initialize({
          client_id: config.clientId,
          callback: (response: GoogleResponse) => this.handleResponse(response),
        });
        this.isLoaded.set(true);
      })
      .catch((e: HttpError) => {
        this.isInError.set(true);
        logger.error('Something went wrong while fetching google configuration', e);
      });
  }

  public getIsInError() {
    return this.isInError.readOnly();
  }

  public getIsLoaded() {
    return this.isLoaded.readOnly();
  }

  public getGoogleConfiguration() {
    return this.googleConfiguration.readOnly();
  }

  private handleResponse(response: GoogleResponse) {
    this.sessionService.authenticateWithGoogle(response.credential);
  }
}
