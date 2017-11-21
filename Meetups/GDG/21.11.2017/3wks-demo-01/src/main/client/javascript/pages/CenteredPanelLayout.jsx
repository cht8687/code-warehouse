import React from 'react';
import { Paper } from 'material-ui';
import { arrayOf, node, oneOfType, string } from 'prop-types';

const CenteredPanelLayout = ({ children, title }) => (
  <div className="centered-panel-layout">
    <div>
      <h1 className="display-3">{title}</h1>

      <Paper className="centered-panel" zDepth={5}>
        {children}
      </Paper>
    </div>
  </div>
);

CenteredPanelLayout.propTypes = {
  children: oneOfType([node, arrayOf(node)]),
  title: string.isRequired,
};

CenteredPanelLayout.defaultProps = {
  children: null,
};

export default CenteredPanelLayout;
