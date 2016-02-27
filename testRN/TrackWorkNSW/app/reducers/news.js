'use strict'

import createReducer from '../utils/createReducer';
import { 
  FETCH_NEWS,
  FETCH_INCOMMING_NEWS
} from '../constant/ActionType';

import { Record } from 'immutable';
import { newsParser } from '../utils/newsParser';
import { inCommingNewsParser } from '../utils/inCommingNewsParser';

class State extends Record({
  newsData: [],
  inCommingNewsData: []

}){

}

const actionHandler = {
  [FETCH_NEWS](state, action) {
    const { data } = action;

    let result = newsParser(data);

    return state.set('newsData', result);
  },

  [FETCH_INCOMMING_NEWS](state, action) {
    const { data } = action;

    let result = inCommingNewsParser(data);

    return state.set('inCommingNewsData', result);
  }
}

export default createReducer(new State(), actionHandler);
