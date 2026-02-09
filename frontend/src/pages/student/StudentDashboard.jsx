import React, { useEffect, useState } from "react";
import api from "../../services/api";
import "../../styles/dashboard.css";
import DepartmentView from "./DepartmentView";

const StudentDashboard = () => {
  const [departments, setDepartments] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);

  const email = localStorage.getItem("email");

  useEffect(() => {
    api.get("/departments")
      .then(res => setDepartments(res.data))
      .catch(err => console.error(err));
  }, []);

  if (selectedDept) {
    return (
      <DepartmentView
        department={selectedDept}
        goBack={() => setSelectedDept(null)}
      />
    );
  }

  return (
    <div className="dashboard">
      <div className="welcome-card">
        <h2>👋 Welcome, {email}</h2>
        <p>Select your department to continue 📚</p>
      </div>

      <div className="cards">
        {departments.map(dept => (
          <div
            key={dept.id}
            className="card"
            onClick={() => setSelectedDept(dept)}
          >
            <div className="emoji">🎓</div>
            <h3>{dept.name}</h3>
            <p>{dept.code}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default StudentDashboard;
