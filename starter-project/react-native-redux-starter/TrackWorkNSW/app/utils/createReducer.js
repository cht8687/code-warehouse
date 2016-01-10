'use strict'

// export default function createReducer (initialState, actionHandlers) {
//   return (state = initialState, action) => {
//     const reduceFn = actionHandlers[action.type]
//     if (!reduceFn) return state
//     // Looks it works like Object.assign
//     return { ...state, ...reduceFn(state, action) }
//   }
// }

export default function createReducer(initialState, handlers) {
  return function reducer(state = initialState, action) {
    if (handlers.hasOwnProperty(action.type)) {
      return handlers[action.type](state, action)
    } else {
      return state
    }
  }
}