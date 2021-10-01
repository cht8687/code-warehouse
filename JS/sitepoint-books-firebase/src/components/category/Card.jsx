import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import StorageService from '@/services/StorageService'

function CategoryCard({ category }) {
  const [imageLink, setImageLink] = useState()

  useEffect(async () => {
    const url = await StorageService.getImageURL(category.cover)
    setImageLink(url)
  }, [category])

  return (
    <div>
      <Link to={`/category/edit/${category.id}`}>
        <img src={imageLink} alt={category.name} />
      </Link>
    </div>
  )
}

export default CategoryCard
