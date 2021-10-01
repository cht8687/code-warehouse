import React from 'react'
import { useQuery, useMutation, useQueryClient } from 'react-query'

import { CategoryService } from '@/services/DatabaseService'
import PageHeading from '@/components/ui/PageHeading'
import CategoryList from '@/components/category/List'
import Alert from '@/components/ui/Alert'

function ScreenCategoryList() {
  const { data, isLoading, error, status } = useQuery(
    'books',
    CategoryService.getAll
  )

  const queryClient = useQueryClient()

  const deleteMutation = useMutation((id) => CategoryService.remove(id), {
    onSuccess: () => {
      queryClient.invalidateQueries('categories')
    },
  })

  const deleteAction = async (id) => {
    deleteMutation.mutateAsync(id)
  }

  return (
    <>
      <PageHeading title="Category List" />
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
          <CategoryList data={data} deleteAction={deleteAction} />
        )}
      </div>
    </>
  )
}

export default ScreenCategoryList
