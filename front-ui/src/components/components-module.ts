import { Injector } from 'plume-ts-di';
import NotificationRenderer from './layout/NotificationRenderer';

export default function installComponentsModule(injector: Injector) {
  injector.registerSingleton(NotificationRenderer);
}
