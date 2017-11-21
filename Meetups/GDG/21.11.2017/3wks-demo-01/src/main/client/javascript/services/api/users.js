import { requestJSON } from './http';

const invite = userDetails =>
  requestJSON('/users/invite', 'POST', userDetails);

const redeemInvite = (inviteCode, userDetails) =>
  requestJSON(`/users/invite/${inviteCode}`, 'POST', userDetails);

const list = () =>
  requestJSON('/users');

const login = credentials =>
  requestJSON('/users/login', 'POST', credentials);

const requestMagicLink = email =>
  requestJSON('/users/login/magic', 'POST', { email });

const logout = () =>
  requestJSON('/users/logout', 'POST');

const me = () =>
  requestJSON('/users/me', 'GET');

const get = userId =>
  requestJSON(`/users/${userId}`);

const save = user =>
  requestJSON(`/users/${user.username}`, 'PUT', user);

export default {
  get,
  invite,
  list,
  login,
  logout,
  me,
  redeemInvite,
  requestMagicLink,
  save,
};
