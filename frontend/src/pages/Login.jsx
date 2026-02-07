import React, { useState } from "react";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import "../styles/login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const res = await axios.post(
        "http://localhost:8080/auth/login",
        { email, password }
      );

      const token = res.data;
      localStorage.setItem("token", token);

      const decoded = jwtDecode(token);
      localStorage.setItem("role", decoded.role);
      localStorage.setItem("email", decoded.sub);

      if (decoded.role === "STUDENT") window.location = "/student";
      else if (decoded.role === "FACULTY") window.location = "/faculty";
      else window.location = "/admin";

    } catch {
      setError("❌ Invalid login details");
    }
  };

  return (
    <div className="login-bg">
      <div className="login-card">
        <h1>🎓 Secure Academic Portal</h1>
        <p>Welcome back! Let’s continue learning 🚀</p>

        <form onSubmit={handleLogin}>
          <input
            type="email"
            placeholder="📧 Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />

          <input
            type="password"
            placeholder="🔑 Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          {error && <p className="error">{error}</p>}

          <button>Login ✨</button>
        </form>

        <div className="stickers">
          📚 🎓 ⭐
        </div>
      </div>
    </div>
  );
};

export default Login;
