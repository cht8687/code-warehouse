import { NgModule } from '@angular/core';
import { NgReduxModule, NgRedux, DevToolsExtension } from '@angular-redux/store';
import { NgReduxRouterModule, NgReduxRouter } from '@angular-redux/router';

import { createLogger } from 'redux-logger';

import { IAppState } from './model';
import { rootReducer } from './reducers';

@NgModule({
    imports: [NgReduxModule, NgReduxRouterModule],
    providers: [],
  })
  export class StoreModule {
    constructor(
      public store: NgRedux<IAppState>,
      devTools: DevToolsExtension,
      ngReduxRouter: NgReduxRouter
    ) {
      // Tell Redux about our reducers and epics. If the Redux DevTools
      // chrome extension is available in the browser, tell Redux about
      // it too.
      store.configureStore(
        rootReducer,
        {},
        [ createLogger() ],
        devTools.isEnabled() ? [ devTools.enhancer() ] : []);
  
      // Enable syncing of Angular router state with our Redux store.
      if (ngReduxRouter) {
        ngReduxRouter.initialize();
      }
    }
  }
