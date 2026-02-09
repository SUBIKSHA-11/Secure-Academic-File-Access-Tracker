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

  // ⬇️ DOWNLOAD FILE (JWT + blob)
  const downloadFile = async (file) => {
    try {
      const res = await api.get(
        `/files/download/${file.id}`,
        { responseType: "blob" }
      );

      const url = window.URL.createObjectURL(
        new Blob([res.data])
      );

      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", file.originalFileName);

      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      console.error("Download failed", err);
    }
  };

  // 👁️ VIEW FILE (PDF preview)
  const viewFile = async (file) => {
    try {
      const res = await api.get(
        `/files/download/${file.id}`,
        { responseType: "blob" }
      );

      const blob = new Blob(
        [res.data],
        { type: "application/pdf" } // 🔥 IMPORTANT
      );

      const fileURL = window.URL.createObjectURL(blob);
      window.open(fileURL, "_blank");
    } catch (err) {
      console.error("View failed", err);
    }
  };

  return (
    <div className="dashboard">
      <button className="stu-back-btn" onClick={goBack}>
  ⬅ Back
</button>


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

            <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
              <button
                onClick={() => viewFile(file)}
                style={{
                  padding: "8px",
                  borderRadius: "10px",
                  border: "none",
                  cursor: "pointer",
                  background: "#22c55e",
                  fontWeight: "bold",
                }}
              >
                👁️ View
              </button>

              <button
                onClick={() => downloadFile(file)}
                style={{
                  padding: "8px",
                  borderRadius: "10px",
                  border: "none",
                  cursor: "pointer",
                  background: "#22c55e",
                  fontWeight: "bold",
                }}
              >
                ⬇️ Download
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FilesView;
