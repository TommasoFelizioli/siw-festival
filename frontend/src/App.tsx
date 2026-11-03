import { Route, Routes } from 'react-router-dom'
import Intestazione from './components/Intestazione'
import ProgrammiPage from './pages/ProgrammiPage'
import ProgrammaPage from './pages/ProgrammaPage'

function App() {
  return (
    <>
      <Intestazione />
      <main className="contenuto">
        <Routes>
          <Route path="/" element={<ProgrammiPage />} />
          <Route path="/festival/:id" element={<ProgrammaPage />} />
        </Routes>
      </main>
    </>
  )
}

export default App