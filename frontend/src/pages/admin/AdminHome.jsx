import React, { useEffect, useState } from "react";
import api from "../../services/api";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer
} from "recharts";

const AdminHome = () => {
  const [stats, setStats] = useState({});
  const [studentAccess, setStudentAccess] = useState([]);
  const [facultyAccess, setFacultyAccess] = useState([]);

  useEffect(() => {
    // ---- SUMMARY ----
    api.get("/admin/analytics")
      .then(res => setStats(res.data))
      .catch(console.error);

    // ---- STUDENT ACCESS (DEPT WISE) ----
    api.get("/admin/analytics/access/student-department")
      .then(res => {
        const data = res.data.map(d => ({
          department: d[0],
          count: d[1]
        }));
        setStudentAccess(data);
      })
      .catch(console.error);

    // ---- FACULTY ACCESS (DEPT WISE) ----
    api.get("/admin/analytics/access/faculty-department")
      .then(res => {
        const data = res.data.map(d => ({
          department: d[0],
          count: d[1]
        }));
        setFacultyAccess(data);
      })
      .catch(console.error);

  }, []);

  return (
    <div>
      <h2>📊 Admin Dashboard</h2>

      {/* ===== SUMMARY CARDS ===== */}
      <div className="admin-cards">
        <div className="card">👨‍🎓 Students<br /><b>{stats.students}</b></div>
        <div className="card">👩‍🏫 Faculty<br /><b>{stats.faculty}</b></div>
        <div className="card">📁 Files<br /><b>{stats.files}</b></div>
        <div className="card">🚨 Suspicious<br /><b>{stats.suspicious}</b></div>
      </div>

      {/* ===== STUDENT ACCESS GRAPH ===== */}
      <h3 style={{ marginTop: "40px" }}>
        📈 Student Access – Department Wise
      </h3>

      {studentAccess.length === 0 ? (
        <p style={{ color: "#888" }}>
          No student access data available
        </p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <div style={{ minWidth: studentAccess.length * 120 }}>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={studentAccess}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="department" />
                <YAxis />
                <Tooltip />
                <Line
                  type="monotone"
                  dataKey="count"
                  stroke="#16a34a"
                  strokeWidth={3}
                  dot={{ r: 5 }}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      )}

      {/* ===== FACULTY ACCESS GRAPH ===== */}
      <h3 style={{ marginTop: "40px" }}>
        📈 Faculty Access – Department Wise
      </h3>

      {facultyAccess.length === 0 ? (
        <p style={{ color: "#888" }}>
          No faculty access data available
        </p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <div style={{ minWidth: facultyAccess.length * 120 }}>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={facultyAccess}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="department" />
                <YAxis />
                <Tooltip />
                <Line
                  type="monotone"
                  dataKey="count"
                  stroke="#2563eb"
                  strokeWidth={3}
                  dot={{ r: 5 }}
                />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminHome;
