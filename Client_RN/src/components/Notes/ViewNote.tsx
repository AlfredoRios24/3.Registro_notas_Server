import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./ViewNote.css"; // Asegúrate de tener los estilos adecuados en este archivo
import StateCircle from "./StateCiercleProps";

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

const ViewNote: React.FC<ViewNoteProps> = ({
  note,
  onClose,
  onNext,
  onPrev,
  setNotes,
  setFilteredNotes,
  onDelete,
}) => {
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
        setNotes((prevNotes) =>
          prevNotes.filter((note) => note.id !== noteToDelete)
        );
        setFilteredNotes((prevFilteredNotes) =>
          prevFilteredNotes.filter((note) => note.id !== noteToDelete)
        );
        setShowModal(false);
        alert("Nota eliminada exitosamente");
        navigate("/");
      } catch (error) {
        console.error("Error al eliminar la nota:", error);
        alert("Error al eliminar la nota");
      }
    }
  };

  return (
    <div className="register-note-wrapper">
      <h2>Detalle de la Nota</h2>
      
      <form>

        <div>
          <label>Título:</label>
          <input type="text" value={note.title} readOnly />
          
        </div>
        <div>
          <label>Contenido:</label>
          <textarea className="textarea-content" value={note.content} readOnly />
        </div>

        <div className="form-date">
          <div>
            <label>Fecha Inicio:</label>
            <input type="date" value={note.startDate.split("T")[0]} readOnly />
          </div>

          <div>
            <label>Fecha Fin:</label>
            <input type="date" value={note.endDate.split("T")[0]} readOnly />
          </div>
        </div>
        
        <div className="form-state">
          {["PENDING", "IN_PROGRESS", "COMPLETED"].map((status) => (
            <div
              key={status}
              className={`state-option ${
                note.state === status ? status.toLowerCase() : ""
              }`}
            >
              {status === "PENDING"
                ? "Pendiente"
                : status === "IN_PROGRESS"
                ? "En progreso"
                : "Completado"}
            </div>
          ))}
        </div>

      <div style={{ display: "flex", gap: "10px", marginTop: "20px", width: "100%" }}>
      <Link to={`/edit/${note.id}`} style={{ flex: 1 }}>
          <button
            type="button"
            className="edit"
            style={{ backgroundColor: "#ff4081",width: "50%"}} // Azul para el botón Editar
          >
            Editar
          </button>
        </Link>

        <button
          type="button"
          className="delete"
          onClick={() => confirmDelete(note.id)}
          style={{ backgroundColor: "#e74c3c", width: "40%" }} // Rojo para el botón Eliminar
        >
          Eliminar
      </button>

    </div>

      </form>
      <div >

        {/* Botón para cerrar (X) */}
        <button className="close-btn" onClick={onClose}>
          X
        </button>

        {/* Información adicional de la nota */}
      </div>

      {/* Botones de navegación lateral */}
      <button className="nav-btn left" onClick={onPrev}>
        &#10094; {/* Flecha izquierda */}
      </button>
      <button className="nav-btn right" onClick={onNext}>
        &#10095; {/* Flecha derecha */}
      </button>

      {/* Modal de confirmación para eliminar */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>¿Estás seguro de que deseas eliminar esta nota?</h3>
            <button onClick={deleteNote} className="confirm-delete">
              Sí
            </button>
            <button
              onClick={() => setShowModal(false)}
              className="cancel-delete"
            >
              NO
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ViewNote;
