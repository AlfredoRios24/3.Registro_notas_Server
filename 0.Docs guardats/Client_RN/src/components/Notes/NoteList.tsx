import React, { useEffect, useState } from 'react';
import { getNotes } from '../../services/api';
import './NoteList.css';
import Table from './Table';
import ViewNote from './ViewNote';
import StateCircle from './StateCiercleProps';

interface Note {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  startDate: string;
  endDate: string;
  state: "PENDING" | "IN_PROGRESS" | "COMPLETED";
}

export const NoteList: React.FC = () => {
  const [notes, setNotes] = useState<Note[]>([]);
  const [filteredNotes, setFilteredNotes] = useState<Note[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [selectedNoteIndex, setSelectedNoteIndex] = useState<number | null>(null);

  useEffect(() => {
    const fetchNotes = async () => {
      try {
        const notesData = await getNotes();
        setNotes(notesData);
        setFilteredNotes(notesData);
      } catch (error) {
        console.error('Error fetching notes', error);
      }
    };

    fetchNotes();
  }, []);

  useEffect(() => {
    const filtered = notes.filter((note) =>
      (note.title && note.title.toLowerCase().includes(searchTerm.toLowerCase())) ||
      (note.content && note.content.toLowerCase().includes(searchTerm.toLowerCase()))
    );
    setFilteredNotes(filtered);
  }, [searchTerm, notes]);

  const handleViewNote = (index: number) => {
    setSelectedNoteIndex(index);
  };

  const handleNext = () => {
    setSelectedNoteIndex((prevIndex) => {
      if (prevIndex !== null && prevIndex < filteredNotes.length - 1) {
        return prevIndex + 1;
      }
      return prevIndex;
    });
  };

  const handlePrev = () => {
    setSelectedNoteIndex((prevIndex) => {
      if (prevIndex !== null && prevIndex > 0) {
        return prevIndex - 1;
      }
      return prevIndex;
    });
  };

  const handleDeleteNote = (noteId: number) => {
    // Eliminar la nota
    setNotes(prevNotes => prevNotes.filter(note => note.id !== noteId));
    setFilteredNotes(prevFilteredNotes => prevFilteredNotes.filter(note => note.id !== noteId));
  
    // Verificar si hay notas restantes y actualizar el índice
    if (filteredNotes.length > 1) {
      // Si hay más de una nota, seleccionamos la siguiente o anterior según corresponda
      const newIndex = selectedNoteIndex !== null && selectedNoteIndex < filteredNotes.length
        ? selectedNoteIndex
        : selectedNoteIndex !== null && selectedNoteIndex > 0
        ? selectedNoteIndex - 1
        : null;
      setSelectedNoteIndex(newIndex);
    } else {
      setSelectedNoteIndex(null); // No hay más notas
    }
  };

  const data = filteredNotes.map((note, index) => ({
    title: note.title,
    dateStart: note.startDate.split("T")[0],
    dateEnd: note.endDate.split("T")[0],
    status:  <StateCircle state={note.state} />,
    view: <button className="btn-view" onClick={() => handleViewNote(index)}>Abrir</button>
  }));

  const columns = [
    { key: "title", label: "Titulo" },
    { key: "dateStart", label: "Fecha Inicio" },
    { key: "dateEnd", label: "Fecha Fin" },
    { key: "status", label: "Estado" },
    { key: "view", label: "Abrir" }
  ];

  return (
    <div>
      <h2>Lista de Notas</h2>
      <input
        type="text"
        placeholder="Buscar por título o contenido"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="search-input"
      />
      {selectedNoteIndex !== null && selectedNoteIndex < filteredNotes.length ? (
        <ViewNote
          key={selectedNoteIndex} // Cambiar la clave para forzar la re-renderización
          note={filteredNotes[selectedNoteIndex!]}
          onClose={() => setSelectedNoteIndex(null)}
          onNext={handleNext}
          onPrev={handlePrev}
          setNotes={setNotes}
          setFilteredNotes={setFilteredNotes}
          onDelete={handleDeleteNote}
        />
      ) : (
        <div>
          <h1>Mi Tabla Ordenable</h1>
          <Table data={data} columns={columns} />
        </div>
      )}
    </div>
  );
};

export default NoteList;
