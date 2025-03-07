// src/utils/dateUtils.ts

/**
 * Genera la fecha de creación en formato legible
 * @returns {string} Fecha actual formateada
 */
export const getCurrentDate = (): string => {
        const options: Intl.DateTimeFormatOptions = {
        year: "numeric",
        month: "long", // Mes en texto
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
        hour12: false, // Cambiar a true si prefieres formato de 12 horas
    };
  return new Date().toLocaleDateString("es-ES", options); // Cambia el idioma según sea necesario
};
