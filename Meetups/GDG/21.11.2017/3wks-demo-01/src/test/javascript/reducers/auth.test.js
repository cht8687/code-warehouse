import reducer, { getIsAuthenticated, getUser } from '../../../main/client/javascript/reducers/auth';

describe('Reducer: Auth', () => {
  const user = { username: 'test' };
  const error = { message: 'Test Error' };

  test('correctly sets initial state', () => {
    const state = reducer(undefined, {});

    expect(state).toEqual({
      user: null,
      isAuthenticated: false,
      error: null,
    });
  });

  describe('SET_USER', () => {
    test('SET_USER sets user', () => {
      const state = reducer(undefined, { type: 'SET_USER', user });

      expect(state.user).toEqual(user);
    });
  });

  describe('REQUEST_LOGIN', () => {
    test('REQUEST_LOGIN_SUCCESS sets user', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGIN_SUCCESS', user });

      expect(state.user).toEqual(user);
    });

    test('REQUEST_LOGIN_SUCCESS sets isAuthenticated to true', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGIN_SUCCESS', user });

      expect(state.isAuthenticated).toBe(true);
    });

    test('REQUEST_LOGIN_FAILURE sets isAuthenticated to false', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGIN_FAILURE', error });

      expect(state.isAuthenticated).toBe(false);
    });

    test('REQUEST_LOGIN_SUCCESS sets error to null', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGIN_SUCCESS', user });

      expect(state.error).toBe(null);
    });

    test('REQUEST_LOGIN_FAILURE sets error', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGIN_FAILURE', error });

      expect(state.error).toEqual(error);
    });
  });

  describe('REQUEST_REGISTER', () => {
    test('REQUEST_REGISTER_SUCCESS sets user', () => {
      const state = reducer(undefined, { type: 'REQUEST_REGISTER_SUCCESS', user });

      expect(state.user).toEqual(user);
    });

    test('REQUEST_REGISTER_SUCCESS sets isAuthenticated to true', () => {
      const state = reducer(undefined, { type: 'REQUEST_REGISTER_SUCCESS', user });

      expect(state.isAuthenticated).toBe(true);
    });

    test('REQUEST_REGISTER_FAILURE sets isAuthenticated to false', () => {
      const state = reducer(undefined, { type: 'REQUEST_REGISTER_FAILURE', error });

      expect(state.isAuthenticated).toBe(false);
    });

    test('REQUEST_REGISTER_SUCCESS sets error to null', () => {
      const state = reducer(undefined, { type: 'REQUEST_REGISTER_SUCCESS', user });

      expect(state.error).toBe(null);
    });

    test('REQUEST_REGISTER_FAILURE sets error', () => {
      const state = reducer(undefined, { type: 'REQUEST_REGISTER_FAILURE', error });

      expect(state.error).toEqual(error);
    });
  });

  describe('REQUEST_LOGGED_IN_USER_SUCCESS', () => {
    test('REQUEST_LOGGED_IN_USER_SUCCESS sets isAuthenticated to true', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGGED_IN_USER_SUCCESS' });

      expect(state.isAuthenticated).toBe(true);
    });

    test('REQUEST_LOGGED_IN_USER_FAILURE sets isAuthenticated to false', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGGED_IN_USER_FAILURE', error });

      expect(state.isAuthenticated).toBe(false);
    });

    test('REQUEST_LOGGED_IN_USER_SUCCESS sets error to null', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGGED_IN_USER_SUCCESS' });

      expect(state.error).toBe(null);
    });

    test('REQUEST_LOGGED_IN_USER_FAILURE sets error', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGGED_IN_USER_FAILURE', error });

      expect(state.error).toEqual(error);
    });
  });

  describe('REQUEST_LOGOUT', () => {
    test('REQUEST_LOGOUT_SUCCESS sets user to null', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGOUT_SUCCESS' });

      expect(state.user).toBe(null);
    });

    test('REQUEST_LOGOUT_SUCCESS sets isAuthenticated to false', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGOUT_SUCCESS' });

      expect(state.isAuthenticated).toBe(false);
    });

    test('REQUEST_LOGOUT_SUCCESS sets error to null', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGOUT_SUCCESS' });

      expect(state.error).toBe(null);
    });

    test('REQUEST_LOGOUT_FAILURE sets error', () => {
      const state = reducer(undefined, { type: 'REQUEST_LOGOUT_FAILURE', error });

      expect(state.error).toEqual(error);
    });
  });

  test('getUser returns user from state', () => {
    const state = reducer(undefined, { type: 'REQUEST_LOGIN_SUCCESS', user });

    expect(getUser(state)).toEqual(user);
  });

  test('getIsAuthenticated returns value from state', () => {
    const state = reducer(undefined, { type: 'REQUEST_LOGIN_SUCCESS', user });

    expect(getIsAuthenticated(state)).toBe(true);
  });
});
