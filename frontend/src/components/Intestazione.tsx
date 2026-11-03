import { BACKEND_URL } from '../services/api'

function Intestazione() {
  return (
    <header className="intestazione">
      <a className="marchio" href={`${BACKEND_URL}/`}>
        CineFest
      </a>
      <nav className="menu">
        <a href={`${BACKEND_URL}/festival`}>Festival</a>
        <a href={`${BACKEND_URL}/film`}>Film</a>
        <a href={`${BACKEND_URL}/attori`}>Attori</a>
        <a href={import.meta.env.BASE_URL}>Programmi</a>
      </nav>
    </header>
  )
}

export default Intestazione