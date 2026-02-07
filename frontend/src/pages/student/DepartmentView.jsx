import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/dashboard.css";
import SubjectView from "./SubjectView";

const DepartmentView = ({ department, goBack }) => {
  const [semesters, setSemesters] = useState([]);
  const [selectedSemester, setSelectedSemester] = useState(null);

  useEffect(() => {
    api
      .get(`/admin/semesters/${department.id}`)
      .then((res) => setSemesters(res.data))
      .catch((err) => console.error(err));
  }, [department]);

  if (selectedSemester) {
    return (
      <SubjectView
        department={department}
        semester={selectedSemester}
        goBack={() => setSelectedSemester(null)}
      />
    );
  }

  return (
    <div className="dashboard">
      <button onClick={goBack}>⬅ Back</button>

      <div className="welcome-card">
        <h2>🏫 {department.name}</h2>
        <p>Select your semester ✨</p>
      </div>

      <div className="cards">
        {semesters.map((sem) => (
          <div
            key={sem.id}
            className="card"
            onClick={() => setSelectedSemester(sem)}
          >
            <div className="emoji">📘</div>
            <h3>Semester {sem.number}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DepartmentView;
