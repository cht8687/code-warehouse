import { combineReducers } from 'redux';

const user = (state = null, action) => {
  switch (action.type) {
    case 'SET_USER':
    case 'REQUEST_LOGIN_SUCCESS':
    case 'REQUEST_REGISTER_SUCCESS':
      return { ...action.user };
    case 'REQUEST_LOGOUT_SUCCESS':
      return null;
    default:
      return state;
  }
};

const isAuthenticated = (state = false, action) => {
  switch (action.type) {
    case 'REQUEST_LOGIN_SUCCESS':
    case 'REQUEST_REGISTER_SUCCESS':
    case 'REQUEST_LOGGED_IN_USER_SUCCESS':
      return true;
    case 'REQUEST_LOGIN_FAILURE':
    case 'REQUEST_REGISTER_FAILURE':
    case 'REQUEST_LOGGED_IN_USER_FAILURE':
    case 'REQUEST_LOGOUT_SUCCESS':
      return false;
    default:
      return state;
  }
};

const error = (state = null, action) => {
  switch (action.type) {
    case 'REQUEST_LOGIN_SUCCESS':
    case 'REQUEST_REGISTER_SUCCESS':
    case 'REQUEST_LOGGED_IN_USER_SUCCESS':
    case 'REQUEST_LOGOUT_SUCCESS':
      return null;
    case 'REQUEST_LOGIN_FAILURE':
    case 'REQUEST_REGISTER_FAILURE':
    case 'REQUEST_LOGGED_IN_USER_FAILURE':
    case 'REQUEST_LOGOUT_FAILURE':
      return { ...action.error, message: action.error.message };
    default:
      return state;
  }
};

export const getUser = state =>
  state.user;

export const getIsAuthenticated = state =>
  state.isAuthenticated;

export default combineReducers({
  user,
  isAuthenticated,
  error,
});

