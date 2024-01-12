import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import ChangePassword from '../features/login/ChangePassword';
import Home from '../features/Home';
import Legal from '../features/Legal';
import Login from '../features/login/Login';
import Register from '../features/login/Register';
import ResetPassword from '../features/login/ResetPassword';
import VerifyEmail from '../features/login/VerifyEmail';
import {
  CHANGE_PASSWORD, HOME, LOGIN, MENTIONS_LEGALES, REGISTER, RESET_PASSWORD, RGPD, VERIFY_EMAIL,
} from '../Routes';
import Footer from './Footer';
import Header from './Header';

export default function Router() {
  return (
    <div className="page-layout">
      <Header/>
      <div className="main-layout">
        <div className="content">
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
      </div>
      <Footer />
    </div>
  );
}
