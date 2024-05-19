import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Styles/styles1.css';

function HomePage() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/');
  };

  const handleProducts = () => {
    navigate('/products');
  }

  const handleOrders = () => {
    navigate('/orders');
  }

  return (
    <div className="container">
      <h2>Welcome to the Home Page!</h2>
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
                <button onClick={handleLogout}>Logout</button>
      </div>
      <button onClick={handleProducts}>Products</button>
      <button onClick={handleOrders}>Orders</button>
    </div>
  );
}

export default HomePage;
