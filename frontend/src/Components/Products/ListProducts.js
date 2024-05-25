import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ListProductsContainer = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const [selectedProducts, setSelectedProducts] = useState(() => {
    const savedCart = localStorage.getItem('selectedProducts');
    return savedCart ? JSON.parse(savedCart) : [];
  });

  const token = localStorage.getItem('token');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    if (!token) {
      setError('No token found');
      setIsLoading(false);
      return;
    }

    fetchData();
  }, [token]);

  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/products/list', {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      setProducts(response.data);
      setIsLoading(false);
    } catch (err) {
      setError(err.message);
      setIsLoading(false);
    }
  };

  const deleteProduct = async (productId) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await axios.delete(`http://localhost:8080/products/delete/${productId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const updatedProducts = products.filter((product) => product.id !== productId);
        setProducts(updatedProducts);
      } catch (err) {
        setError(err.message);
      }
    }
  };

  const [updatingProduct, setUpdatingProduct] = useState(false);
  const [productIdToUpdate, setProductIdToUpdate] = useState(null);
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [successMessage, setSuccessMessage] = useState(null);

  const handleUpdateClick = (product) => {
    setProductIdToUpdate(product.id);
    setName(product.name);
    setPrice(product.price);
    setSuccessMessage(null);
    setUpdatingProduct(true);
  };

  const handleCancelUpdate = () => {
    setProductIdToUpdate(null);
    setName('');
    setPrice('');
    setUpdatingProduct(false);
  };

  const addToCart = (productId) => {
    const existingProduct = selectedProducts.find(p => p.productId === productId);
    if (existingProduct) {
      existingProduct.quantity++;
    } else {
      setSelectedProducts(prevSelectedProducts => {
        const updatedProducts = [...prevSelectedProducts, { productId, quantity: 1 }];
        localStorage.setItem('selectedProducts', JSON.stringify(updatedProducts));
        return updatedProducts;
      });
    }
    console.log('Product added to cart!');
  };

  const updateProduct = async () => {
    if (!productIdToUpdate || !name || !price) {
      setError('Please fill in all fields.');
      return;
    }

    try {
      setIsLoading(true);
      const response = await axios.put(
        `http://localhost:8080/products/update/${productIdToUpdate}`,
        {
          name,
          price,
        },
        {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setIsLoading(false);
      setSuccessMessage('Product was updated!');

      const updatedProductIndex = products.findIndex((p) => p.id === productIdToUpdate);
      const updatedProducts = [...products];
      updatedProducts[updatedProductIndex] = { ...response.data };
      setProducts(updatedProducts);

      setProductIdToUpdate(null);
      setName('');
      setPrice('');
      setUpdatingProduct(false);
    } catch (err) {
      setIsLoading(false);
      if (err.response && err.response.status === 404) {
        setError('Product not found. Please check the product ID.');
      } else if (err.response && err.response.status === 403) {
        setError('You do not have the required authorization to update this product.');
      } else {
        setError('An error occurred while updating the product. Please try again.');
      }
    }
  };

  const goBack = () => {
    navigate('/products');
  };

  const viewOrders = () => {
    localStorage.setItem('selectedProducts', JSON.stringify(selectedProducts));
    navigate('/orders', { state: { selectedProducts } });
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value.toLowerCase()); 
  };

  const filteredProducts = products.filter((product) =>
    product.name.toLowerCase().includes(searchTerm)
  );

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="container" style={{ overflowY: 'scroll', height: '600px' }}>
      <h2>Product List</h2>
      <div style={{ display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' }}>
      <input
          type="text"
          placeholder="Search Products"
          style={{ marginRight: '0.5rem' }}
          value={searchTerm}
          onChange={handleSearchChange}
        />
        <button onClick={fetchData} style={{ marginRight: '0.5rem' }}>Refresh</button>
        <button onClick={goBack} style={{ marginRight: '0.5rem' }}>Go Back</button>
        <button onClick={viewOrders}>View Orders</button>
      </div>
      <ul style={{ display: 'flex', flexWrap: 'wrap', listStyle: 'none', padding: 0 }}>
      {filteredProducts.map((product) => (
          <li key={product.id} style={{ width: '50%', padding: '0.5rem 1rem', margin: '0.5rem', border: '1px solid #ddd' }}>
            {product.name} - {product.price}
            <button onClick={() => deleteProduct(product.id)}>Delete</button>
            <button onClick={() => addToCart(product.id)}>Add to Cart</button>
            {updatingProduct && productIdToUpdate === product.id ? (
              <div>
                <input
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Enter product name"
                />
                <input
                  type="number"
                  value={price}
                  onChange={(e) => setPrice(e.target.value)}
                  placeholder="Enter product price"
                />
                <button onClick={updateProduct}>Save Update</button>
                <button onClick={handleCancelUpdate}>Cancel</button>
              </div>
            ) : (
              <button onClick={() => handleUpdateClick(product)}>Update</button>
            )}
          </li>
        ))}
      </ul>
      {successMessage && <div className="success-message">{successMessage}</div>}
    </div>
  );
};

export default ListProductsContainer;
