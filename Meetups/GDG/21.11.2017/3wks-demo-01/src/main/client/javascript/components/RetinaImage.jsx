/* eslint-disable global-require, import/no-dynamic-require */

import path from 'path';
import React from 'react';
import PropTypes from 'prop-types';

const RetinaImage = ({ src, alt, ...rest }) => {
  const ext = path.extname(src);
  const srcMinusExt = src.substring(0, src.lastIndexOf(ext));

  return (
    <img
      src={require(`../../images/${srcMinusExt}${ext}`)}
      srcSet={require(`../../images/${srcMinusExt}@2x${ext}`)}
      alt={alt}
      {...rest}
    />
  );
};

RetinaImage.propTypes = {
  src: PropTypes.string.isRequired,
  alt: PropTypes.string.isRequired,
};

export default RetinaImage;
