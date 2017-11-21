import React from 'react';
import Avatar from 'react-avatar';
import { object } from 'prop-types';
import { Card, CardMedia } from 'material-ui';
import './ProfileCard.less';

const ProfileCard = ({ user }) => (
  <Card className="profile-card">
    <CardMedia
      className="profile-media"
      overlay={
        <div className="profile-details">
          <Avatar
            className="avatar"
            name={user.name}
            email={user.email}
            size={48}
            round
          />
          <div className="name">
            {user.name || user.email}
          </div>
          <div className="email">
            {user.name && user.email}
          </div>
        </div>
      }
      overlayContentStyle={{ background: 'none' }}
    />
  </Card>
);

ProfileCard.propTypes = {
  user: object.isRequired,
};

export default ProfileCard;
