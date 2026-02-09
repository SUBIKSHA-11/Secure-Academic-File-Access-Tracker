import React, { useEffect, useState } from "react";
import axios from "axios";
import "../../styles/faculty.css";

const EditLesson = ({ lesson, goBack }) => {
  const [name, setName] = useState(lesson.name);
  const token = localStorage.getItem("token");

  const updateLesson = async () => {
    try {
      await axios.put(
        `http://localhost:8080/lessons/${lesson.id}?name=${name}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      goBack();
    } catch {
      alert("Update failed");
    }
  };

  return (
    <div className="faculty-bg">
      <button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>Edit Unit</h2>

        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <button onClick={updateLesson}>
          Save Changes
        </button>
      </div>
    </div>
  );
};

export default EditLesson;
