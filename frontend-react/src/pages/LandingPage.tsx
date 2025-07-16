import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function LandingPage() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLoginClick = () => {
    navigate('/login');
  };

  const handleSignupClick = () => {
    navigate('/signup');
  };

  return (
    <div style={{ padding: '2rem', fontFamily: 'Arial, sans-serif' }}>
      <h1>Üdvözöllek az oldalon!</h1>
      
      {user ? (
        <>
          <p>Bejelentkezve mint: <strong>{user.username}</strong></p>
          <button onClick={logout} style={{ marginRight: '1rem' }}>Kijelentkezés</button>
        </>
      ) : (
        <>
          <p>Nem vagy bejelentkezve.</p>
          <button onClick={handleLoginClick} style={{ marginRight: '1rem' }}>Belépés</button>
          <button onClick={handleSignupClick}>Regisztráció</button>
        </>
      )}
    </div>
  );
}
