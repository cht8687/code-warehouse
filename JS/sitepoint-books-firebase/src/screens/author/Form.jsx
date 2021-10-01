import React from 'react'
import { useParams, Redirect } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from 'react-query'
import { AuthorService } from '@/services/DatabaseService'

import PageHeading from '@/components/ui/PageHeading'
import AuthorForm from '@/components/author/Form'
import Alert from '@/components/ui/Alert'

function ScreenAuthorForm() {
  const { id } = useParams()
  const { data, isLoading, error, status } = useQuery(
    ['author', { id }],
    AuthorService.getOne
  )

  const queryClient = useQueryClient()

  const saveData = (data) => {
    if (id) {
      return AuthorService.update(id, data)
    } else {
      AuthorService.create(data)
    }
  }

  const mutation = useMutation((data) => saveData(data), {
    onSuccess: () => {
      if (id) queryClient.invalidateQueries(['author', { id }])
    },
  })

  const { isSuccess } = mutation

  const onSubmit = async (submittedData) => {
    mutation.mutate(submittedData)
  }

  if (isSuccess) {
    return <Redirect to="/author" />
  }

  if (!id) {
    return (
      <>
        <PageHeading title="Create Author" />
        <div className="mt-12">
          {error && <Alert type="error" message={error.message} />}
          <AuthorForm submit={onSubmit} />
        </div>
      </>
    )
  }

  return (
    <>
      <PageHeading title="Edit Author" />
      <div className="mt-12">
        {error && <Alert type="error" message={error.message} />}
        {isLoading && (
          <Alert
            type="info"
            message="Loading..."
            innerClass="animate animate-pulse"
          />
        )}
        {status === 'success' && <AuthorForm values={data} submit={onSubmit} />}
      </div>
    </>
  )
}

export default ScreenAuthorForm
