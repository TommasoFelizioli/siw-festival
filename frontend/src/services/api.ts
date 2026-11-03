import axios from 'axios'

export const BACKEND_URL = import.meta.env.DEV ? 'http://localhost:8080' : ''

const api = axios.create({
  baseURL: BACKEND_URL,
})

export default api