import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/dashboard.css";
import FilesView from "./FilesView";

const LessonView = ({ subject, goBack }) => {
  const [lessons, setLessons] = useState([]);
  const [selectedLesson, setSelectedLesson] = useState(null);

  useEffect(() => {
      api.get(`/lessons/${subject.id}`)
      .then((res) => setLessons(res.data))
      .catch((err) => console.error(err));
  }, [subject]);

  if (selectedLesson) {
    return (
      <FilesView
        lesson={selectedLesson}
        goBack={() => setSelectedLesson(null)}
      />
    );
  }

  return (
    <div className="dashboard">
      <button className="stu-back-btn" onClick={goBack}>
  ⬅ Back
</button>


      <div className="welcome-card">
        <h2>📚 {subject.name}</h2>
        <p>Select a lesson / unit 👇</p>
      </div>

      <div className="cards">
        {lessons.map((lesson) => (
          <div
            key={lesson.id}
            className="card"
            onClick={() => setSelectedLesson(lesson)}
          >
            <div className="emoji">🧩</div>
            <h3>{lesson.name}</h3>
          </div>
        ))}
      </div>
    </div>
  );
  
};

export default LessonView;
