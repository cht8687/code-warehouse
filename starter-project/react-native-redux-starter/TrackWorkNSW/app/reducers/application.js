'use strict'

import createReducer from '../utils/createReducer';
import { APP } from '../constant/application';

const initialState = {
  tab: 'game'
};

const actionHandler = {
  [APP.TAB]: (state, action) => {
    return Object.assign({}, state, {
      tab: action.data
    })
  }
}

export default createReducer(initialState, actionHandler);
