import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/dashboard.css";

const FilesView = ({ lesson, goBack }) => {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    api
      .get(`/files/lesson/${lesson.id}`)
      .then((res) => setFiles(res.data))
      .catch((err) => console.error(err));
  }, [lesson]);

  const downloadFile = (fileId) => {
    window.open(
      `http://localhost:8080/files/download/${fileId}`,
      "_blank"
    );
  };

  return (
    <div className="dashboard">
      <button onClick={goBack}>⬅ Back</button>

      <div className="welcome-card">
        <h2>📂 {lesson.name}</h2>
        <p>Available academic files ⬇️</p>
      </div>

      <div className="cards">
        {files.map((file) => (
          <div key={file.id} className="card">
            <div className="emoji">📄</div>

            <h3>{file.originalFileName}</h3>

            <p
              style={{
                color:
                  file.sensitivity === "HIGH"
                    ? "#f87171"
                    : "#4ade80",
                fontWeight: "bold",
              }}
            >
              🔐 {file.sensitivity}
            </p>

            <button
              style={{
                marginTop: "10px",
                padding: "10px",
                borderRadius: "10px",
                border: "none",
                cursor: "pointer",
                background:
                  "linear-gradient(90deg,#14b8a6,#f59e0b)",
                color: "#020617",
                fontWeight: "bold",
              }}
              onClick={() => downloadFile(file.id)}
            >
              ⬇️ Download
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FilesView;
