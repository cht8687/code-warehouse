// modules/Repos.js
import React from 'react'
import NavLink from './NavLink'

export default React.createClass({
  render() {
    return (
      <div>
        <h2>Repos</h2>
        {/* add some links */}
        <ul>
          <li><NavLink to="/repos/rackt/react-router">React Router</NavLink></li>
          <li><NavLink to="/repos/facebook/react">React</NavLink></li>
        </ul>
        {this.props.children}
      </div>
    )
  }
})