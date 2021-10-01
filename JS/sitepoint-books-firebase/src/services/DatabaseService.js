import { db } from '@/firebase'

class DatabaseService {
  collection

  constructor(collectionName) {
    this.collection = db.collection(collectionName)
  }

  getAll = async () => {
    const snapshot = await this.collection.get()
    return snapshot.docs.map((doc) => {
      return {
        id: doc.id,
        ...doc.data(),
      }
    })
  }

  getOne = async ({ queryKey }) => {
    const { id } = queryKey[1]
    if (!id) return
    const snapshot = await this.collection.doc(id).get()
    return snapshot.data()
  }

  getReference = async (documentReference) => {
    const res = await documentReference.get()
    const data = res.data()

    if (data && documentReference.id) {
      data.uid = documentReference.id
    }

    return data
  }

  create = async (data) => {
    return await this.collection.add(data)
  }

  update = async (id, values) => {
    return await this.collection.doc(id).update(values)
  }

  remove = async (id) => {
    return await this.collection.doc(id).delete()
  }
}

export const AuthorService = new DatabaseService('authors')

export const CategoryService = new DatabaseService('categories')

export const BookService = new DatabaseService('books')
