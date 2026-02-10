import React, { useEffect, useState } from "react";
import api from "../../services/api";
import {
  FaUserGraduate,
  FaEnvelope,
  FaPhoneAlt,
  FaIdCard,
  FaTrash,
  FaEdit,
  FaPlus,
  FaArrowLeft
} from "react-icons/fa";
import "../../styles/admin.css";

const emptyForm = {
  name: "",
  email: "",
  phone: "",
  regNo: "",
  password: ""
};

const AdminStudents = () => {
  const [departments, setDepartments] = useState([]);
  const [semesters, setSemesters] = useState([]);
  const [students, setStudents] = useState([]);

  const [selectedDept, setSelectedDept] = useState(null);
  const [selectedSem, setSelectedSem] = useState(null);

  const [editStudent, setEditStudent] = useState(null);
  const [form, setForm] = useState(emptyForm);

  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const pageSize = 5;

  /* ---------------- LOAD DEPARTMENTS ---------------- */
  useEffect(() => {
    api.get("/admin/departments")
      .then(res => setDepartments(res.data));
  }, []);

  /* ---------------- LOAD SEMESTERS ---------------- */
  const loadSemesters = async (dept) => {
    setSelectedDept(dept);
    setSelectedSem(null);
    setStudents([]);
    const res = await api.get(`/admin/semesters/${dept.id}`);
    setSemesters(res.data);
  };

  /* ---------------- LOAD STUDENTS ---------------- */
  const loadStudents = async (sem) => {
    setSelectedSem(sem);
    setPage(1);

    const res = await api.get(
      `/admin/students/${selectedDept.id}/${sem.id}`
    );

    const sorted = res.data.sort((a, b) =>
      a.name.localeCompare(b.name)
    );

    setStudents(sorted);
  };

  /* ---------------- SEARCH + PAGINATION ---------------- */
  const filteredStudents = students.filter(s =>
    s.name.toLowerCase().includes(search.toLowerCase()) ||
    (s.regNo || "").toLowerCase().includes(search.toLowerCase())
  );

  const start = (page - 1) * pageSize;
  const paginatedStudents =
    filteredStudents.slice(start, start + pageSize);

  /* ---------------- ADD STUDENT ---------------- */
  const addStudent = async () => {
    await api.post("/admin/users", null, {
      params: {
        ...form,
        role: "STUDENT",
        departmentId: selectedDept.id,
        semesterId: selectedSem.id
      }
    });

    setEditStudent(null);
    setForm(emptyForm);
    loadStudents(selectedSem);
  };

  /* ---------------- UPDATE STUDENT ---------------- */
  const updateStudent = async () => {
    await api.put(`/admin/students/${editStudent.id}`, null, {
      params: {
        name: form.name,
        email: form.email,
        phone: form.phone,
        regNo: form.regNo
      }
    });

    setEditStudent(null);
    setForm(emptyForm);
    loadStudents(selectedSem);
  };

  /* ---------------- DELETE STUDENT ---------------- */
  const deleteStudent = async (id) => {
    if (!window.confirm("Delete this student?")) return;
    await api.delete(`/admin/students/${id}`);
    loadStudents(selectedSem);
  };

  return (
    <div className="admin-page">

      <h2>🎓 Student Records</h2>

      {/* ---------- DEPARTMENTS ---------- */}
      {!selectedDept && (
        <div className="admin-grid">
          {departments.map(d => (
            <div
              key={d.id}
              className="admin-card clickable"
              onClick={() => loadSemesters(d)}
            >
              🏫 {d.name}
            </div>
          ))}
        </div>
      )}

      {/* ---------- SEMESTERS ---------- */}
      {selectedDept && !selectedSem && (
        <>
          <button className="back-btn" onClick={() => setSelectedDept(null)}>
            <FaArrowLeft /> Back
          </button>

          <h3>{selectedDept.name} – Select Year</h3>

          <div className="admin-grid">
            {semesters.map(s => (
              <div
                key={s.id}
                className="admin-card clickable"
                onClick={() => loadStudents(s)}
              >
                📘 Year {s.number}
              </div>
            ))}
          </div>
        </>
      )}

      {/* ---------- STUDENTS TABLE ---------- */}
      {selectedDept && selectedSem && (
        <>
          <button className="back-btn" onClick={() => setSelectedSem(null)}>
            <FaArrowLeft /> Back
          </button>

          <h3>
            {selectedDept.name} – Year {selectedSem.number}
          </h3>

          <p style={{ fontWeight: 600 }}>
            Total Students: {filteredStudents.length}
          </p>

          <input
            className="search-box"
            placeholder="Search by name or reg no..."
            value={search}
            onChange={e => setSearch(e.target.value)}
          />

          <button
            className="primary-btn"
            onClick={() => {
              setForm(emptyForm);
              setEditStudent({});
            }}
          >
            <FaPlus /> Add Student
          </button>

          <table className="admin-table">
            <thead>
              <tr>
                <th>Reg No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {paginatedStudents.map(s => (
                <tr key={s.id}>
                  <td><FaIdCard /> {s.regNo}</td>
                  <td><FaUserGraduate /> {s.name}</td>
                  <td><FaEnvelope /> {s.email}</td>
                  <td><FaPhoneAlt /> {s.phone}</td>
                  <td className="action-cell">
                    <button
                      className="edit-btn"
                      onClick={() => {
                        setEditStudent(s);
                        setForm({
                          name: s.name,
                          email: s.email,
                          phone: s.phone,
                          regNo: s.regNo,
                          password: ""
                        });
                      }}
                    >
                      <FaEdit />
                    </button>

                    <button
                      className="danger-btn"
                      onClick={() => deleteStudent(s.id)}
                    >
                      <FaTrash />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* ---------- PAGINATION ---------- */}
          <div className="pagination">
            <button
              disabled={page === 1}
              onClick={() => setPage(page - 1)}
            >
              Prev
            </button>

            <span>Page {page}</span>

            <button
              disabled={start + pageSize >= filteredStudents.length}
              onClick={() => setPage(page + 1)}
            >
              Next
            </button>
          </div>
        </>
      )}

      {/* ---------- ADD / EDIT MODAL ---------- */}
      {editStudent && (
        <div className="modal">
          <div className="modal-card">
            <h3>{editStudent.id ? "Edit Student" : "Add Student"}</h3>

            <input
              placeholder="Register Number"
              value={form.regNo}
              onChange={e => setForm({ ...form, regNo: e.target.value })}
            />
            <input
              placeholder="Name"
              value={form.name}
              onChange={e => setForm({ ...form, name: e.target.value })}
            />
            <input
              placeholder="Email"
              value={form.email}
              onChange={e => setForm({ ...form, email: e.target.value })}
            />
            <input
              placeholder="Phone"
              value={form.phone}
              onChange={e => setForm({ ...form, phone: e.target.value })}
            />

            {!editStudent.id && (
              <input
                type="password"
                placeholder="Password"
                value={form.password}
                onChange={e => setForm({ ...form, password: e.target.value })}
              />
            )}

            <div className="modal-actions">
              <button
                className="primary-btn"
                onClick={editStudent.id ? updateStudent : addStudent}
              >
                Save
              </button>

              <button
                className="secondary-btn"
                onClick={() => {
                  setEditStudent(null);
                  setForm(emptyForm);
                }}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

    </div>
  );
};

export default AdminStudents;
