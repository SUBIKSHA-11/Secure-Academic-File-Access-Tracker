import React, { useState } from "react";
import AdminSidebar from "./AdminSidebar";
import AdminHome from "./AdminHome";
import AdminFaculty from "./AdminFaculty";
import AdminStudents from "./AdminStudents";
import AdminAcademics from "./AdminAcademics";
import AdminFiles from "./AdminFiles";
import AdminLogs from "./AdminLogs";
import AdminAddUser from "./AdminAddUser";
import "../../styles/admin.css";

const AdminDashboard = () => {
  const [page, setPage] = useState("home");

  const renderPage = () => {
    switch (page) {
      case "faculty": return <AdminFaculty />;
      case "students": return <AdminStudents />;
      case "academics": return <AdminAcademics />;
      case "files": return <AdminFiles />;
      case "logs": return <AdminLogs />;
      case "addUser": return <AdminAddUser />;
      default: return <AdminHome />;
    }
  };

  return (
    <div className="admin-layout">
      <AdminSidebar setPage={setPage} />
      <div className="admin-content">
        {renderPage()}
      </div>
    </div>
  );
};

export default AdminDashboard;
