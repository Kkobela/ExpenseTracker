import { useState } from "react";
import axios from "axios";

export default function AuthForm({ type }) {
  const [formData, setFormData] = useState({ username: "", email: "", password: "" });

  const handleChange = (e) => {
    setFormData((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const endpoint = type === "login" ? "/api/auth/login" : "/api/auth/register";
    try {
      const response = await axios.post(`http://localhost:8080${endpoint}`, formData);
      console.log("Sikeres válasz:", response.data);
      // TODO: token mentése, átirányítás
    } catch (error) {
      console.error("Hiba:", error.response?.data || error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>{type === "login" ? "Bejelentkezés" : "Regisztráció"}</h2>
      <input name="username" placeholder="Felhasználónév" onChange={handleChange} required />
      {type === "signup" && (
        <input name="email" placeholder="Email" onChange={handleChange} required />
      )}
      <input name="password" type="password" placeholder="Jelszó" onChange={handleChange} required />
      <button type="submit">{type === "login" ? "Bejelentkezés" : "Regisztráció"}</button>
    </form>
  );
}
