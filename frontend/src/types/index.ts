export interface Festival {
  id: number
  nome: string
  anno: number
  citta: string
  dataInizio: string
  dataFine: string
  descrizione: string | null
}

export interface Proiezione {
  id: number
  data: string
  ora: string
  oraFine: string
  stato: string
  filmId: number
  filmTitolo: string
  salaNome: string
}