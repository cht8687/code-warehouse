import React from 'react';
import { Router, Route, IndexRoute } from 'react-router';
import { history } from '../store';
import {
  composeOnEnterHooks,
  loginRequired,
  hasAnyRole,
} from './hooks';
import Layout from '../pages/Layout';
import AdminLayout from '../pages/admin/AdminLayout';
import DashboardPage from '../pages/admin/DashboardPage';
import HomePage from '../pages/HomePage';
import NotFoundPage from '../pages/NotFoundPage';
import LoginPage from '../pages/LoginPage';
import ManageUsersPage from '../pages/admin/ManageUsersPage';
import RegisterPage from '../pages/RegisterPage';
import UserProfilePage from '../pages/admin/UserProfilePage';

/**
 * Define frontend routes.
 */
const getRoutes = () => (
  <Router history={history}>
    <Route path="/login" component={LoginPage} />
    <Route path="/register/:inviteCode" component={RegisterPage} />

    <Route
      path="/admin"
      component={AdminLayout}
      onEnter={composeOnEnterHooks(loginRequired, hasAnyRole('admin'))}
    >
      <IndexRoute component={DashboardPage} />
      <Route path="users" component={ManageUsersPage} />
      <Route path="users/:userId" component={UserProfilePage} />
      <Route path="*" component={NotFoundPage} />
    </Route>

    <Route path="/" component={Layout}>
      <IndexRoute component={HomePage} />
      <Route path="*" component={NotFoundPage} />
    </Route>
  </Router>
);

export default getRoutes;
