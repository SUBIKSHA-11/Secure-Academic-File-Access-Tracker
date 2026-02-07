import React, { useEffect, useState } from "react";
import api from "../../services/api";
import { FaLayerGroup } from "react-icons/fa";
import FacultySubject from "./FacultySubject";
import "../../styles/faculty.css";

const FacultySemester = ({ department, goBack }) => {
  const [semesters, setSemesters] = useState([]);
  const [selectedSem, setSelectedSem] = useState(null);

  useEffect(() => {
    api.get(`/admin/semesters/${department.id}`)
      .then(res => setSemesters(res.data));
  }, [department]);

  if (selectedSem) {
    return (
      <FacultySubject
        department={department}
        semester={selectedSem}
        goBack={() => setSelectedSem(null)}
      />
    );
  }

  return (
    <div className="faculty-card">
      <button onClick={goBack}>⬅ Back</button>
      <h3>{department.name} – Semesters</h3>

      <div className="faculty-grid">
        {semesters.map(sem => (
          <div
            key={sem.id}
            className="faculty-item"
            onClick={() => setSelectedSem(sem)}
          >
            <FaLayerGroup className="icon" />
            <h4>Semester {sem.number}</h4>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultySemester;
