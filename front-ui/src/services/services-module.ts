import { Scheduler } from 'simple-job-scheduler';
import { Injector } from 'plume-ts-di';
import { BrowserUserActivityListener, IdlenessDetector, UserActivityListener } from 'browser-user-session';
import SessionService from './session/SessionService';
import ObservableNotificationEngine from '../lib/plume-notification/ObservableNotificationEngine';
import NotificationEngine from '../lib/plume-notification/NotificationEngine';
import LegalService from './legal/LegalService';
import GoogleService from './session/GoogleService';
import CeremonyPicksService from './ceremony/CeremonyPicksService';
import ConfigurationService from './configuration/ConfigurationService';
import StorageService from './storage/StorageService';

export default function installServicesModule(injector: Injector) {
  // browser dependent services
  injector.registerSingleton(BrowserUserActivityListener, UserActivityListener);
  // other services
  injector.registerSingleton(IdlenessDetector);
  injector.registerSingleton(SessionService);
  injector.registerSingleton(ObservableNotificationEngine);
  injector.registerSingleton(ObservableNotificationEngine, NotificationEngine);
  injector.registerSingleton(Scheduler);
  injector.registerSingleton(LegalService);
  injector.registerSingleton(GoogleService);
  injector.registerSingleton(CeremonyPicksService);
  injector.registerSingleton(ConfigurationService);
  injector.registerSingleton(StorageService);
}
