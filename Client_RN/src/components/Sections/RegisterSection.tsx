import React, { useState } from 'react';
import axios from 'axios';
import './RegisterSection.css';

const RegisterSection: React.FC = () => {
  const [title, setTitle] = useState('');

  // Función para manejar el envío del formulario
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Crear un objeto de la sección que se enviará al backend
    const newSection = {
      titlesection: title, // Cambiar a titlesection
    };

    try {
      // Enviar la solicitud POST al backend
      await axios.post('http://localhost:8091/api/sections/', newSection);
      // Limpiar los campos del formulario después del envío
      setTitle('');
      // Mostrar mensaje de éxito en una ventana emergente
      window.alert('Sección registrada con éxito');
    } catch (error) {
      // Mostrar mensaje de error en una ventana emergente
      window.alert('Error al registrar la sección');
      console.error('Error al registrar la sección:', error);
    }
  };

  return (
    <div>
      <h2>Registrar Sección</h2>
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
        <button type="submit" className='btn-Registrar'>Registrar Sección</button>
      </form>
    </div>
  );
};

export default RegisterSection;
