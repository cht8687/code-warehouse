import React from 'react'

function Footer() {
  const currentYear = new Date().getFullYear()
  return (
    <footer className="py-4 text-center bg-neutral">
      <small text="xs light-900" font="light" tracking="wider">
        &copy; Copyright {currentYear} Sitepoint Books. All Rights Reserved
      </small>
    </footer>
  )
}

export default Footer
