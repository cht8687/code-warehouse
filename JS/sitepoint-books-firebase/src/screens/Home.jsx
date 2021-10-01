import React from 'react'
import { Link } from 'react-router-dom'
import {
  CollectionIcon,
  UserCircleIcon,
  BookOpenIcon,
} from '@heroicons/react/outline'
import PageHeading from '@/components/ui/PageHeading'

function PageCardLink({ title, url, icon }) {
  return (
    <Link
      className="flex flex-col w-48 p-4 place-items-center bg-neutral rounded-box hover:text-primary"
      to={url}
    >
      {React.createElement(icon, {
        className: 'h-36 w-36',
        'aria-hidden': 'true',
      })}

      <h3 className="text-lg font-bold">{title} </h3>
    </Link>
  )
}

function Home() {
  const data = [
    {
      title: 'Category',
      url: '/category',
      icon: CollectionIcon,
    },
    {
      title: 'Author',
      url: '/author',
      icon: UserCircleIcon,
    },
    {
      title: 'Book',
      url: '/book',
      icon: BookOpenIcon,
    },
  ]

  const pageCards = data.map((item, index) => (
    <PageCardLink
      key={index}
      title={item.title}
      url={item.url}
      icon={item.icon}
    />
  ))
  return (
    <>
      <PageHeading title="Home" />
      <h2 className="mt-8 text-lg font-semibold text-primary">
        Browse books by:
      </h2>
      <div className="grid max-w-screen-md grid-cols-1 gap-4 mt-8 justify-items-center md:grid-cols-3 md:justify-items-start">
        {pageCards}
      </div>
    </>
  )
}

export default Home
