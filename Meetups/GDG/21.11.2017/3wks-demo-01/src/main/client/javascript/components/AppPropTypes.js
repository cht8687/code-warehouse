import { arrayOf, shape, string } from 'prop-types';

const user = shape({
  username: string,
  email: string,
  name: string,
  roles: arrayOf(string),
  status: string,
});

export default {
  user,
};
