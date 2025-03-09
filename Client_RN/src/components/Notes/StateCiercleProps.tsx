import React from "react";

interface StateCircleProps {
  state: "PENDING" | "IN_PROGRESS" | "COMPLETED";
}

const stateColors = {
  PENDING: "red",
  IN_PROGRESS: "orange",
  COMPLETED: "lightgreen",
};

const StateCircle: React.FC<StateCircleProps> = ({ state }) => {
  return (
    <div style={{ display: "flex", gap: "5px", alignItems: "center" }}>
      {Object.entries(stateColors).map(([key, color]) => {
        const isActive = key === state; // Verifica si es el estado actual
        return (
          <span
            key={key}
            style={{
              backgroundColor: color,
              display: "inline-block",
              width: isActive ? "18px" : "15px", // Más grande si está activo
              height: isActive ? "18px" : "15px",
              borderRadius: "50%",
              opacity: isActive ? 1 : 0.3, // Más visible si está activo
              border: isActive ? "3px solid black" : "none", // Borde negro solo en el activo
              transition: "all 0.3s ease-in-out",
            }}
          ></span>
        );
      })}
    </div>
  );
};

export default StateCircle;
