import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './ViewNote.css';
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

interface ViewNoteProps {
  note: Note;
  onClose: () => void;
  onNext: () => void;
  onPrev: () => void;
  setNotes: React.Dispatch<React.SetStateAction<Note[]>>;
  setFilteredNotes: React.Dispatch<React.SetStateAction<Note[]>>;
  onDelete: (noteId: number) => void;
}

const ViewNote: React.FC<ViewNoteProps> = ({ note, onClose, onNext, onPrev, setNotes, setFilteredNotes, onDelete }) => {
  const [showModal, setShowModal] = useState(false);
  const [noteToDelete, setNoteToDelete] = useState<number | null>(null);
  const navigate = useNavigate();

  const confirmDelete = (id: number) => {
    setNoteToDelete(id);
    setShowModal(true);
  };

  const deleteNote = async () => {
    if (noteToDelete !== null) {
      try {
        await axios.delete(`http://localhost:8091/api/notes/${noteToDelete}`);
        setNotes(prevNotes => prevNotes.filter((note) => note.id !== noteToDelete));
        setFilteredNotes(prevFilteredNotes => prevFilteredNotes.filter((note) => note.id !== noteToDelete));
        setShowModal(false);
        alert('Nota eliminada exitosamente');
        navigate('/');
      } catch (error) {
        console.error('Error al eliminar la nota:', error);
        alert('Error al eliminar la nota');
      }
    }
  };

  return (
    <div className="view-note-wrapper">
      <button className="nav-btn left" onClick={onPrev}>&#10094;</button>
      <div className="view-note-container">
        <button className="close-btn" onClick={onClose}>X</button>
        <div className="note-content">
          <h2>{note.title}</h2>
          <div className='content-container'>
            <p>{note.content}</p>
          </div>
          
          <div className="dates-container">
            <div className="date-item">
              <p className="p-fecha">Fecha de inicio: </p>
                <p>{note.startDate.split("T")[0]}</p>
            </div>
            <div className="date-item">
              <p className="p-fecha">Fecha de fin: </p>
              <p>{note.endDate.split("T")[0]}</p>
            </div>
          </div>

          <div className="state-container">
            <p><strong> Estado </strong></p>
            {note.state}<StateCircle state={note.state} />
          </div>
          <div className='actions-container'>
            <Link to={`/edit/${note.id}`}>
              <button className="edit">Editar</button>
            </Link>
            <button onClick={() => confirmDelete(note.id)} className="delete">
              Eliminar
            </button>
          </div>
        </div>
      </div>
      <button className="nav-btn right" onClick={onNext}>&#10095;</button>
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>¿Estás seguro de que deseas eliminar esta nota?</h3>
            <button onClick={deleteNote} className="confirm-delete">Sí</button>
            <button onClick={() => setShowModal(false)} className="cancel-delete">NO</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewNote;


