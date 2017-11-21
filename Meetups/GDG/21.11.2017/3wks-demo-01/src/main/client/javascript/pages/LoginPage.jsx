import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { SubmissionError } from 'redux-form';
import { func, object } from 'prop-types';
import CenteredPanelLayout from './CenteredPanelLayout';
import LoginForm from '../components/forms/LoginForm';
import RetinaImage from '../components/RetinaImage';
import * as authActions from '../actions/auth';
import api from '../services/api';
import './LoginPage.less';

const MagicLinkInstructions = () => (
  <div className="magic-link-instructions">
    <RetinaImage src="envelope.png" alt="Envelope" />
    <p className="subheading-2">Magic link sent! Check your email and click the button inside to login.</p>
  </div>
);

class LoginPage extends Component {
  static propTypes = {
    location: object.isRequired,
    login: func.isRequired,
    navigateTo: func.isRequired,
  };

  state = { magicLinkSent: false };

  handleSubmit = (values) => {
    const { location, login, navigateTo } = this.props;
    const next = location.query.next || '/';

    if (values.password) {
      return login(values)
        .then(() => navigateTo(next))
        .catch((error) => {
          throw new SubmissionError({ _error: error.message });
        });
    }
    return api.users.requestMagicLink(values.username)
      .then(() => this.setState({ magicLinkSent: true }))
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });
  };

  render() {
    const { magicLinkSent } = this.state;

    return (
      <CenteredPanelLayout title="Sign in">
        {magicLinkSent
          ? <MagicLinkInstructions />
          : <LoginForm onSubmit={this.handleSubmit} />}
      </CenteredPanelLayout>
    );
  }
}


const actions = { ...authActions, navigateTo: push };
export default connect(null, actions)(LoginPage);
