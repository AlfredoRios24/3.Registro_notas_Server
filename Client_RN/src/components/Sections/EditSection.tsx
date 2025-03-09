import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './EditSection.css';

interface Section {
  idsection: number;
  titlesection: string;
}

const EditSection: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // Obtener el id de la URL
  const navigate = useNavigate(); // Para redirigir después de la edición

  const [title, setTitle] = useState('');

  useEffect(() => {
    // Obtener los datos de la sección para editarla
    const fetchSection = async () => {
      try {
        const sectionId = Number(id); 
        if (!isNaN(sectionId)) {
          const response = await axios.get(`http://localhost:8091/api/sections/${sectionId}`);
          setTitle(response.data.titlesection); // Cambiar a titlesection
        } else {
          console.error('ID no válido');
        }
      } catch (error) {
        console.error('Error al obtener la sección para editar:', error);
      }
    };

    if (id) {
      fetchSection();
    }
  }, [id]);

  // Manejar el envío del formulario de edición
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const updatedSection = { titlesection: title }; // Cambiar a titlesection
      await axios.put(`http://localhost:8091/api/sections/${id}`, updatedSection);
      alert('Sección actualizada con éxito');
      navigate('/'); // Redirigir a la página principal después de editar
    } catch (error) {
      console.error('Error al actualizar la sección:', error);
      alert('Error al actualizar la sección');
    }
  };

  return (
    <div>
      <h2>Editar Sección</h2>
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
        <button type="submit" className='btn-edit'>Actualizar Sección</button>
      </form>
    </div>
  );
};

export default EditSection;
