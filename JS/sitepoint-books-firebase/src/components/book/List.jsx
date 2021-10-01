import React from 'react'
import { BookOpenIcon } from '@heroicons/react/outline'

import EmptyState from '@/components/ui/EmptyState'
import BookCard from './Card'

function BookList({ data }) {
  if (!data || data.length == 0) {
    return (
      <EmptyState
        icon={BookOpenIcon}
        title="No books"
        message="Start by adding a new book"
        btnLabel="Add Book"
        link="/book/create"
      />
    )
  }
  const cards = data.map((book, index) => <BookCard book={book} key={index} />)

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 md:gap-4 gap-y-4">
      {cards}
    </div>
  )
}

export default BookList
