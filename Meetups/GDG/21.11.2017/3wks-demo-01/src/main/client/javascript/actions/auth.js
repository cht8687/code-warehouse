import users from '../services/api/users';

export const setUser = user => ({
  type: 'SET_USER',
  user,
});

export const fetchUser = () => (dispatch) => {
  dispatch({ type: 'REQUEST_LOGGED_IN_USER' });

  return users.me()
    .then((user) => {
      dispatch({ type: 'REQUEST_LOGGED_IN_USER_SUCCESS' });
      dispatch(setUser(user));
      return user;
    })
    .catch((error) => {
      dispatch({ type: 'REQUEST_LOGGED_IN_USER_FAILURE', error });
      throw error;
    });
};

export const login = credentials => (dispatch) => {
  dispatch({ type: 'REQUEST_LOGIN' });

  return users.login(credentials)
    .then((user) => {
      dispatch({ type: 'REQUEST_LOGIN_SUCCESS', user });
      return user;
    })
    .catch((error) => {
      dispatch({ type: 'REQUEST_LOGIN_FAILURE', error });
      throw error;
    });
};

export const register = (inviteCode, userDetails) => (dispatch) => {
  dispatch({ type: 'REQUEST_REGISTER' });

  return users.redeemInvite(inviteCode, userDetails)
    .then((user) => {
      dispatch({ type: 'REQUEST_REGISTER_SUCCESS', user });
      return user;
    })
    .catch((error) => {
      dispatch({ type: 'REQUEST_REGISTER_FAILURE', error });
      throw error;
    });
};

export const logout = () => (dispatch) => {
  dispatch({ type: 'REQUEST_LOGOUT' });

  return users.logout()
    .then(() => {
      dispatch({ type: 'REQUEST_LOGOUT_SUCCESS' });
      return null;
    })
    .catch((error) => {
      dispatch({ type: 'REQUEST_LOGOUT_FAILURE', error });
      throw error;
    });
};
