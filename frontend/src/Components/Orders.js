import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';

function OrdersPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const selectedProducts = location.state?.selectedProducts || JSON.parse(localStorage.getItem('selectedProducts')) || [];
  const [customerid] = useState(uuidv4());

  useEffect(() => {
    fetchProducts();
  }, [selectedProducts]);

  const fetchProducts = async () => {
    const token = localStorage.getItem('token');
    const productDetails = [];

    for (const { productId, quantity } of selectedProducts) {
      try {
        const response = await axios.get(`http://localhost:8080/products/getId/${productId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        productDetails.push({ ...response.data, quantity });
      } catch (err) {
        console.error('Error fetching product details:', err);
      }
    }
    setProducts(productDetails);
  };

  const handleQuantityChange = (productId, newQuantity) => {
    if (newQuantity < 1) {
      const confirmRemove = window.confirm('Are you sure you want to remove this product from your order?');
      if (!confirmRemove) return;

      const updatedProducts = products.filter((product) => product.id !== productId);
      setProducts(updatedProducts);

      const updatedSelectedProducts = selectedProducts.filter((item) => item.productId !== productId);
      localStorage.setItem('selectedProducts', JSON.stringify(updatedSelectedProducts));
      return;
    }

    const updatedProducts = products.map((product) => {
      if (product.id === productId) {
        return { ...product, quantity: newQuantity };
      }
      return product;
    });
    setProducts(updatedProducts);

    const updatedSelectedProducts = selectedProducts.map((item) => {
      if (item.productId === productId) {
        return { ...item, quantity: newQuantity };
      }
      return item;
    });

    localStorage.setItem('selectedProducts', JSON.stringify(updatedSelectedProducts));
  };

  const calculateTotalPrice = (product) => {
    return product.price * product.quantity;
  };

  const calculateTotalOrderPrice = () => {
    return products.reduce((total, product) => total + calculateTotalPrice(product), 0);
  };

  const getRandomInt = (min, max) => {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  };

  const handleOrderSubmit = async () => {

    const confirmOrder = window.confirm('Are you sure you want to order these products?');
    if (!confirmOrder) return;

    const total = calculateTotalOrderPrice();

    const orderDTO = {
      customerid,
      total
    };

    const token = localStorage.getItem('token');
    try {
      const response = await axios.post('http://localhost:8080/orders/insert', orderDTO, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
      },
      });
      if (response.status === 201) {
        alert('Order placed successfully!');
        localStorage.removeItem('selectedProducts');
        navigate('/orders');
      }
    } catch (error) {
      console.error('Error placing order:', error);
      alert('Failed to place order. Please try again.');
    }
  };

  return (
    <div className="container">
      <h2>Welcome to the Orders Page!</h2>
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
        <button onClick={() => navigate('/home')} style={{ marginRight: '0.5rem' }}>
          Go To HomePage
        </button>
        <button onClick={() => navigate('/listProducts')} style={{ marginRight: '0.5rem' }}>
          Go Back
        </button>
        <button onClick={() => navigate('/')} style={{ marginRight: '0.5rem' }}>
          Logout
        </button>
      </div>
      {products.length > 0 && (
        <div>
          <h3>Current Order</h3>
          <ul>
            {products.map((product) => (
              <li key={product.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <span>{product.name}</span>
                <span>{calculateTotalPrice(product)} LEI</span>
                <span style={{ display: 'flex', alignItems: 'center' }}>
                  <button onClick={() => handleQuantityChange(product.id, product.quantity - 1)} style={{ padding: '5px 10px', fontSize: '10px', marginRight: '2px' }}>
                    -
                  </button>
                  <span style={{ padding: '10px', fontSize: '15px' }}>{product.quantity}</span>
                  <button onClick={() => handleQuantityChange(product.id, product.quantity + 1)} style={{ padding: '5px 10px', fontSize: '10px', marginLeft: '2px' }}>
                    +
                  </button>
                </span>
              </li>
            ))}
          </ul>
          <div style={{ textAlign: 'right', marginTop: '1rem' }}>
            <strong>Total: {calculateTotalOrderPrice()} LEI</strong>
          </div>
          <div style={{ textAlign: 'right', marginTop: '1rem' }}>
            <button onClick={handleOrderSubmit} style={{ padding: '10px 20px', fontSize: '15px' }}>Order Products</button>
          </div>
        </div>
      )}
      {selectedProducts.length > 0 && products.length === 0 && (
        <div>Failed to fetch some product details.</div>
      )}
      {selectedProducts.length === 0 && <div>No current orders found.</div>}
    </div>
  );
}

export default OrdersPage;
