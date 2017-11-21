/* eslint-disable no-console */

const baseUrl = '/api/v1';

// required so IE11 does not automatically cache all GET requests
const noCacheHeaders = {
  pragma: 'no-cache',
  'cache-control': 'no-cache',
};

export const request = (path, method = 'GET', body = null, headers = {}) => {
  const url = `${baseUrl}${path}`;

  console.log(`${method} ${url}`);

  const config = {
    method,
    headers: { ...noCacheHeaders, ...headers },
    credentials: 'include',
  };

  // Edge browsers will fail silently if you give a body, even a null one, to a GET request
  if (body) {
    config.body = body;
  }

  return fetch(url, config).then((response) => {
    if (response.ok) {
      return response;
    }

    return response.text().then((text) => {
      let error;

      try {
        // Attempt to parse body as JSON, fallback to plain text if parsing fails
        const data = JSON.parse(text);
        error = new Error(data.message);
        error.type = data.type;
      } catch (e) {
        // Fallback to plain text
        error = new Error(response.statusText);
      }

      error.status = response.status;
      error.payload = text;

      throw error;
    });
  });
};

const hasHeader = (headers = {}, headerName) =>
  Object.keys(headers).some(key => key.toLowerCase() === headerName.toLowerCase());

const requestWithData = (path, method, data, headers = {}) => {
  const headerContentType = 'Content-Type';
  // Don't modify for FormData or request with existing content-type header set
  if (data instanceof FormData || hasHeader(headers, headerContentType)) {
    return request(path, method, data, headers);
  }
  // Otherwise default to JSON
  return request(path, method, JSON.stringify(data), {
    [headerContentType]: 'application/json',
    ...headers,
  });
};

export const requestJSON = (path, method, data, headers = {}) =>
  (data ? requestWithData(path, method, data, headers) : request(path, method, null, headers))
    .then(response => (response.status !== 204 ? response.json() : null));
