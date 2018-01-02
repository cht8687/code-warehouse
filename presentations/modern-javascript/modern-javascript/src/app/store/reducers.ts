
import { combineReducers } from 'redux';
import { composeReducers, defaultFormReducer } from '@angular-redux/form';
import { routerReducer } from '@angular-redux/router';


export const rootReducer = composeReducers(
    defaultFormReducer(),
    combineReducers({
      router: routerReducer
    }));
