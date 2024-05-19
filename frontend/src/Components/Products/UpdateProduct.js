import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const UpdateProductContainer = () => {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [productId, setProductId] = useState('');
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const navigate = useNavigate();

    const handleInputChange1 = (e) => {
        setProductId(e.target.value);
    };

    const handleInputChange2 = (e) => {
        setName(e.target.value);
    };

    const handleInputChange3 = (e) => {
        setPrice(e.target.value);
    };

    const updateProduct = async () => {
        const token = localStorage.getItem('token');
        if (!token) {
            setError('No token found. Please log in.');
            setIsLoading(false);
            return;
        }

        if (!productId || !name || !price) {
            setError('Please fill in all fields.');
            return;
        }

        try {
            setIsLoading(true);
            const response = await axios.put(
                `http://localhost:8080/products/${productId}`,
                {
                    name: name,
                    price: price,
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
            setProductId('');
            setName('');
            setPrice('');
            setError(null);
        } catch (err) {
            if (err.response && err.response.status === 404) {
                setError('Product not found. Please check the product ID.');
            } else if (err.response && err.response.status === 403) {
                setError('You do not have the required authorization to update this product.');
            } else {
                setError('An error occurred while updating the product. Please try again.');
            }
            setIsLoading(false);
        }
    };

    const goBack = () => {
        navigate('/products');
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container">
            <h2>Update Product</h2>
            {error && <div className="error-message">{error}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}
            <input
                type="number"
                value={productId}
                onChange={handleInputChange1}
                placeholder="Enter product ID"
            />
            <input
                type="text"
                value={name}
                onChange={handleInputChange2}
                placeholder="Enter product name"
            />
            <input
                type="number"
                value={price}
                onChange={handleInputChange3}
                placeholder="Enter product price"
            />
            <button onClick={updateProduct}>Update Product</button>
            <button onClick={goBack}>Go Back</button>
        </div>
    );
};

export default UpdateProductContainer;
