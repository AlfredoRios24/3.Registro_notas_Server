import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getNotes } from '../../services/apiNotes';
import { getCurrentDate } from '../../utils/dateUtils'; // Importamos la función de fechas
import './NoteList.css';


interface Note {
  id: number;
  title: string;
  content: string;
  createdAt: string; // Fecha de creación
}

const NotesList: React.FC = () => {
  const [notes, setNotes] = useState<Note[]>([]);
  const [filteredNotes, setFilteredNotes] = useState<Note[]>([]); // Estado para las notas filtradas
  const [searchTerm, setSearchTerm] = useState<string>(''); // Estado para el término de búsqueda
  const [showModal, setShowModal] = useState(false); // Estado para controlar la visibilidad del modal
  const [noteToDelete, setNoteToDelete] = useState<number | null>(null); // Estado para almacenar la nota que se va a eliminar

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const notesData = await getNotes();

        // Asignamos la fecha de creación si no existe
        const notesWithCreationDate = notesData.map((note: Note) => ({
          ...note,
          createdAt: note.createdAt || getCurrentDate(), // Si no tiene fecha de creación, se asigna la actual
        }));

        setNotes(notesWithCreationDate);
        setFilteredNotes(notesWithCreationDate); // Inicializa las notas filtradas con todas las notas
      } catch (error) {
        console.error('Error fetching notes', error);
      }
    };

    fetchNotes();
  }, []);

  // Filtrar las notas según el término de búsqueda
  useEffect(() => {
    const filtered = notes.filter((note) =>
      note.title.toLowerCase().includes(searchTerm.toLowerCase()) || // Filtra por título
      note.content.toLowerCase().includes(searchTerm.toLowerCase()) // Filtra por contenido
    );
    setFilteredNotes(filtered); // Actualiza las notas filtradas
  }, [searchTerm, notes]); // Se vuelve a filtrar cada vez que cambia el término de búsqueda o las notas

  // Eliminar una nota
  const confirmDelete = (id: number) => {
    setNoteToDelete(id);
    setShowModal(true); // Mostrar el modal de confirmación
  };

  const deleteNote = async () => {
    if (noteToDelete !== null) {
      try {
        await axios.delete(`http://localhost:8091/api/notes/${noteToDelete}`);
        setNotes(notes.filter((note) => note.id !== noteToDelete)); // Actualiza el estado para quitar la nota eliminada
        setFilteredNotes(filteredNotes.filter((note) => note.id !== noteToDelete)); // Actualiza las notas filtradas
        setShowModal(false); // Cierra el modal después de eliminar
        alert('Nota eliminada exitosamente');
      } catch (error) {
        console.error('Error al eliminar la nota:', error);
        alert('Error al eliminar la nota');
      }
    }
  };

  const cancelDelete = () => {
    setShowModal(false); // Cierra el modal si se cancela la acción
  };

  return (
    <div>
      <h2>Lista de Notas</h2>

      {/* Barra de búsqueda */}
      <input
        type="text"
        placeholder="Buscar por título o contenido"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)} // Actualiza el término de búsqueda
        className="search-input"
      />

      <ul>
        {filteredNotes.length > 0 ? (
          filteredNotes.map((note) => (
            <li key={note.id} className='liListNote'>
              <fieldset>
                <legend> Titulo de la Nota</legend>
                <h3>{note.title}</h3>
                <legend>Contenido de la Nota </legend>
                <p>{note.content}</p>
                <p className='p-fecha'>Fecha de creación: {note.createdAt}</p> {/* Mostramos la fecha de creación */}
              </fieldset>
              {/* Botón para eliminar la nota */}
              <button onClick={() => confirmDelete(note.id)} className="delete">
                Eliminar
              </button>
              {/* Enlace para editar la nota */}
              <Link to={`/edit/${note.id}`}>
                <button className="edit">Editar</button>
              </Link>
              

            </li>
          ))
        ) : (
          <p>No se encontraron notas que coincidan con tu búsqueda.</p>
        )}
      </ul>

      {/* Modal de confirmación */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>¿Estás seguro de que deseas eliminar esta nota?</h3>
            <button onClick={deleteNote} className="confirm-delete">Sí</button>
            <button onClick={cancelDelete} className="cancel-delete">Cancelar</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default NotesList;
