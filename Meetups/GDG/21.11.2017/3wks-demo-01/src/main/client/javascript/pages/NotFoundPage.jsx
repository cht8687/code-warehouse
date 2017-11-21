import React from 'react';
import { RaisedButton } from 'material-ui';
import { Link } from 'react-router';

const NotFoundPage = () => (
  <div>
    <h1 className="display-1">Not Found</h1>

    <p>
      Sorry, we can&#39;t find the page you&#39;re looking for.
    </p>

    <RaisedButton containerElement={<Link to="/" />}>Home</RaisedButton>
  </div>
);

export default NotFoundPage;
