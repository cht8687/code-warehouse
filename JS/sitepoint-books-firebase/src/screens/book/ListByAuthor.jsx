import React from 'react'
import PageHeading from '@/components/ui/PageHeading'
import BookList from '@/components/book/List'

function ScreenBookListByAuthor({ match: { params } }) {
  const { authorId } = params
  return (
    <>
      <PageHeading title="Book List by Author" />
      <BookList author={authorId} />
    </>
  )
}

export default ScreenBookListByAuthor
