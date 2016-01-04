'use strict'

import { APP } from '../constant/application';

/**
 * Switch tab
 */
export function changeTab (tab) {
  return (dispatch) => {
    return Promise.resolve(dispatch({
      type: APP.TAB,
      data: tab
    }))
  }
}
