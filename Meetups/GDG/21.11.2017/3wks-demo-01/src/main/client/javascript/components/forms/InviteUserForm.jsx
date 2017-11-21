import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import { MultiselectField } from './fields';
import { email, required } from './validators';

// eslint-disable-next-line react/prop-types
const InviteUserForm = ({ error }) => (
  <form>
    {error && <p style={{ color: 'red' }}>{error}</p>}
    <Field
      name="email"
      component={TextField}
      hintText="Your email address"
      floatingLabelText="Email"
      type="email"
      validate={[
        required('Email address is required'),
        email('Enter a valid email'),
      ]}
      fullWidth
    />
    <Field
      name="roles"
      component={MultiselectField}
      dataSource={['admin', 'user', 'member']}
      format={value => (value === '' ? null : value)}
      floatingLabelText="Roles"
      hintText="Start typing roles..."
      newChipKeyCodes={[]}
      fullWidth
    />
  </form>
);

export default reduxForm({ form: 'inviteUser' })(InviteUserForm);
