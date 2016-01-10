'use strict'

import { 
  CHANGE_TAB 
} from '../constant/ActionType';

/**
 * Switch tab
 */
export function changeTab (data) {
  return {
    type: CHANGE_TAB,
    data
  }
}
