import React, { useEffect, useState } from "react";
import api from "../../services/api";
import UploadFile from "./FacultyUploadFile";
import { FaEdit, FaEye, FaStreetView } from "react-icons/fa";
import "../../styles/faculty.css";
import {
  FaFileAlt,
  FaDownload,
  FaTrash,
  FaArrowLeft
} from "react-icons/fa";
import axios from "axios";

const FacultyFilesView = ({ lesson, goBack }) => {
  const [files, setFiles] = useState([]);
  const token = localStorage.getItem("token");
const [editFile, setEditFile] = useState(null);

  const loadFiles = () => {
    api.get(`/files/lesson/${lesson.id}`)
      .then(res => setFiles(res.data))
      .catch(console.error);
  };

  useEffect(() => {
    loadFiles();
  }, [lesson]);

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
  } catch {
    alert("Download failed");
  }
};

  const deleteFile = async (id) => {
    if (!window.confirm("Delete this file?")) return;

    try {
      await axios.delete(
        `http://localhost:8080/files/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      loadFiles();
    } catch {
      alert("Delete failed");
    }
  };
  if (editFile) {
  return (
    <UploadFile
      lesson={lesson}
      existingFile={editFile}
      goBack={() => 
        { setEditFile(null);
          loadFiles();
        }}
    />
  );
}
const viewFile = async (file) => {
  try {
    const res = await api.get(
      `/files/download/${file.id}`,
      { responseType: "blob" }
    );

    const blob = new Blob(
      [res.data],
      { type: "application/pdf" }
    );

    const url = window.URL.createObjectURL(blob);
    window.open(url, "_blank");
  } catch {
    alert("View failed");
  }
};


  return (
    <div className="faculty-bg">
      <button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>{lesson.name}</h2>
        <p>Manage uploaded files</p>
      </div>

      <div className="faculty-grid">
        {files.map(file => (
          <div key={file.id} className="faculty-item">
            <FaFileAlt className="icon" />
            <h3>{file.originalFileName}</h3>

            <p
              style={{
                fontWeight: 600,
                color:
                  file.sensitivity === "HIGH"
                    ? "#c62828"
                    : "#2e7d32"
              }}
            >
              {file.sensitivity}
            </p>

            <div className="action-row">
  <button
    className="icon-btn"
    onClick={() => downloadFile(file)}
    title="Download"
  >
    <FaDownload />
  </button>

  <button
    className="icon-btn"
    onClick={() => deleteFile(file.id)}
    title="Delete"
  >
    <FaTrash />
  </button>
<button
  className="icon-btn"
  onClick={() => setEditFile(file)}
>
  <FaEdit />
</button>
<button
  className="icon-btn"
  onClick={() => viewFile(file)}
>
  <FaEye />
</button>
</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default FacultyFilesView;
