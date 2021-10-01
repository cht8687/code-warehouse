import React from 'react'
import { Link } from 'react-router-dom'
import { PlusIcon } from '@heroicons/react/solid'

function EmptyState({ title, message, icon, btnLabel, link }) {
  return (
    <div className="text-center">
      {React.createElement(icon, {
        className: 'w-16 h-16 mx-auto',
        'aria-hidden': 'true',
      })}
      <h3 className="mt-2 text-lg font-bold">{title}</h3>
      <p className="mt-1 text-sm">{message} </p>
      <div className="mt-6">
        <Link to={link} className="btn btn-secondary">
          <PlusIcon className="w-5 h-5 mr-2 -ml-1" aria-hidden="true" />
          {btnLabel}
        </Link>
      </div>
    </div>
  )
}

export default EmptyState
