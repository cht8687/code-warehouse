import { find } from 'lodash';
import { requestJSON } from '../../../../main/client/javascript/services/api/http';

describe('Service: http', () => {
  let request;

  beforeEach(() => {
    global.fetch = jest.fn().mockImplementation(
      (url, config) => {
        request = { url, config };
        return new Promise((resolve) => {
          resolve({
            ok: true,
            id: '123',
            json: () => ({ id: '123' }),
          });
        });
      });
  });

  describe('requestJSON', () => {
    const findHeader = (headers, header) =>
      find(headers, (value, key) => key.toLowerCase() === header.toLowerCase());

    test('will prepend baseUrl to url', () => {
      requestJSON('/something/123', 'GET', { id: 'abc' }, {});

      expect(request.url).toBe('/api/v1/something/123');
    });

    test('will default to json if no Content-Type specified', () => {
      requestJSON('/something/123', 'GET', { id: 'abc' }, {});

      expect(findHeader(request.config.headers, 'Content-Type')).toBe('application/json');
      expect(request.config.body).toBe('{"id":"abc"}');
    });

    test('will not modify content-type if header already exists', () => {
      const data = { id: 'abc' };
      requestJSON('/something/123', 'GET', data, { 'content-type': 'text/plain' });

      expect(request.config.body).toBe(data);
      expect(findHeader(request.config.headers, 'Content-Type')).toBe('text/plain');
    });

    test('will not modify content-type if data is FormData', () => {
      const data = new FormData();
      requestJSON('/something/123', 'GET', data, {});

      expect(request.config.body).toBe(data);
      expect(findHeader(request.config.headers, 'Content-Type')).toBeUndefined();
    });
  });
});
