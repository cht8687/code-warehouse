import React from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { func, object } from 'prop-types';
import { Divider, Drawer, MenuItem, Subheader } from 'material-ui';
import ExitIcon from 'material-ui/svg-icons/action/exit-to-app';
import DashboardIcon from 'material-ui/svg-icons/action/dashboard';
import PersonIcon from 'material-ui/svg-icons/social/person';
import PeopleIcon from 'material-ui/svg-icons/social/people';
import ProfileCard from './ProfileCard';
import * as authActions from '../../../actions/auth';
import { getLoggedInUser } from '../../../reducers';

const MenuDrawer = ({
  loggedInUser, logout, navigateTo, ...rest
}) => (
  <Drawer {...rest} docked={false} width={280}>
    <ProfileCard user={loggedInUser} />

    <MenuItem
      primaryText="Dashboard"
      leftIcon={<DashboardIcon />}
      onClick={() => navigateTo('/admin')}
    />
    <MenuItem
      primaryText="Manage users"
      leftIcon={<PeopleIcon />}
      onClick={() => navigateTo('/admin/users')}
    />

    <Divider />
    <Subheader>My account</Subheader>

    <MenuItem
      primaryText="Profile"
      leftIcon={<PersonIcon />}
      onClick={() => navigateTo(`/admin/users/${loggedInUser.username}`)}
    />
    <MenuItem primaryText="Sign out" leftIcon={<ExitIcon />} onClick={() => logout()} />
  </Drawer>
);

MenuDrawer.propTypes = {
  loggedInUser: object,
  logout: func.isRequired,
  navigateTo: func.isRequired,
};

MenuDrawer.defaultProps = {
  loggedInUser: {},
};

const mapStateToProps = state => ({
  loggedInUser: getLoggedInUser(state),
});

const mapDispatchToProps = dispatch => ({
  logout: () => dispatch(authActions.logout()).then(() => dispatch(push('/'))),
  navigateTo: path => dispatch(push(path)),
});

const mergeProps = (stateProps, dispatchProps, ownProps) => ({
  ...stateProps,
  ...dispatchProps,
  ...ownProps,
  navigateTo: (path) => {
    ownProps.onRequestChange(false); // Ensure drawer closes upon navigation
    dispatchProps.navigateTo(path);
  },
});

export default connect(mapStateToProps, mapDispatchToProps, mergeProps)(MenuDrawer);
