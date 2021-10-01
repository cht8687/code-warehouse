import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import {
  UserCircleIcon,
  PencilAltIcon,
  TrashIcon,
} from '@heroicons/react/outline'

import EmptyState from '@/components/ui/EmptyState'
import DeleteModal from '@/components/ui/DeleteModal'

function AuthorList({ data, deleteAction }) {
  const [selected, setSelected] = useState()
  const [openModal, setOpenModal] = useState(false)
  if (!data || data.length == 0) {
    return (
      <EmptyState
        icon={UserCircleIcon}
        title="No authors"
        message="Start by adding a new author"
        btnLabel="Add Author"
        link="/author/create"
      />
    )
  }

  const showDeleteModal = (id) => {
    setSelected(id)
    setOpenModal(true)
  }

  const deleteModalAction = () => {
    deleteAction(selected)
    setOpenModal(false)
  }

  const cancelModalAction = () => {
    setOpenModal(false)
  }

  return (
    <div className="overflow-x-auto">
      <DeleteModal
        open={openModal}
        deleteAction={deleteModalAction}
        cancelAction={cancelModalAction}
      />
      <div className="mb-4">
        <Link to="/author/create" className="btn btn-secondary btn-sm">
          <UserCircleIcon className="w-5 h-5 mr-2 -ml-1" aria-hidden="true" />
          New Author
        </Link>
      </div>
      <table className="table w-full max-w-screen-lg">
        <thead>
          <tr>
            <th>Name</th>
            <th scope="col">
              <span className="sr-only">Edit</span>
            </th>
            <th scope="col">
              <span className="sr-only">Delete</span>
            </th>
          </tr>
        </thead>
        <tbody>
          {data.map((author, index) => (
            <tr key={index}>
              <td>{author.name}</td>
              <td>
                <Link
                  to={`/author/edit/${author.id}`}
                  className="text-primary hover:text-primary-focus"
                  title={`Edit ${author.name}`}
                >
                  <PencilAltIcon
                    className="w-5 h-5 mr-2 -ml-1"
                    aria-hidden="true"
                  />
                </Link>
              </td>
              <td>
                <button
                  type="button"
                  title={`Delete ${author.name}`}
                  className="text-secondary-content"
                  onClick={() => showDeleteModal(author.id)}
                >
                  <TrashIcon
                    className="w-5 h-5 mr-2 -ml-1"
                    aria-hidden="true"
                  />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default AuthorList
