import React, { useEffect, useState } from "react";
import api from "../../services/api";
import {
  FaUserTie, FaEnvelope, FaPhoneAlt,
  FaTrash, FaEdit, FaPlus, FaArrowLeft
} from "react-icons/fa";
import "../../styles/admin.css";

const AdminFaculty = () => {
  const [departments, setDepartments] = useState([]);
  const [faculty, setFaculty] = useState([]);
  const [selectedDept, setSelectedDept] = useState(null);
  const [editFaculty, setEditFaculty] = useState(null);
  const emptyForm = {
  name: "",
  email: "",
  phone: "",
  password: ""
};

  const [form, setForm] = useState({
    name: "", email: "", phone: "", password: ""
  });

  useEffect(() => {
    api.get("/admin/departments")
      .then(res => setDepartments(res.data));
  }, []);

  const loadFaculty = (dept) => {
    setSelectedDept(dept);
    api.get(`/admin/users/faculty/by-department/${dept.id}`)
      .then(res => setFaculty(res.data));
      
  };

  const addFaculty = async () => {
     if (!form.password) {
    alert("Password is required");
    return;
  }
    await api.post("/admin/users", null, {
      params: {
        ...form,
        role: "FACULTY",
        departmentId: selectedDept.id
      }
    });
    setForm({ name: "", email: "", phone: "", password: "" });
    loadFaculty(selectedDept);
  };

  const updateFaculty = async () => {
    await api.put(`/admin/users/${editFaculty.id}`, null, {
      params: {
        name: form.name,
        email: form.email,
        phone: form.phone
      }
    });
    setEditFaculty(null);
    loadFaculty(selectedDept);
  };

  const deleteFaculty = async (id) => {
    if (!window.confirm("Delete faculty?")) return;
    await api.delete(`/admin/users/${id}`);
    loadFaculty(selectedDept);
  };

  return (
    <div className="admin-page">

      <h2>👩‍🏫 Faculty Records</h2>

      {/* DEPARTMENTS */}
      {!selectedDept && (
        <div className="admin-grid">
          {departments.map(d => (
            <div
              key={d.id}
              className="admin-card clickable"
              onClick={() => loadFaculty(d)}
            >
              {d.name}
            </div>
          ))}
        </div>
      )}

      {/* FACULTY LIST */}
      {selectedDept && (
        <>
          <button className="back-btn" onClick={() => setSelectedDept(null)}>
            <FaArrowLeft /> Back
          </button>

          <h3>{selectedDept.name} – Faculty</h3>

        <button
  className="primary-btn"
  onClick={() => {
    setForm(emptyForm);     // 🔥 RESET FORM
    setEditFaculty({});    // open modal in ADD mode
  }}
>
  <FaPlus /> Add Faculty
</button>


          <div className="admin-grid">
            {faculty.map(f => (
              <div key={f.id} className="admin-card hover-card">
                <h3><FaUserTie /> {f.name}</h3>
                <p><FaEnvelope /> {f.email}</p>
                <p><FaPhoneAlt /> {f.phone || "N/A"}</p>

                <div className="card-actions">
                  <button
                    className="edit-btn"
                    onClick={() => {
                      setEditFaculty(f);
                      setForm(emptyForm);
                    }}
                  >
                    <FaEdit /> Edit
                  </button>

                  <button
                    className="danger-btn"
                    onClick={() => deleteFaculty(f.id)}
                  >
                    <FaTrash /> Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        </>
      )}

      {/* ADD / EDIT MODAL */}
      {editFaculty && (
        <div className="modal">
          <div className="modal-card">
            <h3>{editFaculty.id ? "Edit Faculty" : "Add Faculty"}</h3>

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

            {!editFaculty.id && (
              <input
                placeholder="Password"
                type="password"
                value={form.password}
                required
                onChange={e => setForm({ ...form, password: e.target.value })}
              />
            )}

            <div className="modal-actions">
              <button className="primary-btn"
                onClick={editFaculty.id ? updateFaculty : addFaculty}>
                Save
              </button>
              <button
                className="secondary-btn"
                onClick={() => setEditFaculty(null)}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}

    </div>
  );
};

export default AdminFaculty;
