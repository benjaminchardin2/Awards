import React, { useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { Logger } from 'simple-logging-system';
import { getGlobalInstance } from 'plume-ts-di';
import { HttpError } from 'simple-http-rest-client';
import Router from './layout/Router';
import GlobalErrorBoundary from './layout/GlobalErrorBoundary';
import { useOnComponentMounted } from '../lib/react-hooks-alias/ReactHooksAlias';
import LegalService from '../services/legal/LegalService';
import GoogleService from '../services/session/GoogleService';
import ConfigurationService from '../services/configuration/ConfigurationService';
import useLoader, { LoaderState } from '../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { FrontendConfiguration } from '../api/configuration/ConfigurationApi';

const logger: Logger = new Logger('App');
const basePath: string = '/';

// react router redirection is not made anymore :(, see https://github.com/remix-run/react-router/issues/8427
if (window && !window.location.pathname.startsWith(basePath)) {
  window.history.replaceState('', '', basePath + window.location.pathname);
}

export default function App() {
  const legalService: LegalService = getGlobalInstance(LegalService);
  const googleService: GoogleService = getGlobalInstance(GoogleService);
  const configurationService: ConfigurationService = getGlobalInstance(ConfigurationService);
  const [configurationLoaded, setConfigurationLoaded] = useState<boolean>(false);
  const loader: LoaderState = useLoader();

  useOnComponentMounted(() => {
    legalService.loadLegalPages();
    googleService.loadGoogleClient();
    loader.monitor(configurationService
      .loadFrontendConfiguration()
      .then((frontendConfigurationResult: FrontendConfiguration) => {
        setConfigurationLoaded(true);
        configurationService.setFrontendConfiguration(frontendConfigurationResult);
      })
      .catch((e: HttpError) => {
        logger.error('Something went wrong while fetching frontend configuration', e);
      }),
    );
  });

  logger.info('Render App');
  return (
      <GlobalErrorBoundary>
        <ToastContainer />
        {(loader.isLoaded && configurationLoaded) && <BrowserRouter basename={basePath}>
          <Routes>
            <Route
              path="/*"
              element={<Router />}
            />
          </Routes>
        </BrowserRouter>}
      </GlobalErrorBoundary>
  );
}
