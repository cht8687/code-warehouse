import React, { useState, useEffect } from 'react'

import { BookService } from '@/services/DatabaseService'
import StorageService from '@/services/StorageService'

function BookDetail({ book }) {
  const [author, setAuthor] = useState()
  const [category, setCategory] = useState()
  const [coverURL, setCoverURL] = useState()

  // Resolve book.author_id document reference
  useEffect(async () => {
    const authorRef = await BookService.getReference(book.author_id)
    setAuthor(authorRef)
  }, [book])

  // Resolve book.category_id document reference
  useEffect(async () => {
    const categoryRef = await BookService.getReference(book.category_id)
    setCategory(categoryRef)
  }, [book])

  // Get Cover image URL
  useEffect(async () => {
    const url = await StorageService.getImageURL(book.cover)
    setCoverURL(url)
  }, [book])

  return (
    <div className="mt-8">
      <div className="flex justify-between">
        <h2 className="text-primary font-bold">{book.title}</h2>
        {category && <p className="capitalize text-sm">{category.name}</p>}
      </div>
      <div className="grid grid-cols-2 mt-8">
        <div className="w-72">
          <img className="w-full" src={coverURL} />
        </div>
        <div>
          {author && <p className="text-primary">By {author.name}</p>}
          <p className="mt-4 text-justify">{book.description} </p>
          <a
            href={book.url}
            target="_blank"
            className="btn btn-primary btn-block mt-8"
          >
            Read
          </a>
        </div>
      </div>
    </div>
  )
}

export default BookDetail
