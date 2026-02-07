import React, { useState } from "react";
import { FaUpload, FaBook } from "react-icons/fa";
import FacultyBrowse from "./FacultyBrowse";
import FacultyUpload from "./FacultyUpload";
import "../../styles/faculty.css";

const FacultyDashboard = () => {
  const [view, setView] = useState("browse");
  const email = localStorage.getItem("email");

  return (
    <div className="faculty-page">
      <div className="faculty-header">
        <h2>Welcome, {email}</h2>

        <div>
          <button className="nav-btn" onClick={() => setView("browse")}>
            <FaBook /> Browse Files
          </button>{" "}
          <button className="nav-btn" onClick={() => setView("upload")}>
            <FaUpload /> Upload File
          </button>
        </div>
      </div>

      {view === "browse" ? <FacultyBrowse /> : <FacultyUpload />}
    </div>
  );
};

export default FacultyDashboard;
