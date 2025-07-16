import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();

  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
      const data = await response.json();
      console.log('Backend válasz:', data);
      if (data.token && data.user) {
        login(data.token, data.user);
        navigate('/profile');
      } else {
        alert('Hibás válasz formátum!');
      }
    } else {
      alert('Hibás bejelentkezés!');
    }
  } catch (error) {
    console.error('Hálózati hiba:', error);
    alert('Hálózati hiba!');
  }
};

  return (
    <form onSubmit={handleSubmit}>
      <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Felhasználónév" />
      <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Jelszó" />
      <button type="submit">Bejelentkezés</button>
    </form>
  );
}
