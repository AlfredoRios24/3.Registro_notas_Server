import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './EditNote.css'; // Importar los estilos

// eslint-disable-next-line @typescript-eslint/no-unused-vars
interface Note {
  id: number;
  title: string;
  content: string;
}

const EditNote: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // Obtener el id de la URL
  const navigate = useNavigate(); // Para redirigir después de la edición

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  useEffect(() => {
    // Obtener los datos de la nota para editarla
    const fetchNote = async () => {
        try {
          // Verifica que el id es un número antes de hacer la solicitud
          const noteId = Number(id); 
          if (!isNaN(noteId)) {
            const response = await axios.get(`http://localhost:8091/api/notes/${noteId}`);
            setTitle(response.data.title);
            setContent(response.data.content);
          } else {
            console.error('ID no válido');
          }
        } catch (error) {
          console.error('Error al obtener la nota para editar:', error);
        }
      };

    if (id) {
      fetchNote();
    }
  }, [id]);

  // Manejar el envío del formulario de edición
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const updatedNote = { title, content };
      await axios.put(`http://localhost:8091/api/notes/${id}`, updatedNote);
      alert('Nota actualizada con éxito');
      navigate('/'); // Redirigir a la página principal después de editar
    } catch (error) {
      console.error('Error al actualizar la nota:', error);
      alert('Error al actualizar la nota');
    }
  };

  return (
    <div>
      <h2>Editar Nota</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="title">Título:</label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="content">Contenido:</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <button type="submit" className='btn-edit'>Actualizar Nota</button>
      </form>
    </div>
  );
};

export default EditNote;
