import React, { useState, useEffect } from 'react';
// import { Link } from 'react-router-dom';
import { getSections, deleteSection } from '../../services/apiSections';
import './SectionList.css';

interface Section {
  idsection: number;
  titlesection: string;
}

const SectionsList: React.FC = () => {
  const [sections, setSections] = useState<Section[]>([]);
  const [showModal, setShowModal] = useState(false); // Estado para controlar el modal
  const [sectionToDelete, setSectionToDelete] = useState<number | null>(null); // Estado para eliminar sección

  // Obtener las secciones al cargar el componente
  useEffect(() => {
    const fetchSections = async () => {
      try {
        const sectionsData = await getSections();
        setSections(sectionsData);
      } catch (error) {
        console.error('Error fetching sections:', error);
      }
    };

    fetchSections();
  }, []);

  // Confirmar eliminación de una sección
  const confirmDelete = (id: number) => {
    setSectionToDelete(id);
    setShowModal(true); // Mostrar el modal de confirmación
  };

  // Eliminar la sección seleccionada
  const handleDeleteSection = async () => {
    if (sectionToDelete !== null) {
      try {
        await deleteSection(sectionToDelete);
        setSections(sections.filter((section) => section.idsection !== sectionToDelete)); // Actualiza las secciones
        setShowModal(false); // Cierra el modal
        alert('Sección eliminada exitosamente');
      } catch (error) {
        console.error('Error al eliminar la sección:', error);
        alert('Error al eliminar la sección');
      }
    }
  };

  // Cancelar la eliminación
  const cancelDelete = () => {
    setShowModal(false); // Cierra el modal si se cancela
  };

  return (
    <div>
      <h2>Lista de Secciones</h2>
      <ul>
        {sections.map((section) => (
          <li key={section.idsection} className="liListSection">
            <fieldset>
              <legend> Título de la Sección</legend>
              <h3>{section.titlesection}</h3>
            </fieldset>

            {/* Botón para eliminar la sección */}
            <button onClick={() => confirmDelete(section.idsection)} className="delete">
              Eliminar
            </button>
          </li>
        ))}
      </ul>

      {/* Modal de confirmación */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>¿Estás seguro de que deseas eliminar esta sección?</h3>
            <button onClick={handleDeleteSection} className="confirm-delete">Sí</button>
            <button onClick={cancelDelete} className="cancel-delete">Cancelar</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default SectionsList;
