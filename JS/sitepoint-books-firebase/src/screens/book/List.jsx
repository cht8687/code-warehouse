import React from 'react'
import { useQuery, useMutation, useQueryClient } from 'react-query'

import { BookService } from '@/services/DatabaseService'
import PageHeading from '@/components/ui/PageHeading'
import BookList from '@/components/book/List'
import Alert from '@/components/ui/Alert'

function ScreenBookList() {
  const { data, isLoading, error, status } = useQuery(
    'books',
    BookService.getAll
  )

  const queryClient = useQueryClient()

  const deleteMutation = useMutation((id) => BookService.remove(id), {
    onSuccess: () => {
      queryClient.invalidateQueries('books')
    },
  })

  const deleteAction = async (id) => {
    deleteMutation.mutateAsync(id)
  }

  return (
    <>
      <PageHeading title="Book List" />
      <div className="mt-12">
        {error && <Alert type="error" message={error.message} />}
        {isLoading && (
          <Alert
            type="info"
            message="Loading..."
            innerClass="animate animate-pulse"
          />
        )}
        {status === 'success' && (
          <BookList data={data} deleteAction={deleteAction} />
        )}
      </div>
    </>
  )
}

export default ScreenBookList
