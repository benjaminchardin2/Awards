import dayjs, { Dayjs } from 'dayjs';
import { observable, Observable } from 'micro-observables';
import { CeremonyPronostics, PronosticChoice } from '../../api/ceremony/CeremonyApi';
import { FrontendConfiguration } from '../../api/configuration/ConfigurationApi';
import ConfigurationService from '../configuration/ConfigurationService';

export type StorageItem = {
  item: any,
  expiry: dayjs.Dayjs,
};

export default class StorageService {
  private resetDate: Observable<Dayjs | undefined> = observable(undefined);

  constructor(private readonly configurationService: ConfigurationService) {
    this.resetDate = configurationService
      .getFrontendConfiguration()
      .select((configuration: FrontendConfiguration | undefined) => {
        if (configuration) {
          const date: Dayjs = dayjs(configuration.localStorageResetDate);
          return date;
        }
        return undefined;
      });
  }

  private static CEREMONY_KEY: string = 'CEREMONY:';

  private static CEREMONY_PARTICIPATION_KEY: string = 'CEREMONY_PARTICIPATION:';

  private retrieveItemIfNotExpired = (storageKey: string): any | undefined => {
    const objectStr: string | null = localStorage.getItem(storageKey);
    if (objectStr) {
      const object: StorageItem = JSON.parse(objectStr);
      if (dayjs().isBefore(object.expiry) && this.resetDate.get()?.isBefore(dayjs(object.expiry).add(-1, 'day'))) {
        const storageItem: StorageItem = {
          item: object.item,
          expiry: dayjs().add(1, 'day'),
        };
        localStorage.setItem(storageKey, JSON.stringify(storageItem));
        return object.item;
      }
      localStorage.removeItem(storageKey);
    }
    return undefined;
  };

  private static setItemWithExpiry = (item: any, storageKey: string) => {
    const storageItem: StorageItem = {
      item,
      expiry: dayjs().add(1, 'day'),
    };
    localStorage.setItem(storageKey, JSON.stringify(storageItem));
  };

  public saveCeremonyPicks = (pick: PronosticChoice, ceremonyId: number) => {
    const item: (CeremonyPronostics | undefined) = this
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);
    return new Promise<void>(
      (resolve: () => void) => {
        const ceremonyPronostic: CeremonyPronostics = {
          winnerPicks: {
            ...(item?.winnerPicks || {}),
            ...(
              pick.type === 'WINNER'
                ? {
                  [pick.awardId]: pick,
                }
                : {}
            ),
          },
          favoritesPicks: {
            ...(item?.favoritesPicks || {}),
            ...(
              pick.type === 'FAVORITE'
                ? {
                  [pick.awardId]: pick,
                }
                : {}
            ),
          },
        };
        StorageService.setItemWithExpiry(ceremonyPronostic, `${StorageService.CEREMONY_KEY}${ceremonyId}`);
        resolve();
      },
    );
  };

  public getCeremonyPicksToPromise = (
    ceremonyId: number,
  ): Promise<CeremonyPronostics | undefined> => {
    const item: (CeremonyPronostics | undefined) = this
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);
    return new Promise(
      (resolve: (value: CeremonyPronostics | undefined) => void) => {
        resolve(item);
      },
    );
  };

  public getCeremonyPicks = (
    ceremonyId: number,
  ): (CeremonyPronostics | undefined) => this
    .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);

  public static saveCeremonyParticipation = (participationId: string, ceremonyId: number) => new Promise<void>(
    (resolve: () => void) => {
      StorageService.setItemWithExpiry(participationId, `${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
      resolve();
    },
  );

  public getCeremonyParticipation = (ceremonyId: number): (string | undefined) => {
    const item: (string | undefined) = this
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
    if (item) {
      StorageService.setItemWithExpiry(item, `${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
    }
    return item;
  };

  public deleteCeremonyAwardsPick = (awardId: string, ceremonyId: number): Promise<void> => {
    const item: (CeremonyPronostics | undefined) = this
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);
    return new Promise<void>(
      (resolve: () => void) => {
        if (item) {
          const ceremonyPicks: CeremonyPronostics = {
            winnerPicks: Object.keys(item.winnerPicks)
              .filter((objKey: string) => objKey !== awardId)
              .reduce((newObj: { [key: string]: PronosticChoice }, key: string) => ({
                ...newObj,
                [key]: item.winnerPicks[key],
              }), {},
              ),
            favoritesPicks: Object.keys(item.favoritesPicks)
              .filter((objKey: string) => objKey !== awardId)
              .reduce((newObj: { [key: string]: PronosticChoice }, key: string) => ({
                ...newObj,
                [key]: item.favoritesPicks[key],
              }), {},
              ),
          };
          StorageService.setItemWithExpiry(ceremonyPicks, `${StorageService.CEREMONY_KEY}${ceremonyId}`);
        }
        resolve();
      });
  };
}
