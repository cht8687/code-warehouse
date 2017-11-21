import { isEmpty, isString, trim } from 'lodash';

const isBlank = value => isEmpty(trim(value));
const isValidEmail = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

export const required = (msg = 'is required') => (value) => {
  if (isString(value)) {
    return isBlank(value) && msg;
  }
  return (value === null || value === undefined) && msg;
};

export const email = (msg = 'is invalid') => value => (
  value && !isValidEmail.test(value) && msg
);
