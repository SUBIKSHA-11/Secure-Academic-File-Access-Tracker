import React, { useEffect, useState } from "react";
import api from "../../services/api";
import { FaUniversity } from "react-icons/fa";
import FacultySemester from "./FacultySemester";
import "../../styles/faculty.css";

const FacultyBrowse = () => {
  const [departments, setDepartments] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);

  useEffect(() => {
    api.get("/admin/departments")
      .then(res => setDepartments(res.data));
  }, []);

  if (selectedDept) {
    return (
      <FacultySemester
        department={selectedDept}
        goBack={() => setSelectedDept(null)}
      />
    );
  }

  return (
    <div className="faculty-card">
      <h3>Select Department</h3>

      <div className="faculty-grid">
        {departments.map(dept => (
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
