import { Injector } from 'plume-ts-di';
import ApiHttpClient from './ApiHttpClient';
import ApiHttpClientAuthenticated from './ApiHttpClientAuthenticated';
import SessionApi from './session/SessionApi';
import CeremonyApi from './ceremony/CeremonyApi';
import LegalApi from './legal/LegalApi';
import RegistrationApi from './session/RegistrationApi';
import VerifyEmailApi from './verify-email/VerifyEmailApi';
import PasswordApi from './reset-password/PasswordApi';
import ConfigurationApi from './configuration/ConfigurationApi';
import AccountApi from './session/AccountApi';
import ShareApi from './share/ShareApi';
import SearchApi from './search/SearchApi';

export default function installApiModule(injector: Injector) {
  injector.registerSingleton(ApiHttpClient);
  injector.registerSingleton(ApiHttpClientAuthenticated);
  injector.registerSingleton(SessionApi);
  injector.registerSingleton(CeremonyApi);
  injector.registerSingleton(LegalApi);
  injector.registerSingleton(RegistrationApi);
  injector.registerSingleton(VerifyEmailApi);
  injector.registerSingleton(PasswordApi);
  injector.registerSingleton(ConfigurationApi);
  injector.registerSingleton(AccountApi);
  injector.registerSingleton(ShareApi);
  injector.registerSingleton(SearchApi);
}
