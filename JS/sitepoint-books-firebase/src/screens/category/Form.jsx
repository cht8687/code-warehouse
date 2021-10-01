import React from 'react'
import { useParams, Redirect } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from 'react-query'

import { CategoryService } from '@/services/DatabaseService'
import PageHeading from '@/components/ui/PageHeading'
import CategoryForm from '@/components/category/Form'
import Alert from '@/components/ui/Alert'

function ScreenCategoryForm() {
  let { id } = useParams()
  const { data, isLoading, error, status } = useQuery(
    ['category', { id }],
    CategoryService.getOne
  )
  const queryClient = useQueryClient()

  const saveData = (data) => {
    if (id) {
      return CategoryService.update(id, data)
    } else {
      CategoryService.create(data)
    }
  }

  const mutation = useMutation((data) => saveData(data), {
    onSuccess: () => {
      if (id) queryClient.invalidateQueries(['category', { id }])
    },
  })

  const { isSuccess } = mutation

  const onSubmit = async (submittedData) => {
    mutation.mutate(submittedData)
  }

  if (isSuccess) {
    return <Redirect to="/category" />
  }

  if (!id) {
    return (
      <>
        <PageHeading title="Create Category" />
        <div className="mt-12">
          <CategoryForm values={{ cover: 'nocover' }} action={onSubmit} />
        </div>
      </>
    )
  }

  return (
    <>
      <PageHeading title="Edit Category" />
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
          <CategoryForm values={data} action={onSubmit} />
        )}
      </div>
    </>
  )
}

export default ScreenCategoryForm
