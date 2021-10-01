import React from 'react'
import { useParams } from 'react-router-dom'
import { useQuery } from 'react-query'

import { BookService } from '@/services/DatabaseService'
import PageHeading from '@/components/ui/PageHeading'
import BookDetail from '@/components/book/Detail'
import Alert from '@/components/ui/Alert'

function ScreenBookDetail() {
  const { id } = useParams()
  const { data, isLoading, error, status } = useQuery(
    ['book', { id }],
    BookService.getOne
  )

  return (
    <>
      <PageHeading title="Book Detail" />
      {error && <Alert type="error" message={error.message} />}
      {isLoading && (
        <Alert
          type="info"
          message="Loading..."
          innerClass="animate animate-pulse"
        />
      )}
      {status === 'success' && <BookDetail book={data} />}
    </>
  )
}

export default ScreenBookDetail
