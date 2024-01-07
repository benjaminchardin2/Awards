import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import ChangePassword from '../features/ChangePassword';
import Home from '../features/Home';
import Legal from '../features/Legal';
import Login from '../features/Login';
import Register from '../features/Register';
import ResetPassword from '../features/ResetPassword';
import VerifyEmail from '../features/VerifyEmail';
import {
  CHANGE_PASSWORD, HOME, LOGIN, MENTIONS_LEGALES, REGISTER, RESET_PASSWORD, RGPD, VERIFY_EMAIL,
} from '../Routes';
import Footer from './Footer';
import Header from './Header';

export default function Router() {
  return (
    <div className="page-layout">
      <Header />
      <div className="main-layout">
        <Routes>
          <Route path={REGISTER} element={<Register />} />
          <Route path={LOGIN} element={<Login />} />
          <Route path={HOME} element={<Home />} />
          <Route path={MENTIONS_LEGALES} element={<Legal page='mentions-legales' />} />
          <Route path={RGPD} element={<Legal page='rgpd' />} />
          <Route path={VERIFY_EMAIL} element={<VerifyEmail />} />
          <Route path={CHANGE_PASSWORD} element={<ChangePassword />} />
          <Route path={RESET_PASSWORD} element={<ResetPassword />} />
          <Route path="*" element={<Navigate to={{ pathname: HOME }} />} />
        </Routes>
      </div>
      <Footer />
    </div>
  );
}
