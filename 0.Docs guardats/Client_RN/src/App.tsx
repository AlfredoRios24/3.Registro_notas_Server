import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css'; // Importa el archivo CSS
import EditNote from './components/Notes/EditNote';
import { NoteList } from './components/Notes/NoteList';
import RegisterNote from './components/Notes/RegisterNote';

function App() {
  return (
    <Router>
      <div>
        {/* Encabezado de la aplicación */}
        <header>
          <h1>Mi Aplicación de Notas</h1>
        </header>

        {/* Barra de navegación */}
        <nav>
          <ul>
            <li>
              <Link to="/">Ver Notas</Link>
            </li>
            <li>
              <Link to="/register">Registrar Nota</Link>
            </li>
          </ul>
        </nav>

        {/* Contenido principal */}
        <div className="container">
          <Routes>
            <Route path="/" element={<NoteList />} />
            <Route path="/register" element={<RegisterNote />} />
            <Route path="/edit/:id" element={<EditNote />} />
          </Routes>
        </div>

        {/* Pie de página */}
        <footer>
          <p className='p-footer'>&copy; 2025 Mi Aplicación de Notas</p>
          <a href="http://localhost:8091/swagger-ui/index.html" target='blank'>Swagger</a>
        </footer>
      </div>
    </Router>
  );
}

export default App;
