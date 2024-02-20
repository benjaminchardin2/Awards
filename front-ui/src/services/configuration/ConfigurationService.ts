import { observable, Observable, WritableObservable } from 'micro-observables';
import ConfigurationApi, { FrontendConfiguration } from '../../api/configuration/ConfigurationApi';

export default class ConfigurationService {
  private frontendConfiguration: WritableObservable<FrontendConfiguration | undefined> = observable(undefined);

  constructor(private readonly configurationApi: ConfigurationApi) {
  }

  public loadFrontendConfiguration() {
    return this.configurationApi.getFrontendConfiguration();
  }

  public setFrontendConfiguration(frontendConfiguration: FrontendConfiguration) {
    this.frontendConfiguration.set(frontendConfiguration);
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
