import React from 'react';
import PropTypes from 'prop-types';

const Layout = ({ children }) => (
  <div className="default-layout">
    { children }
  </div>
);

Layout.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.node,
    PropTypes.arrayOf(PropTypes.node),
  ]).isRequired,
};

export default Layout;
