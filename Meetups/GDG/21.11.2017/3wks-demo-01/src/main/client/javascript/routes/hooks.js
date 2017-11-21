import { intersection } from 'lodash';
import applyEachSeries from 'async/applyEachSeries';

import store from '../store';
import { getLoggedInUser, getIsAuthenticated } from '../reducers';
import { fetchUser } from '../actions/auth';

const { dispatch, getState } = store;

const withLogin = nextPage => ({ pathname: '/login', query: { next: nextPage } });

export const composeOnEnterHooks = (...hooks) => (nextState, replace, callback) => {
  applyEachSeries(hooks, nextState, replace, (error) => {
    if (error) {
      console.error('hook error:', error);
    } // eslint-disable-line no-console
    callback();
  });
};

export const loginRequired = (nextState, replace, callback) => {
  const isAuthenticated = getIsAuthenticated(getState());
  if (isAuthenticated) {
    callback();
    return;
  }

  dispatch(fetchUser()).then(
    () => {
      callback();
    },
    (error) => {
      replace(withLogin(nextState.location.pathname));
      callback(error);
    },
  );
};

export const hasAnyRole = (...roles) => (nextState, replace, callback) => {
  const user = getLoggedInUser(getState());

  const hasRequiredRole = user && intersection(roles, user.roles).length > 0;
  if (hasRequiredRole) {
    callback();
    return;
  }

  replace(withLogin(nextState.location.pathname));
  callback(new Error('User lacks required role'));
};
