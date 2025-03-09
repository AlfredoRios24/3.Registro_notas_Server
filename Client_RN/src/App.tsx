import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css'; // Importa el archivo CSS
import EditNote from './components/Notes/EditNote';
import NotesList from './components/Notes/NoteList';
import RegisterNote from './components/Notes/RegisterNote';
import EditSection from './components/Sections/EditSection'; // Importar el componente EditSection
import RegisterSection from './components/Sections/RegisterSection'; // Importar el componente RegisterSection
import SectionsList from './components/Sections/SectionList'; // Importa tu componente de secciones

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
            <li>
              <Link to="/sections">Ver Secciones</Link> {/* Nuevo enlace para secciones */}
            </li>
            <li>
              <Link to="/register-section">Registrar Sección</Link> {/* Enlace para registrar sección */}
            </li>
          </ul>
        </nav>

        {/* Contenido principal */}
        <div className="container">
          <Routes>
            <Route path="/" element={<NotesList />} />
            <Route path="/register" element={<RegisterNote />} />
            <Route path="/edit/:id" element={<EditNote />} />
            <Route path="/sections" element={<SectionsList />} /> {/* Nueva ruta para secciones */}
            <Route path="/edit-section/:id" element={<EditSection />} /> {/* Nueva ruta para editar sección */}
            <Route path="/register-section" element={<RegisterSection />} /> {/* Nueva ruta para registrar sección */}
          </Routes>
        </div>

        {/* Pie de página */}
        <footer>
          <p>&copy; 2025 Mi Aplicación de Notas</p>
          <a href="http://localhost:8092/swagger-ui/index.html" target="_blank" className='footer-swagger'>SWAGGER</a>
        </footer>
      </div>
    </Router>
  );
}

export default App;
