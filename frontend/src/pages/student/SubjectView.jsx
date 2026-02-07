import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/dashboard.css";
import LessonView from "./LessonView";

const SubjectView = ({ department, semester, goBack }) => {
  const [subjects, setSubjects] = useState([]);
  const [selectedSubject, setSelectedSubject] = useState(null);

  useEffect(() => {
    api
      .get(`/admin/subjects/${department.id}/${semester.id}`)
      .then((res) => setSubjects(res.data))
      .catch((err) => console.error(err));
  }, [department, semester]);

  if (selectedSubject) {
    return (
      <LessonView
        subject={selectedSubject}
        goBack={() => setSelectedSubject(null)}
      />
    );
  }

  return (
    <div className="dashboard">
      <button onClick={goBack}>⬅ Back</button>

      <div className="welcome-card">
        <h2>
          📘 {department.code} – Semester {semester.number}
        </h2>
        <p>Select a subject ✨</p>
      </div>

      <div className="cards">
        {subjects.map((sub) => (
          <div
            key={sub.id}
            className="card"
            onClick={() => setSelectedSubject(sub)}
          >
            <div className="emoji">📚</div>
            <h3>{sub.name}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default SubjectView;
