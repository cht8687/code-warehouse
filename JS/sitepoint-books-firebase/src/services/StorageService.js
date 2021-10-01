import { storage } from '@/firebase'

const storageRef = storage.ref()

const getImageURL = async (filePath) => {
  const url = await storageRef.child(filePath).getDownloadURL()
  return url
}

const listFiles = async (folder) => {
  const listRef = storageRef.child(folder)
  const res = await listRef.listAll()
  const list = res.items.map((itemRef) => itemRef._delegate._location.path_)
  return list
}

const StorageService = {
  getImageURL,
  listFiles,
}

export default StorageService
