import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/faculty.css";
import { FaBookOpen } from "react-icons/fa";
import FacultyLessonView from "./FacultyLessonView";
import axios from "axios";

const FacultySubjectView = ({ department, semester, goBack }) => {
  const [subjects, setSubjects] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState(null);
  const token = localStorage.getItem("token");
  const loadSubjects = () => {
  api
    .get(`/subjects/${department.id}/${semester.id}`)
    .then(res => setSubjects(res.data))
    .catch(console.error);
};

  useEffect(() => {
   loadSubjects();
  }, [department, semester]);

  if (selectedSubject) {
    return (
      <FacultyLessonView
        subject={selectedSubject}
        goBack={() => setSelectedSubject(null)}
      />
    );
  }
  return (
    <div className="faculty-bg">
     <button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>{department.code} • Semester {semester.number}</h2>
        <p>Select a subject</p>
      </div>
      


      <div className="faculty-grid">
        {subjects.map(sub => (
          <div
            key={sub.id}
            className="faculty-item"
            onClick={() => setSelectedSubject(sub)}
          >
            <FaBookOpen className="icon" />
            <h3>{sub.name}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultySubjectView;
