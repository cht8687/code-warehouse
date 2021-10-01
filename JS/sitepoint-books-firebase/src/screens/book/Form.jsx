import React from 'react'
import { useParams } from 'react-router-dom'
import PageHeading from '@/components/ui/PageHeading'
import BookForm from '@/components/book/Form'

function ScreenBookForm() {
  let { id } = useParams()
  const title = !id ? 'Create' : 'Update'
  return (
    <>
      <PageHeading title={`${title} Book`} />
      <BookForm id={id} />
    </>
  )
}

export default ScreenBookForm
