import axios from 'axios';

const API_URL2 = 'http://localhost:8092/api/sections';

export const getSections = async () => {
    try {
        const response = await axios.get(`${API_URL2}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching sections", error);
        throw error;
    }
};

export const registerSections = async (section: { titleSections: string }) => {
    try {
        const response = await axios.post(`${API_URL2}`, section);
        return response.data;
    } catch (error) {
        console.error("Error registering section", error);
        throw error;
    }
};

export const deleteSection = async (id: number) => {
    try {
        const response = await axios.delete(`${API_URL2}${id}`);
        return response.data;
    } catch (error) {
        console.error("Error deleting section", error);
        throw error;
    }
};
