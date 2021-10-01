import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

import PageHeading from '@/components/ui/PageHeading'
import Alert from '@/components/ui/Alert'

const schema = yup.object().shape({
  email: yup.string().label('Email').required().email(),
  password: yup.string().label('Password').required().min(8),
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref('password'), null], 'Passwords must match'),
})

function ScreenJoin() {
  const [message, setMessage] = useState('')
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(schema),
  })

  const onSubmit = (data) => {
    setMessage('Feature not yet available')
    console.log(data)
  }

  return (
    <>
      <PageHeading title="Join Sitepoint Books" />
      <div className="flex flex-col justify-center py-12 bg-base sm:px-6 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-md">
          <h2 className="mt-6 text-3xl font-extrabold text-center">
            Create User Account
          </h2>
        </div>

        <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
          <div className="px-4 py-8 shadow bg-base-200 sm:rounded-lg sm:px-10">
            <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
              {message && <Alert type="error" message={message} />}

              <div className="form-control">
                <label className="label" htmlFor="email">
                  <span className="label-text">Email address</span>
                </label>
                <input
                  type="email"
                  autoComplete="off"
                  className={`input input-bordered ${
                    errors.email && 'input-error'
                  }`}
                  {...register('email')}
                />
                {errors.email && (
                  <span className="mt-1 text-xs text-error">
                    {errors.email.message}
                  </span>
                )}
              </div>

              <div className="form-control">
                <label className="label" htmlFor="password">
                  <span className="label-text">Password</span>
                </label>
                <input
                  type="password"
                  className={`input input-bordered ${
                    errors.password && 'input-error'
                  }`}
                  {...register('password')}
                />
                {errors.password && (
                  <span className="mt-1 text-xs text-error">
                    {errors.password.message}
                  </span>
                )}
              </div>

              <div className="form-control">
                <label className="label" htmlFor="passwordConfirm">
                  <span className="label-text">Password Confirmation</span>
                </label>
                <input
                  type="password"
                  className={`input input-bordered ${
                    errors.passwordConfirm && 'input-error'
                  }`}
                  {...register('passwordConfirm')}
                />
                {errors.passwordConfirm && (
                  <span className="mt-1 text-xs text-error">
                    {errors.passwordConfirm.message}
                  </span>
                )}
              </div>

              <div>
                <button type="submit" className="btn btn-primary btn-block">
                  Sign up
                </button>
              </div>
            </form>

            <div className="flex justify-end mt-4 text-sm">
              Already have an account?{' '}
              <Link className="ml-2 link" to="/login">
                Log In
              </Link>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default ScreenJoin
