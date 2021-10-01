import React from 'react'
import { Link, NavLink } from 'react-router-dom'
import { LoginIcon } from '@heroicons/react/outline'
import { UserCircleIcon } from '@heroicons/react/solid'

import './navbar.css'

function Navbar() {
  const routes = [
    {
      text: 'Categories',
      url: '/category',
    },
    {
      text: 'Authors',
      url: '/author',
    },
    {
      text: 'Books',
      url: '/book',
    },
  ]
  const links = routes.map((route, index) => (
    <NavLink to={route.url} key={index} className="btn btn-ghost btn-sm">
      {route.text}
    </NavLink>
  ))
  return (
    <nav className="navbar mb-2 shadow-lg bg-neutral text-neutral-content">
      <div className="navbar-start px-2 mx-2">
        <Link to="/" className="inline-block text-lg font-bold text-primary">
          <span>Sitepoint </span>
          <span className="text-base-content">Books</span>
        </Link>
      </div>
      <div className="navbar-center px-2 mx-2 hidden md:flex">
        <div className="flex items-stretch space-x-2">
          <NavLink to="/" className="btn btn-ghost btn-sm" exact>
            Home
          </NavLink>
          {links}
        </div>
      </div>
      <div className="navbar-end">
        <div className="flex space-x-4">
          <Link
            className="btn btn-sm btn-secondary w-28 rounded-full"
            to="/login"
          >
            <LoginIcon className="w-5 h-5 mr-2 -ml-1" aria-hidden="true" />
            Login
          </Link>
          <Link className="btn btn-sm btn-primary w-24 rounded-full" to="/join">
            <UserCircleIcon className="w-5 h-5 mr-2 -ml-1" aria-hidden="true" />
            Join
          </Link>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
