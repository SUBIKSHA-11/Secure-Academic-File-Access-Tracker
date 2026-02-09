import React, { useState } from "react";
import axios from "axios";
import "../../styles/faculty.css";
import { FaUpload, FaArrowLeft } from "react-icons/fa";

const FacultyUploadFile = ({ lesson, existingFile, goBack }) => {

  const [file, setFile] = useState(null);
  const [sensitivity, setSensitivity] = useState(
    existingFile ? existingFile.sensitivity : "LOW"
  );
  const [msg, setMsg] = useState("");

  const token = localStorage.getItem("token");

  const handleUpload = async (e) => {
    e.preventDefault();
    setMsg("");

    if (!file) {
      setMsg("Please select a file");
      return;
    }

    try {
      // 🔥 IF EDIT MODE → delete old file first
      if (existingFile) {
        await axios.delete(
          `http://localhost:8080/files/${existingFile.id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
      }

      // 🔥 Upload new file
      const formData = new FormData();
      formData.append("file", file);
      formData.append("lessonId", lesson.id);
      formData.append("sensitivity", sensitivity);

      await axios.post(
        "http://localhost:8080/files/upload",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,

          },
        }
      );

      setMsg(existingFile ? "File updated successfully ✅" : "File uploaded successfully ✅");
      setFile(null);

    } catch (err) {
  console.error(err.response?.data);
  setMsg(err.response?.data || "Upload failed ❌");
}
  };

  return (
    <div className="faculty-bg">
<button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>
          {existingFile ? "Edit File" : "Upload File"}
        </h2>
        <p>
          Lesson: <b>{lesson.name}</b>
        </p>
      </div>

      <form className="faculty-card" onSubmit={handleUpload}>

  {existingFile && (
    <div className="file-info">
      <p><b>Current File:</b> {existingFile.originalFileName}</p>
      <p>
        <b>Sensitivity:</b>{" "}
        <span className={
          existingFile.sensitivity === "HIGH"
            ? "tag-high"
            : "tag-low"
        }>
          {existingFile.sensitivity}
        </span>
      </p>
    </div>
  )}

  <div className="upload-form vertical">

    <label className="file-label">
      📄 Choose New File
      <input
        type="file"
        onChange={(e) => setFile(e.target.files[0])}
        required={!existingFile}
      />
    </label>

    <label>
      🔐 Sensitivity
      <select
        value={sensitivity}
        onChange={(e) => setSensitivity(e.target.value)}
      >
        <option value="LOW">LOW</option>
        <option value="HIGH">HIGH</option>
      </select>
    </label>

    <button type="submit" className="upload-btn">
      <FaUpload />
      {existingFile ? " Update File" : " Upload File"}
    </button>

  </div>

  {msg && (
    <p className="status-msg">
      {msg}
    </p>
  )}

</form>

    </div>
  );
};

export default FacultyUploadFile;
