import React, { useEffect, useState } from "react";
import api from "../../services/api";
import { FaUniversity } from "react-icons/fa";
import FacultySemester from "./FacultySemester";
import "../../styles/faculty.css";

const FacultyBrowse = ({ onUploadSelect }) => {
  const [departments, setDepartments] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);
  const [search, setSearch] = useState("");

  useEffect(() => {
    api.get("/admin/departments")
      .then(res => setDepartments(res.data));
  }, []);

  if (selectedDept) {
    return (
      <FacultySemester
        department={selectedDept}
        goBack={() => setSelectedDept(null)}
        onUploadSelect={onUploadSelect}
      />
    );
  }

  return (
    <div className="faculty-card">
      <h3>Browse Departments</h3>

      <input
        placeholder="Search department"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        style={{ marginBottom: "15px", padding: "8px", width: "100%" }}
      />

      <div className="faculty-grid">
        {departments
          .filter(d =>
            d.name.toLowerCase().includes(search.toLowerCase())
          )
          .map(dept => (
            <div
              key={dept.id}
              className="faculty-item"
              onClick={() => setSelectedDept(dept)}
            >
              <FaUniversity className="icon" />
              <h4>{dept.name}</h4>
            </div>
          ))}
      </div>
    </div>
  );
};

export default FacultyBrowse;
