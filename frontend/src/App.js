import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './Components/Login';
import RegisterPage from './Components/Register';
import HomePage from './Components/Home';
import ProductsPage from './Components/Products/Products';
import ListProducts from './Components/Products/ListProducts';
import CreateProduct from './Components/Products/CreateProduct';
import OrdersPage from './Components/Orders';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/products" element={<ProductsPage />} />
        <Route path="/listProducts" element={<ListProducts />} />
        <Route path="/createProduct" element={<CreateProduct />} />
        <Route path="/orders" element={<OrdersPage />} />
      </Routes>
    </Router>
  );
}

export default App;
