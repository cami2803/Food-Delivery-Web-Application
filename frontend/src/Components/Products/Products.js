import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ProductsContainer = () => {
    const navigate = useNavigate();

    const fetchData = () => {
        const token = localStorage.getItem('token');
        if (token !== "") {
          navigate('/listProducts');
        } else {
            console.log("Token is null");
        }
    };

    const createData = () => {
        const token = localStorage.getItem('token'); 
        if (token !== "") {
          navigate('/createProduct');
        } else {
            console.log("Token is null");
        }
    };

    const handleLogout = () => {
      localStorage.removeItem('token');
      navigate('/');
    };

    const handleGoToHomePage = () => {
      navigate('/home');
    };

    return (
      <div className="container">
            <h2>Welcome to the Products Page!</h2>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
              <button onClick={handleGoToHomePage} style={{ marginRight: '0.5rem' }}>Go To HomePage</button>
              <button onClick={handleLogout}>Logout</button>
            </div>
            <button onClick={fetchData}>List</button>
            <button onClick={createData}>Create</button>
      </div>
    );
};

export default ProductsContainer;
