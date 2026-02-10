import React from "react";
import {
  FaChartBar,
  FaUsers,
  FaUserGraduate,
  FaUniversity,
  FaFileAlt,
  FaExclamationTriangle,
  FaUserPlus
} from "react-icons/fa";

const AdminSidebar = ({ setPage }) => {
  return (
    <div className="admin-sidebar">
      <h2>🎓 Admin Panel</h2>

      <button onClick={() => setPage("home")}><FaChartBar /> Dashboard</button>
      <button onClick={() => setPage("faculty")}><FaUsers /> Faculty</button>
      <button onClick={() => setPage("students")}><FaUserGraduate /> Students</button>
      <button onClick={() => setPage("academics")}><FaUniversity /> Academics</button>
      <button onClick={() => setPage("files")}><FaFileAlt /> Files</button>
      <button onClick={() => setPage("logs")}><FaExclamationTriangle /> Logs</button>
      <button onClick={() => setPage("addUser")}><FaUserPlus /> Add User</button>
    </div>
  );
};

export default AdminSidebar;
