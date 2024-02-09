import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import { getGlobalInstance } from 'plume-ts-di';
import { useObservable } from 'micro-observables';
import ChangePassword from '../features/login/ChangePassword';
import Home from '../features/Home';
import Legal from '../features/Legal';
import Login from '../features/login/Login';
import Register from '../features/login/Register';
import ResetPassword from '../features/login/ResetPassword';
import VerifyEmail from '../features/login/VerifyEmail';
import {
  ACCOUNT_DELETED,
  CEREMONY_PAGE,
  CHANGE_PASSWORD,
  HOME,
  LOGIN,
  MENTIONS_LEGALES,
  PROFILE,
  REGISTER,
  RESET_PASSWORD,
  RGPD,
  SHARE_PAGE,
  VERIFY_EMAIL,
} from '../Routes';
import Footer from './Footer';
import Header from './Header';
import ConditionalRoute from '../navigation/routes/ConditionalRoute';
import SessionService from '../../services/session/SessionService';
import Profile from '../features/profile/Profile';
import DeletedAccount from '../features/deleted-account/DeletedAccount';
import Ceremony from '../features/ceremony/Ceremony';
import Share from '../features/share/Share';
import ConfigurationService from '../../services/configuration/ConfigurationService';

export default function Router() {
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const configurationService: ConfigurationService = getGlobalInstance(ConfigurationService);

  return (
    <div className="page-layout">
      <Header/>
      <div className="main-layout">
        <div className="content">
          <Routes>
            <Route path={REGISTER} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <Register />
              </ConditionalRoute>
            )} />
            <Route path={LOGIN} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <Login />
              </ConditionalRoute>
            )} />
            <Route path={HOME} element={<Home />} />
            <Route path={MENTIONS_LEGALES} element={<Legal page='mentions-legales' />} />
            <Route path={RGPD} element={<Legal page='rgpd' />} />
            <Route path={VERIFY_EMAIL} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <VerifyEmail />
              </ConditionalRoute>
            )} />
            <Route path={CHANGE_PASSWORD} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <ChangePassword />
              </ConditionalRoute>
            )} />
            <Route path={RESET_PASSWORD} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <ResetPassword />
              </ConditionalRoute>
            )} />
            <Route path={ACCOUNT_DELETED} element={(
              <ConditionalRoute shouldDisplayRoute={configurationService.getIsAccountEnabled()} defaultRoute={HOME} >
                <DeletedAccount />
              </ConditionalRoute>
            )} />
            <Route path={PROFILE} element={(
              <ConditionalRoute shouldDisplayRoute={sessionService.isAuthenticated()} defaultRoute={LOGIN} >
                <Profile />
              </ConditionalRoute>
            )} />
            <Route path={CEREMONY_PAGE} element={<Ceremony />} />
            <Route path={SHARE_PAGE} element={<Share />} />
            <Route path="*" element={<Navigate to={{ pathname: HOME }} />} />
          </Routes>
        </div>
      </div>
      <Footer />
    </div>
  );
}
