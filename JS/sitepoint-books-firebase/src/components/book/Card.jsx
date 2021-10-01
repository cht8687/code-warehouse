import React, { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'

import { BookService } from '@/services/DatabaseService'
import StorageService from '@/services/StorageService'

function BookCard({ book }) {
  const [coverURL, setCoverURL] = useState()
  const [author, setAuthor] = useState()

  // Get Cover image URL
  useEffect(async () => {
    const url = await StorageService.getImageURL(book.cover)
    setCoverURL(url)
  }, [book])

  // Resolve book.author_id document reference
  useEffect(async () => {
    const authorRef = await BookService.getReference(book.author_id)
    setAuthor(authorRef)
  }, [book])

  return (
    <div>
      <Link to={`/book/detail/${book.id}`}>
        <div className="w-52">
          <img src={coverURL} alt={book.title} />
        </div>
        {author && <p className="text-primary">By {author.name}</p>}
      </Link>
    </div>
  )
}

export default BookCard
