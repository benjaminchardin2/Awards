import { observable, Observable, WritableObservable } from 'micro-observables';
import { HttpError } from 'simple-http-rest-client';
import { Logger } from 'simple-logging-system';
import ConfigurationApi, { FrontendConfiguration } from '../../api/configuration/ConfigurationApi';
import { LegalPages } from '../../api/legal/LegalApi';

const logger: Logger = new Logger('ConfigurationService');

export default class ConfigurationService {
  private frontendConfiguration: WritableObservable<FrontendConfiguration | undefined> = observable(undefined);

  constructor(private readonly configurationApi: ConfigurationApi) {
  }

  public loadFrontendConfiguration() {
    this.configurationApi.getFrontendConfiguration()
      .then((frontendConfigurationResult: FrontendConfiguration) => {
        this.frontendConfiguration.set(frontendConfigurationResult);
      })
      .catch((e: HttpError) => {
        logger.error('Something went wrong while fetching frontend configuration', e);
      });
  }

  public getFrontendConfiguration(): Observable<FrontendConfiguration | undefined> {
    return this.frontendConfiguration.readOnly();
  }

  public getIsAccountEnabled(): Observable<boolean> {
    return this.frontendConfiguration.select((frontendConfiguration: FrontendConfiguration | undefined) => {
      if (frontendConfiguration) {
        return frontendConfiguration.isAccountEnabled;
      }
      return false;
    });
  }
}
