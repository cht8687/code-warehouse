import rootReducer from '../../../main/client/javascript/reducers';

describe('Root reducer', () => {
  test('returns form in state', () => {
    const state = rootReducer(undefined, {});

    expect(state.form).toBeDefined();
  });

  test('returns routing in state', () => {
    const state = rootReducer(undefined, {});

    expect(state.routing).toBeDefined();
  });
});
