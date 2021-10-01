import React from 'react'
import {
  CheckCircleIcon,
  BanIcon,
  ExclamationCircleIcon,
  InformationCircleIcon,
} from '@heroicons/react/outline'

function Alert({ type, message, innerClass }) {
  const icons = {
    info: InformationCircleIcon,
    success: CheckCircleIcon,
    warning: ExclamationCircleIcon,
    error: BanIcon,
  }
  return (
    <div className={`alert alert-${type}`}>
      <div className={`flex-1 ${innerClass}`}>
        {React.createElement(icons[type], {
          className: 'w-6 h-6 mx-2',
          'aria-hidden': 'true',
        })}
        <label>{message}</label>
      </div>
    </div>
  )
}

export default Alert
