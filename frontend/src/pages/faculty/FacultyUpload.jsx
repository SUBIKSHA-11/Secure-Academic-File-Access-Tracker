import React, { useState } from "react";
import api from "../../services/api";
import { FaUpload } from "react-icons/fa";
import "../../styles/faculty.css";

const FacultyUpload = () => {
  const [file, setFile] = useState(null);
  const [lessonId, setLessonId] = useState("");
  const [sensitivity, setSensitivity] = useState("LOW");

  const upload = async () => {
    const form = new FormData();
    form.append("file", file);
    form.append("lessonId", lessonId);
    form.append("sensitivity", sensitivity);

    await api.post("/files/upload", form);
    alert("File uploaded successfully");
  };

  return (
    <div className="faculty-card">
      <h3><FaUpload /> Upload Academic File</h3>

      <input type="file" onChange={e => setFile(e.target.files[0])} />
      <input placeholder="Lesson ID" onChange={e => setLessonId(e.target.value)} />

      <select onChange={e => setSensitivity(e.target.value)}>
        <option value="LOW">LOW</option>
        <option value="HIGH">HIGH</option>
      </select>

      <br /><br />
      <button className="nav-btn" onClick={upload}>
        Upload
      </button>
    </div>
  );
};

export default FacultyUpload;
