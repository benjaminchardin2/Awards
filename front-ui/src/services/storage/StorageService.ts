import dayjs from 'dayjs';
import { PronosticChoice } from '../../api/ceremony/CeremonyApi';

export type StorageItem = {
  item: any,
  expiry: dayjs.Dayjs,
};

export default class StorageService {
  private static CEREMONY_KEY: string = 'CEREMONY:';

  private static CEREMONY_PARTICIPATION_KEY: string = 'CEREMONY_PARTICIPATION:';

  private static retrieveItemIfNotExpired = (storageKey: string): any | undefined => {
    const objectStr: string | null = localStorage.getItem(storageKey);
    if (objectStr) {
      const object: StorageItem = JSON.parse(objectStr);
      if (dayjs().isBefore(object.expiry)) {
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

  public static saveCeremonyPicks = (pick: PronosticChoice, ceremonyId: number) => {
    const item: ({ [key: string]: PronosticChoice } | undefined) = StorageService
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);
    return new Promise<void>(
      (resolve: () => void) => {
        StorageService.setItemWithExpiry({
          ...(item || {}),
          [pick.awardId]: pick,
        }, `${StorageService.CEREMONY_KEY}${ceremonyId}`);
        resolve();
      },
    );
  };

  public static getCeremonyPicksToPromise = (
    ceremonyId: number,
  ): Promise<{ [key: string]: PronosticChoice } | undefined> => {
    const item: ({ [key: string]: PronosticChoice } | undefined) = StorageService
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);
    return new Promise(
      (resolve: (value: { [key: string]: PronosticChoice } | undefined) => void) => {
        resolve(item);
      },
    );
  };

  public static getCeremonyPicks = (
    ceremonyId: number,
  ): ({ [key: string]: PronosticChoice } | undefined) => StorageService
    .retrieveItemIfNotExpired(`${StorageService.CEREMONY_KEY}${ceremonyId}`);

  public static saveCeremonyParticipation = (participationId: string, ceremonyId: number) => new Promise<void>(
    (resolve: () => void) => {
      StorageService.setItemWithExpiry(participationId, `${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
      resolve();
    },
  );

  public static getCeremonyParticipation = (ceremonyId: number): (string | undefined) => {
    const item: (string | undefined) = StorageService
      .retrieveItemIfNotExpired(`${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
    if (item) {
      StorageService.setItemWithExpiry(item, `${StorageService.CEREMONY_PARTICIPATION_KEY}${ceremonyId}`);
    }
    return item;
  };
}
