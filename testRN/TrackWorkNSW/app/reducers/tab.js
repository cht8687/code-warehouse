'use strict'

import createReducer from '../utils/createReducer';
import {
	CHANGE_TAB
} from '../constant/ActionType';
import {
	LIVE,
	COMMING
} from '../constant/tab';
import { Record } from 'immutable';

class State extends Record({
	tabName: 'LIVE'

}){

}

const actionHandler = {
  [CHANGE_TAB](state, action) {
    const { data } = action;
    return state.set('tabName', data);
  }
}

export default createReducer(new State(), actionHandler);
