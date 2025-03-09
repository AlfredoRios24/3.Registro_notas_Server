import React, { useState } from "react";
import "./Table.css";

interface TableProps {
  data: any[];
  columns: { key: string; label: string }[];
}


const Table: React.FC<TableProps> = ({ data, columns }) => {
  const [sortColumn, setSortColumn] = useState<string | null>(null);
  const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");

  const handleSort = (key: string) => {
    const isAsc = sortColumn === key && sortOrder === "asc";
    setSortColumn(key);
    setSortOrder(isAsc ? "desc" : "asc");
  };

  const sortedData = [...data].sort((a, b) => {
    if (!sortColumn) return 0;
    return sortOrder === "asc"
      ? a[sortColumn] > b[sortColumn]
        ? 1
        : -1
      : a[sortColumn] < b[sortColumn]
      ? 1
      : -1;
  });

  

  return (
    <table className="sortable-table">
      <thead>
        <tr>
          {columns.map((col) => (
            <th key={col.key} onClick={() => handleSort(col.key)}>
              {col.label} {sortColumn === col.key ? (sortOrder === "asc" ? "▲" : "▼") : ""}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {sortedData.map((row, index) => (
          <tr key={index}>
            {columns.map((col) => (
              <td key={col.key}>{row[col.key]}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;



  
