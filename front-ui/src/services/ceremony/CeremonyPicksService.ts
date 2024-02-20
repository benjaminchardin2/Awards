import { HttpPromise } from 'simple-http-rest-client';
import CeremonyApi, { CeremonyPronostics, PronosticChoice, SeriliazedId } from '../../api/ceremony/CeremonyApi';
import StorageService from '../storage/StorageService';

export default class CeremonyPicksService {
  constructor(private readonly ceremonyApi: CeremonyApi, private readonly storageService: StorageService) {
  }

  public findCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
  ): Promise<CeremonyPronostics | undefined> {
    if (isUserConnected) {
      return new Promise((resolve: ((ceremonyPicks: CeremonyPronostics) => void)) => {
        this.ceremonyApi.findCeremonyUserPronostics(ceremonyId).then(
          (ceremonyPronostics: CeremonyPronostics) => {
            if (!ceremonyPronostics || (
              (Object.values(ceremonyPronostics.favoritesPicks).length === 0)
              && (Object.values(ceremonyPronostics.winnerPicks).length === 0)
            )) {
              resolve(this.linkPronosticsToUser(ceremonyId));
            } else {
              resolve(ceremonyPronostics);
            }
          });
      });
    }
    return this.storageService.getCeremonyPicksToPromise(ceremonyId);
  }

  public saveCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
    pronosticChoice: PronosticChoice,
  ): Promise<void> {
    if (isUserConnected) {
      return this.ceremonyApi.saveUserPronostic(ceremonyId, pronosticChoice).toPromise();
    }
    const participationId: string | undefined = this.storageService.getCeremonyParticipation(ceremonyId);
    if (participationId) {
      return this.ceremonyApi.saveUserPronostic(
        ceremonyId,
        {
          ...pronosticChoice,
          participationId,
        },
      )
        .toPromise()
        .finally(() => {
          this.storageService.saveCeremonyPicks(pronosticChoice, ceremonyId);
        });
    }
    return this.storageService.saveCeremonyPicks(pronosticChoice, ceremonyId);
  }

  public deleteCeremonyAwardsPick(
    ceremonyId: number,
    awardId: string,
    isUserConnected: boolean,
  ): Promise<void> {
    if (isUserConnected) {
      return this.ceremonyApi.deleteCeremonyAwardsPick(ceremonyId, {
        awardId,
      }).toPromise();
    }
    const participationId: string | undefined = this.storageService.getCeremonyParticipation(ceremonyId);
    if (participationId) {
      return this.ceremonyApi.deleteCeremonyAwardsPick(
        ceremonyId,
        {
          awardId,
          participationId,
        },
      )
        .toPromise()
        .finally(() => {
          this.storageService.deleteCeremonyAwardsPick(awardId, ceremonyId);
        });
    }
    return this.storageService.deleteCeremonyAwardsPick(awardId, ceremonyId);
  }

  public getShareLink(
    ceremonyId: number,
    isUserConnected: boolean,
  ): Promise<string> {
    return new Promise((resolve: ((value: string) => void)) => {
      if (isUserConnected) {
        CeremonyPicksService.resolveShareLinkApiResult(
          this.ceremonyApi.getShareLink(ceremonyId),
          resolve,
        );
      } else {
        const participationId: string | undefined = this.storageService.getCeremonyParticipation(ceremonyId);
        if (participationId) {
          CeremonyPicksService.resolveShareLinkApiResult(
            this.ceremonyApi.getShareLink(ceremonyId, participationId),
            resolve,
          );
        } else {
          const ceremonyPicks: (CeremonyPronostics | undefined) = this.storageService
            .getCeremonyPicks(ceremonyId);
          if (ceremonyPicks) {
            this.ceremonyApi.saveAnonymousPronostics(ceremonyId, ceremonyPicks)
              .then((newParticipationId: SeriliazedId) => {
                StorageService.saveCeremonyParticipation(newParticipationId.id, ceremonyId)
                  .then(() => {
                    CeremonyPicksService.resolveShareLinkApiResult(
                      this.ceremonyApi.getShareLink(ceremonyId, newParticipationId.id),
                      resolve,
                    );
                  });
              });
          }
        }
      }
    });
  }

  public linkPronosticsToUser(ceremonyId: number): CeremonyPronostics {
    const ceremonyPicks: (CeremonyPronostics | undefined) = this.storageService
      .getCeremonyPicks(ceremonyId);
    if (ceremonyPicks) {
      this.ceremonyApi.linkPronosticsToUser(
        ceremonyId,
        ceremonyPicks,
      );
    }
    return ceremonyPicks || {
      winnerPicks: {},
      favoritesPicks: {},
    };
  }

  private static resolveShareLinkApiResult(
    promise: HttpPromise<Response>,
    resolve: ((value: string) => void),
  ) {
    promise
      .then((response: Response) => {
        response.text()
          .then((shareLink: string) => {
            resolve(shareLink);
          });
      });
  }
}
