import React, { useState } from 'react';
import axios from 'axios';
import './RegisterNote.css';

const RegisterNote = () => {
  // Estados para manejar los inputs
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  // Función para manejar el envío del formulario
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Crear un objeto de la nota que se enviará al backend
    const newNote = {
      title: title,
      content: content,
    };

    try {
      // Enviar la solicitud POST al backend
      await axios.post('http://localhost:8091/api/register/', newNote);
      // Limpiar los campos del formulario después del envío
      setTitle('');
      setContent('');
      // Mostrar mensaje de éxito en una ventana emergente
      window.alert('Nota registrada con éxito'); // Ventana emergente (alert)
    } catch (error) {
      // Mostrar mensaje de error en una ventana emergente
      window.alert('Error al registrar la nota'); // Ventana emergente (alert)
      console.error('Error al registrar la nota:', error);
    }
  };

  return (
    <div>
      <h2>Registrar Nota</h2>
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
            className='textarea-content'
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <button type="submit" className='btn-Registrar'>Registrar Nota</button>
      </form>
    </div>
  );
};

export default RegisterNote;
