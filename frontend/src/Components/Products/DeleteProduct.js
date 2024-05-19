import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const DeleteProductContainer = () => {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [productId, setProductId] = useState('');
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        setProductId(e.target.value);
    };

    useEffect(() => {
        setError('');
        setSuccessMessage('');
    }, [productId]);

    const deleteProduct = async () => {
        const token = localStorage.getItem('token');

        if (!token) {
            setError('No token found. Please log in.');
            setIsLoading(false);
            return;
        }

        if (!productId) {
            setError('Please enter a product ID.');
            return;
        }

        try {
            setIsLoading(true);
            const response = await axios.delete(
                `http://localhost:8080/products/${productId}`,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            setIsLoading(false);
            setSuccessMessage('Product was deleted!');
            setProductId('');
            setError(null);
        } catch (err) {
            setIsLoading(false);
            if (err.response && err.response.status === 404) {
                setError('Product not found. Please check the product ID.');
            } else if (err.response && err.response.status === 403) {
                setError('You do not have the required authorization to delete this product.');
            } else {
                setError('An error occurred while deleting the product. Please try again.');
            }
            setIsLoading(false);
        }
    };

    const goBack = () => {
        navigate('/products');
    };

    return (
        <div className="container">
            <h2>Delete Product</h2>
            {error && <div className="error-message">{error}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}
            <input
                type="number"
                value={productId}
                onChange={handleInputChange}
                placeholder="Enter product ID"
            />
            <button onClick={deleteProduct}>Delete Product</button>
            <button onClick={goBack}>Go Back</button>
        </div>
    );
};

export default DeleteProductContainer;
