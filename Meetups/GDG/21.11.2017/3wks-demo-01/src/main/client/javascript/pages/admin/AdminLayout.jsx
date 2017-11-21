import React, { Component } from 'react';
import Alert from 'react-s-alert';
import { arrayOf, node, oneOfType } from 'prop-types';
import { AppBar } from 'material-ui';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import MenuDrawer from './menu/MenuDrawer';

class AdminLayout extends Component {
  static propTypes = {
    children: oneOfType([node, arrayOf(node)]).isRequired,
  };

  constructor(props) {
    super(props);

    this.state = { drawerOpen: false };
  }

  toggleDrawer = () => {
    this.setState({ drawerOpen: !this.state.drawerOpen });
  };

  render() {
    const { drawerOpen } = this.state;
    const { children } = this.props;

    return (
      <div className="admin-layout">
        <Alert effect="slide" position="bottom-right" stack />

        <AppBar title="001" onLeftIconButtonTouchTap={this.toggleDrawer} />

        <MenuDrawer
          open={drawerOpen}
          onRequestChange={open => this.setState({ drawerOpen: open })}
        />

        <div className="main">{children}</div>
      </div>
    );
  }
}

export default AdminLayout;
