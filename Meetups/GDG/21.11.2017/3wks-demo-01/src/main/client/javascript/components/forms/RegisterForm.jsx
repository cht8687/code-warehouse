import React from 'react';
import { Field, reduxForm } from 'redux-form';
import { TextField } from 'redux-form-material-ui';
import RaisedButton from 'material-ui/RaisedButton';
import PropTypes from 'prop-types';
import { required } from './validators';

const RegisterForm = ({ handleSubmit, submitting }) => (
  <form onSubmit={handleSubmit} noValidate>
    <Field
      name="name"
      component={TextField}
      hintText="Your full name"
      floatingLabelText="Full name"
      validate={required('Your full name is required')}
      fullWidth
    />
    <Field
      name="password"
      hintText="Choose your password"
      floatingLabelText="Choose Password"
      component={TextField}
      type="password"
      validate={required('Password is required')}
      fullWidth
    />

    <div className="actions">
      <RaisedButton
        label={submitting ? 'Completing setup...' : 'Complete setup'}
        type="submit"
        disabled={submitting}
        primary
        fullWidth
      />
    </div>
  </form>
);

RegisterForm.propTypes = {
  handleSubmit: PropTypes.func.isRequired,
  submitting: PropTypes.bool.isRequired,
};

export default reduxForm({ form: 'register' })(RegisterForm);
