import React from 'react'
import PageHeading from '@/components/ui/PageHeading'
import BookList from '@/components/book/List'

function ScreenBookListByCategory({ match: { params } }) {
  const { authorId: categoryId } = params
  return (
    <>
      <PageHeading title="Book List by Category" />
      <BookList author={categoryId} />
    </>
  )
}

export default ScreenBookListByCategory
