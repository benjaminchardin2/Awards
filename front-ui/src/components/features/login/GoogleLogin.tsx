import { CircularProgress } from '@mui/material';
import { useObservable } from 'micro-observables';
import { getGlobalInstance } from 'plume-ts-di';
import React, { Ref, useRef } from 'react';
import { useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import GoogleService from '../../../services/session/GoogleService';

export default function GoogleLogin() {
  const googleService: GoogleService = getGlobalInstance(GoogleService);
  const btnContainerRef: Ref<HTMLDivElement> = useRef<HTMLDivElement>(null);

  const isLoaded: boolean = useObservable(googleService.getIsLoaded());

  useOnDependenciesChange(() => {
    if (isLoaded) {
      GoogleService.displayButton(btnContainerRef);
    }
  }, [isLoaded]);

  return isLoaded ? (
    <div ref={btnContainerRef} className="google-login">
    </div>
  ) : (<CircularProgress/>);
}
