import React, { useEffect, useState } from "react";
import api from "../../services/api";
import { FaBook } from "react-icons/fa";
import FacultyLesson from "./FacultyLesson";
import "../../styles/faculty.css";

const FacultySubject = ({ department, semester, goBack }) => {
  const [subjects, setSubjects] = useState([]);
  const [selectedSub, setSelectedSub] = useState(null);

  useEffect(() => {
    api.get(`/admin/subjects/${department.id}/${semester.id}`)
      .then(res => setSubjects(res.data));
  }, [department, semester]);

  if (selectedSub) {
    return (
      <FacultyLesson
        subject={selectedSub}
        goBack={() => setSelectedSub(null)}
      />
    );
  }

  return (
    <div className="faculty-card">
      <button onClick={goBack}>⬅ Back</button>
      <h3>Semester {semester.number} – Subjects</h3>

      <div className="faculty-grid">
        {subjects.map(sub => (
          <div
            key={sub.id}
            className="faculty-item"
            onClick={() => setSelectedSub(sub)}
          >
            <FaBook className="icon" />
            <h4>{sub.name}</h4>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultySubject;
