// api.ts
import axios from 'axios';

const API_URL = 'http://localhost:8091/api';

const formatDate = (date: Date) => {
    return date.toISOString().split('.')[0]; // Remueve la zona horaria 'Z'
};

export const getNotes = async () => {
    try {
        const response = await axios.get(`${API_URL}/notes/`);
        return response.data;
    } catch (error) {
        console.error("Error fetching notes", error);
        throw error;
    }
};

// Función para registrar una nueva nota
export const registerNote = async (note: {
    titleNotes: string;
    contentNotes: string;
    startDate: Date;
    endDate: Date;
    createAt: Date;
    state: "PENDING" | "COMPLETED" | "IN_PROGRESS";
}) => {
    try {
        const formattedNote = {
            ...note,
            startDate: formatDate(note.startDate),
            endDate: formatDate(note.endDate),
            createAt: formatDate(note.createAt)
        };

        const response = await axios.post(`${API_URL}/register/`, formattedNote);
        return response.data;
    } catch (error) {
        console.error("Error registering note", error);
        throw error;
    }
};

// Función para obtener el id de la nota
export const getNoteById = async (id: number) => {
    try {
        const response = await axios.get(`${API_URL}/notes/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error al obtener la nota:', error);
        throw error;
    }
};


// Función para actualizar una nota 
export const updateNoteEdit = async (id: number, note: {
    title: string;
    content: string;
    startDate: string;
    endDate: string;
    state: "PENDING" | "IN_PROGRESS" | "COMPLETED";
}) => {
    try {
        const formattedStartDate = formatDate(new Date(note.startDate));
        const formattedEndDate = formatDate(new Date(note.endDate));

        const updatedNote = {
            title: note.title,
            content: note.content,
            startDate: formattedStartDate,
            endDate: formattedEndDate,
            state: note.state
        };

        const response = await axios.put(`${API_URL}/notes/${id}`, updatedNote);
        return response.data;
    } catch (error) {
        console.error("Error updating note content", error);
        throw error;
    }
};

// Función para eliminar una nota
export const deleteNote = async (id: number) => {
    try {
        const response = await axios.delete(`${API_URL}/notes/${id}`);
        return response.data;
    } catch (error) {
        console.error("Error deleting note", error);
        throw error;
    }
};
