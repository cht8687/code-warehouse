'use strict'

import { 
  FETCH_NEWS,
  FETCH_INCOMMING_NEWS
} from '../constant/ActionType';

import Api from '../api/api';

/**
 * fetch news
 */
export function fetchNews() {

  return (dispatch, getStore) => {
    const api = new Api();
      return api.getNews()
        .then(data => {
          dispatch({
            type: FETCH_NEWS,
            data
          })
        })
  }
}

export function fetchIncommingNews() {

  return (dispatch, getStore) => {
    const api = new Api();
      return api.getNews()
        .then(data => {
          dispatch({
            type: FETCH_INCOMMING_NEWS,
            data
          })
        })
  }
}