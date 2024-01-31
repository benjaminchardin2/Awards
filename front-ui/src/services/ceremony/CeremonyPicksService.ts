import CeremonyApi, { PronosticChoice } from '../../api/ceremony/CeremonyApi';
import StorageService from '../storage/StorageService';

export default class CeremonyPicksService {
  constructor(private readonly ceremonyApi: CeremonyApi) {
  }

  public findCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
  ): Promise<{ [key: string]: PronosticChoice } | undefined> {
    if (isUserConnected) {
      return this.ceremonyApi.findCeremonyUserPronostics(ceremonyId).toPromise();
    }
    return StorageService.getCeremonyPicks(ceremonyId);
  }

  public saveCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
    pronosticChoice: PronosticChoice,
  ): Promise<void> {
    if (isUserConnected) {
      return this.ceremonyApi.saveUserPronostic(ceremonyId, pronosticChoice).toPromise();
    }
    return StorageService.saveCeremonyPicks(pronosticChoice, ceremonyId);
  }
}
