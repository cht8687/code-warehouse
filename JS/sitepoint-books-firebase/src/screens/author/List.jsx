import React from 'react'
import { useQuery, useMutation, useQueryClient } from 'react-query'

import { AuthorService } from '@/services/DatabaseService'
import PageHeading from '@/components/ui/PageHeading'
import AuthorList from '@/components/author/List'
import Alert from '@/components/ui/Alert'

function ScreenAuthorList() {
  const { data, isLoading, error, status } = useQuery(
    'authors',
    AuthorService.getAll
  )

  const queryClient = useQueryClient()

  const deleteMutation = useMutation((id) => AuthorService.remove(id), {
    onSuccess: () => {
      queryClient.invalidateQueries('authors')
    },
  })

  const deleteAction = async (id) => {
    deleteMutation.mutateAsync(id)
  }

  return (
    <>
      <PageHeading title="Author List" />
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
          <AuthorList data={data} deleteAction={deleteAction} />
        )}
      </div>
    </>
  )
}

export default ScreenAuthorList
