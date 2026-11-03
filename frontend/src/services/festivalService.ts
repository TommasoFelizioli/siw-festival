import axios from 'axios'
import api from './api'
import type { Festival, Proiezione } from '../types'

function messaggioErrore(e: unknown): string {
  if (axios.isAxiosError(e) && e.response?.status === 404) {
    return 'Festival non trovato'
  }
  return 'Impossibile caricare i dati dal server'
}

export async function getFestivals(): Promise<Festival[]> {
  try {
    const { data } = await api.get<Festival[]>('/api/festivals')
    return data
  } catch (e) {
    throw new Error(messaggioErrore(e))
  }
}

export async function getFestival(id: number): Promise<Festival> {
  try {
    const { data } = await api.get<Festival>(`/api/festivals/${id}`)
    return data
  } catch (e) {
    throw new Error(messaggioErrore(e))
  }
}

export async function getProgramma(id: number): Promise<Proiezione[]> {
  try {
    const { data } = await api.get<Proiezione[]>(`/api/festivals/${id}/screenings`)
    return data
  } catch (e) {
    throw new Error(messaggioErrore(e))
  }
}