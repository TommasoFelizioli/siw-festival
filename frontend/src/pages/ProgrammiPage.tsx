import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import type { Festival } from '../types'
import { getFestivals } from '../services/festivalService'
import { formattaData } from '../utils/date'

function ProgrammiPage() {
  const [festivals, setFestivals] = useState<Festival[]>([])
  const [caricamento, setCaricamento] = useState(true)
  const [errore, setErrore] = useState<string | null>(null)

  useEffect(() => {
    getFestivals()
      .then((dati) => setFestivals(dati))
      .catch((e: Error) => setErrore(e.message))
      .finally(() => setCaricamento(false))
  }, [])

  if (caricamento) {
    return <p>Caricamento...</p>
  }

  if (errore) {
    return <p className="errore">{errore}</p>
  }

  return (
    <div>
      <h1>Programmi dei festival</h1>
      <div className="griglia">
        {festivals.map((f) => (
          <Link key={f.id} className="scheda" to={`/festival/${f.id}`}>
            <div className="copertina segnaposto">{f.nome}</div>
            <h3>
              {f.nome} {f.anno}
            </h3>
            {f.citta}
            <br />
            dal {formattaData(f.dataInizio)} al {formattaData(f.dataFine)}
          </Link>
        ))}
      </div>
    </div>
  )
}

export default ProgrammiPage