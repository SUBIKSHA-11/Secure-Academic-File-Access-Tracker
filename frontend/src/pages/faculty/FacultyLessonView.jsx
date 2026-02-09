import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/faculty.css";
import {
  FaPuzzlePiece,
  FaUpload,
  FaTrash,
  FaEdit
} from "react-icons/fa";
import FacultyFilesView from "./FacultyFilesView";
import UploadFile from "./FacultyUploadFile";
import axios from "axios";
import EditLesson from "./EditLesson";

const FacultyLessonView = ({ subject, goBack }) => {

  const [lessons, setLessons] = useState([]);
  const [uploadLesson, setUploadLesson] = useState(null);
  const [viewFilesLesson, setViewFilesLesson] = useState(null);
  const [editLesson, setEditLesson] = useState(null);
  const [newName, setNewName] = useState("");
const [editLessonPage, setEditLessonPage] = useState(null);
const [showAddLesson, setShowAddLesson] = useState(false);
const [lessonName, setLessonName] = useState("");
const token = localStorage.getItem("token");


  // ---------- LOAD LESSONS ----------
  const loadLessons = () => {
    api.get(`/lessons/${subject.id}`)
      .then(res => setLessons(res.data))
      .catch(() => alert("Failed to load lessons"));
  };

  useEffect(() => {
    loadLessons();
  }, [subject]);

  // ---------- DELETE LESSON ----------
  const deleteLesson = async (lessonId) => {
    if (!window.confirm("Delete this lesson?")) return;

    try {
      await axios.delete(
        `http://localhost:8080/lessons/${lessonId}`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      loadLessons();
    } catch {
      alert("Delete failed");
    }
  };

  // ---------- UPDATE LESSON ----------
  const updateLesson = async () => {
    try {
      await axios.put(
        `http://localhost:8080/lessons/${editLesson.id}?name=${newName}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setEditLesson(null);
      loadLessons();
    } catch {
      alert("Update failed");
    }
  };

  // ---------- NAVIGATION ----------
  if (uploadLesson) {
    return (
      <UploadFile
        lesson={uploadLesson}
        goBack={() => setUploadLesson(null)}
      />
    );
  }

  if (viewFilesLesson) {
    return (
      <FacultyFilesView
        lesson={viewFilesLesson}
        goBack={() => setViewFilesLesson(null)}
      />
    );
  }
if (editLessonPage) {
  return (
    <EditLesson
      lesson={editLessonPage}
      goBack={() => setEditLessonPage(null)}
    />
  );
}
const addLesson = async () => {
  try {
    await axios.post(
  "http://localhost:8080/lessons/add",
  null,
  {
    params: {
      name: lessonName,
      subjectId: subject.id
    },
    headers: {
      Authorization: `Bearer ${token}`
    }
  }
);
    setShowAddLesson(false);
    setLessonName("");
    loadLessons();
  } catch {
    alert("Add unit failed");
  }
};

  // ---------- UI ----------
  return (
    <div className="faculty-bg">

      <button className="back-btn" onClick={goBack}>
        ← Back
      </button>

      <div className="faculty-card">
        <h2>{subject.name}</h2>
        <p>Manage units (lessons)</p>
      </div>
<button
  className="add-btn"
  onClick={() => setShowAddLesson(true)}
>
  + Add Unit
</button>
{showAddLesson && (
  <div className="faculty-card">
    <h3>Add Unit</h3>

    <input
      placeholder="Unit name"
      value={lessonName}
      onChange={(e) => setLessonName(e.target.value)}
    />

    <button onClick={addLesson}>Add</button>
  </div>
)}

      <div className="faculty-grid">
        {lessons.map((lesson) => (
          <div key={lesson.id} className="faculty-item">

            <FaPuzzlePiece className="icon" />
            <h3>{lesson.name}</h3>

            <div className="action-row">

              <button
                className="icon-btn"
                onClick={() => setUploadLesson(lesson)}
                title="Upload file"
              >
                <FaUpload />
              </button>

              <button
                className="icon-btn"
                onClick={() => setViewFilesLesson(lesson)}
                title="View files"
              >
                Files
              </button>


              <button
                className="icon-btn"
                onClick={() => deleteLesson(lesson.id)}
                title="Delete lesson"
              >
                <FaTrash />
              </button>
              <button
  className="icon-btn"
  onClick={() => setEditLessonPage(lesson)}
>
  <FaEdit />
</button>


            </div>
          </div>
        ))}
      </div>

      {/* ---------- EDIT MODAL ---------- */}

    </div>
  );
};

export default FacultyLessonView;
