import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import type { Festival, Proiezione } from '../types'
import { getFestival, getProgramma } from '../services/festivalService'
import { BACKEND_URL } from '../services/api'
import { formattaData } from '../utils/date'

function ProgrammaPage() {
  const { id } = useParams()
  const festivalId = Number(id)

  const [festival, setFestival] = useState<Festival | null>(null)
  const [proiezioni, setProiezioni] = useState<Proiezione[]>([])
  const [giorno, setGiorno] = useState('')
  const [sala, setSala] = useState('')
  const [caricamento, setCaricamento] = useState(true)
  const [errore, setErrore] = useState<string | null>(null)

  useEffect(() => {
    Promise.all([getFestival(festivalId), getProgramma(festivalId)])
      .then(([datiFestival, datiProgramma]) => {
        setFestival(datiFestival)
        setProiezioni(datiProgramma)
      })
      .catch((e: Error) => setErrore(e.message))
      .finally(() => setCaricamento(false))
  }, [festivalId])

  if (caricamento) {
    return <p>Caricamento...</p>
  }

  if (errore || festival === null) {
    return (
      <div>
        <p className="errore">{errore}</p>
        <Link to="/">Torna ai programmi</Link>
      </div>
    )
  }

  const giorni = [...new Set(proiezioni.map((p) => p.data))]
  const sale = [...new Set(proiezioni.map((p) => p.salaNome))].sort()

  const visibili = proiezioni.filter(
    (p) => (giorno === '' || p.data === giorno) && (sala === '' || p.salaNome === sala),
  )

  return (
    <div>
      <h1>
        Programma - {festival.nome} {festival.anno}
      </h1>
      <p>
        {festival.citta}, dal {formattaData(festival.dataInizio)} al {formattaData(festival.dataFine)}
      </p>

      <p>
        <label>Giorno:</label>
        <select value={giorno} onChange={(e) => setGiorno(e.target.value)}>
          <option value="">Tutti i giorni</option>
          {giorni.map((g) => (
            <option key={g} value={g}>
              {formattaData(g)}
            </option>
          ))}
        </select>
      </p>

      <p>
        <label>Sala:</label>
        <select value={sala} onChange={(e) => setSala(e.target.value)}>
          <option value="">Tutte le sale</option>
          {sale.map((s) => (
            <option key={s} value={s}>
              {s}
            </option>
          ))}
        </select>
      </p>

      <p>
        Proiezioni mostrate: {visibili.length} su {proiezioni.length}
      </p>

      {visibili.length === 0 ? (
        <p>Nessuna proiezione con questi filtri.</p>
      ) : (
        <table className="tabella">
          <thead>
            <tr>
              <th>Data</th>
              <th>Orario</th>
              <th>Film</th>
              <th>Sala</th>
              <th>Stato</th>
            </tr>
          </thead>
          <tbody>
            {visibili.map((p) => (
              <tr key={p.id}>
                <td>{formattaData(p.data)}</td>
                <td>
                  {p.ora.slice(0, 5)} - {p.oraFine.slice(0, 5)}
                </td>
                <td>
                  <a href={`${BACKEND_URL}/film/${p.filmId}`}>{p.filmTitolo}</a>
                </td>
                <td>{p.salaNome}</td>
                <td>{p.stato}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <p>
        <a href={`${BACKEND_URL}/festival/${festival.id}`}>Vai alla pagina del festival</a>
      </p>
      <p>
        <Link to="/">Tutti i programmi</Link>
      </p>
    </div>
  )
}

export default ProgrammaPage