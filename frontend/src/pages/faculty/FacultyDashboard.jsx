import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/faculty.css";
import { FaUniversity } from "react-icons/fa";
import FacultyDepartmentView from "./FacultyDepartmentView";

const FacultyDashboard = () => {
  const [departments, setDepartments] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);

  const facultyEmail = localStorage.getItem("email");

  useEffect(() => {
    api.get("/departments")
      .then(res => setDepartments(res.data));
  }, []);

  if (selectedDept) {
    return (
      <FacultyDepartmentView
        department={selectedDept}
        goBack={() => setSelectedDept(null)}
      />
    );
  }

  return (
    <div className="faculty-bg">
      <div className="faculty-card">
        <h2>Welcome, {facultyEmail}</h2>
        <p>Select your department</p>
      </div>

      <div className="faculty-grid">
        {departments.map(dept => (
          <div
            key={dept.id}
            className="faculty-item"
            onClick={() => setSelectedDept(dept)}
          >
            <FaUniversity className="icon" />
            <h3>{dept.name}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultyDashboard;
