import { HttpPromise } from 'simple-http-rest-client';
import CeremonyApi, { PronosticChoice, SeriliazedId } from '../../api/ceremony/CeremonyApi';
import StorageService from '../storage/StorageService';

export default class CeremonyPicksService {
  constructor(private readonly ceremonyApi: CeremonyApi) {
  }

  public findCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
  ): Promise<{ [key: string]: PronosticChoice } | undefined> {
    if (isUserConnected) {
      return new Promise((resolve: ((userChoices: { [p: string]: PronosticChoice }) => void)) => {
        this.ceremonyApi.findCeremonyUserPronostics(ceremonyId).then(
          (userChoices: { [p: string]: PronosticChoice }) => {
            if (!userChoices || (Object.values(userChoices).length === 0)) {
              resolve(this.linkPronosticsToUser(ceremonyId));
            } else {
              resolve(userChoices);
            }
          });
      });
    }
    return StorageService.getCeremonyPicksToPromise(ceremonyId);
  }

  public saveCeremonyPicks(
    ceremonyId: number,
    isUserConnected: boolean,
    pronosticChoice: PronosticChoice,
  ): Promise<void> {
    if (isUserConnected) {
      return this.ceremonyApi.saveUserPronostic(ceremonyId, pronosticChoice).toPromise();
    }
    const participationId: string | undefined = StorageService.getCeremonyParticipation(ceremonyId);
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
          StorageService.saveCeremonyPicks(pronosticChoice, ceremonyId);
        });
    }
    return StorageService.saveCeremonyPicks(pronosticChoice, ceremonyId);
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
        const participationId: string | undefined = StorageService.getCeremonyParticipation(ceremonyId);
        if (participationId) {
          CeremonyPicksService.resolveShareLinkApiResult(
            this.ceremonyApi.getShareLink(ceremonyId, participationId),
            resolve,
          );
        } else {
          const ceremonyPicks: ({ [key: string]: PronosticChoice } | undefined) = StorageService
            .getCeremonyPicks(ceremonyId);
          const ceremonyPicksArray: PronosticChoice[] = ceremonyPicks ? Object.values(ceremonyPicks) : [];
          this.ceremonyApi.saveAnonymousPronostics(ceremonyId, ceremonyPicksArray)
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
    });
  }

  public linkPronosticsToUser(ceremonyId: number): { [key: string]: PronosticChoice } {
    const ceremonyPicks: ({ [key: string]: PronosticChoice } | undefined) = StorageService
      .getCeremonyPicks(ceremonyId);
    if (ceremonyPicks) {
      this.ceremonyApi.linkPronosticsToUser(
        ceremonyId,
        Object.values(ceremonyPicks),
      );
    }
    return ceremonyPicks || {};
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
