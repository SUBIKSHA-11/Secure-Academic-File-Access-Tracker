import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/faculty.css";
import { FaBook } from "react-icons/fa";
import FacultySubjectView from "./FacultySubjectView";

const FacultyDepartmentView = ({ department, goBack }) => {
  const [semesters, setSemesters] = useState([]);
  const [selectedSem, setSelectedSem] = useState(null);

  useEffect(() => {
    api
      .get(`/semesters/${department.id}`)
      .then(res => setSemesters(res.data))
      .catch(console.error);
  }, [department]);

  if (selectedSem) {
    return (
      <FacultySubjectView
        department={department}
        semester={selectedSem}
        goBack={() => setSelectedSem(null)}
      />
    );
  }

  return (
    <div className="faculty-bg">
      <button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>{department.name}</h2>
        <p>Select semester</p>
      </div>

      <div className="faculty-grid">
        {semesters.map(sem => (
          <div
            key={sem.id}
            className="faculty-item"
            onClick={() => setSelectedSem(sem)}
          >
            <FaBook className="icon" />
            <h3>Semester {sem.number}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultyDepartmentView;
