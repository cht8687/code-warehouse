import React from 'react'
import { Link } from 'react-router-dom'

function NotFound() {
  return (
    <div>
      <h1 className="text-warning">404 : Not Found!</h1>
      <Link to="/" className="inline-block mt-8 link">
        Go Home
      </Link>
    </div>
  )
}

export default NotFound
