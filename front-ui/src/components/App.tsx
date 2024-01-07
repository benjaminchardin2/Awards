import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { Logger } from 'simple-logging-system';
import { getGlobalInstance } from 'plume-ts-di';
import Router from './layout/Router';
import GlobalErrorBoundary from './layout/GlobalErrorBoundary';
import { useOnComponentMounted } from '../lib/react-hooks-alias/ReactHooksAlias';
import LegalService from '../services/legal/LegalService';

const logger: Logger = new Logger('App');
const basePath: string = '/';

// react router redirection is not made anymore :(, see https://github.com/remix-run/react-router/issues/8427
if (window && !window.location.pathname.startsWith(basePath)) {
  window.history.replaceState('', '', basePath + window.location.pathname);
}

export default function App() {
  const legalService: LegalService = getGlobalInstance(LegalService);

  useOnComponentMounted(() => {
    legalService.loadLegalPages();
  });

  logger.info('Render App');
  return (
      <GlobalErrorBoundary>
        <ToastContainer />
        <BrowserRouter basename={basePath}>
          <Routes>
            <Route
              path="/*"
              element={<Router />}
            />
          </Routes>
        </BrowserRouter>
      </GlobalErrorBoundary>
  );
}
