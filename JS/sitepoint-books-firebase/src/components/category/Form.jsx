import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

import StorageService from '@/services/StorageService'
import Alert from '@/components/ui/Alert'

const schema = yup.object().shape({
  name: yup.string().label('Name').required().min(2),
  cover: yup.string().label('Cover').required(),
})

function CategoryForm({ values, action }) {
  const [errorMsg, setErrorMsg] = useState('')
  const [coverURL, setCoverURL] = useState()
  const [coverOptions, setCoverOptions] = useState([])

  const {
    register,
    watch,
    reset,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  })
  const watchCover = watch('cover')

  // Get list of available images from cloud storage
  useEffect(async () => {
    const availableFiles = await StorageService.listFiles('categories')
    setCoverOptions(availableFiles)
  }, [])

  // Load current document values if available
  useEffect(() => {
    reset(values)
  }, [reset])

  // Display the current cover
  useEffect(async () => {
    if (watchCover && watchCover != 'nocover') {
      const url = await StorageService.getImageURL(watchCover)
      setCoverURL(url)
    }
  }, [watchCover])

  const onSubmit = async (submittedData) => {
    try {
      await action(submittedData) // submit data to action handler
    } catch (err) {
      setErrorMsg(err.message)
    }
  }

  return (
    <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
      <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
        {errorMsg && <Alert type="error" message={errorMsg} />}

        <div className="form-control">
          <label className="label" htmlFor="name">
            <span className="label-text">Name</span>
          </label>
          <input
            type="text"
            autoComplete="off"
            {...register('name')}
            className={`input input-bordered ${errors.name && 'input-error'}`}
          />
          {errors.name && (
            <span className="mt-1 text-xs text-error">
              {errors.name.message}
            </span>
          )}
        </div>

        <div className="form-control">
          <label className="label" htmlFor="cover">
            <span className="label-text">Select Cover</span>
          </label>
          <div className="flex items-center">
            <select
              {...register('cover')}
              value={watchCover}
              className={`select select-bordered w-full ${
                errors.cover ? 'select-error' : ''
              }`}
            >
              <option disabled="disabled" value="nocover">
                Choose a cover
              </option>
              {coverOptions.map((fileName, index) => (
                <option key={index} value={fileName}>
                  {fileName}
                </option>
              ))}
            </select>
            <button type="button" className="btn btn-secondary btn-sm ml-8">
              Upload New
            </button>
          </div>
          {errors.cover && (
            <span className="mt-1 text-xs text-error">
              {errors.cover.message}
            </span>
          )}
          <div className="avatar mt-4">
            <div className="mb-8 rounded-btn w-36 h-36">
              {coverURL && <img src={coverURL} />}
            </div>
          </div>
        </div>

        <div className="flex justify-end space-x-4">
          <button type="submit" className="btn btn-primary btn-sm w-24">
            Save
          </button>
          <Link to="/category" className="btn btn-outline btn-sm w-24">
            Cancel
          </Link>
        </div>
      </form>
    </div>
  )
}

export default CategoryForm
