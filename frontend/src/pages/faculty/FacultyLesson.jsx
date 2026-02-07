import React, { useEffect, useState } from "react";
import api from "../../services/api";
import { FaListAlt } from "react-icons/fa";
import "../../styles/faculty.css";

const FacultyLesson = ({ subject, goBack }) => {
  const [lessons, setLessons] = useState([]);

  useEffect(() => {
    api.get(`/admin/lessons/${subject.id}`)
      .then(res => setLessons(res.data));
  }, [subject]);

  return (
    <div className="faculty-card">
      <button onClick={goBack}>⬅ Back</button>
      <h3>{subject.name} – Units</h3>

      <div className="faculty-grid">
        {lessons.map(lesson => (
          <div key={lesson.id} className="faculty-item">
            <FaListAlt className="icon" />
            <h4>{lesson.name}</h4>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultyLesson;
