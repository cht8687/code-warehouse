import React, { Component } from 'react';
import Alert from 'react-s-alert';
import { Link } from 'react-router';
import { SubmissionError } from 'redux-form';
import { arrayOf } from 'prop-types';
import { Chip, RaisedButton, Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui';
import SendIcon from 'material-ui/svg-icons/content/send';
import AppPropTypes from '../../components/AppPropTypes';
import api from '../../services/api';
import theme from '../../theme';
import './ManageUsersPage.less';
import FormDialog from '../../components/forms/FormDialog';
import InviteUserForm from '../../components/forms/InviteUserForm';

const UserTable = ({ users }) => (
  <Table className="user-table" multiSelectable>
    <TableHeader>
      <TableRow>
        <TableHeaderColumn>Email</TableHeaderColumn>
        <TableHeaderColumn>Name</TableHeaderColumn>
        <TableHeaderColumn>Roles</TableHeaderColumn>
        <TableHeaderColumn>Status</TableHeaderColumn>
      </TableRow>
    </TableHeader>
    <TableBody>
      {users.map(user => (
        <TableRow key={user.username}>
          <TableRowColumn>
            <Link to={`/admin/users/${user.username}`}>{user.email}</Link>
          </TableRowColumn>
          <TableRowColumn>{user.name}</TableRowColumn>
          <TableRowColumn>
            <div className="roles">
              {user.roles.map(role => <Chip key={role} className="role" backgroundColor={theme.palette.primary1Color} labelColor="white">{role}</Chip>)}
            </div>
          </TableRowColumn>
          <TableRowColumn>{user.status}</TableRowColumn>
        </TableRow>
      ))}
    </TableBody>
  </Table>
);

UserTable.propTypes = {
  users: arrayOf(AppPropTypes.user).isRequired,
};

class ManageUsersPage extends Component {
  state = { inviteUserDialogOpen: false, users: [] };

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers() {
    api.users.list()
      .then(users => this.setState({ users }))
      .catch(error => Alert.error(`Error fetching users: ${error.message}`));
  }

  handleInviteUser = values =>
    api.users.invite(values)
      .then(() => {
        Alert.success('User invite sent!');
        this.closeInviteUserDialog();
      })
      .catch((error) => {
        throw new SubmissionError({ _error: error.message });
      });

  openInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: true });
  };

  closeInviteUserDialog = () => {
    this.setState({ inviteUserDialogOpen: false });
  };

  render() {
    const { inviteUserDialogOpen, users } = this.state;

    return (
      <div className="manage-users-page">
        <h1 className="display-1">Manage users</h1>

        <RaisedButton
          className="invite-user-btn"
          label="Invite User"
          icon={<SendIcon />}
          onClick={this.openInviteUserDialog}
        />

        <UserTable users={users} />

        <FormDialog
          title="Invite user"
          submitButtonText="Invite"
          formComponent={InviteUserForm}
          formName="inviteUser"
          open={inviteUserDialogOpen}
          onCancel={this.closeInviteUserDialog}
          onSubmit={this.handleInviteUser}
        />
      </div>
    );
  }
}

export default ManageUsersPage;
