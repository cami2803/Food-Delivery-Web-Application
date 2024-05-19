import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CreateProductContainer = () => {
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState('');
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const navigate = useNavigate();

    const handleNameChange = (e) => {
        setName(e.target.value);
    };

    const handlePriceChange = (e) => {
        setPrice(e.target.value);
    };

    const createProduct = async () => {
        const token = localStorage.getItem('token');

        if (!token) {
            setError('No token found');
            setIsLoading(false);
            return;
        }

        if (!name || !price) {
            setError('Please fill in all fields.');
            return;
        }

        try {
            setIsLoading(true);
            const response = await axios.post(
                'http://localhost:8080/products/insert',
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
            setSuccessMessage('Product was created successfully!');
            setName('');
            setPrice('');
            navigate('/createProduct');
        } catch (err) {
            setError(err.message);
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
            <h2>Create Product</h2>
            {error && <div>Error: {error}</div>}
            {successMessage && <div>{successMessage}</div>}
            <input
                type="text"
                value={name}
                onChange={handleNameChange}
                placeholder="Enter product name"
            />
            <input
                type="number"
                value={price}
                onChange={handlePriceChange}
                placeholder="Enter product price"
            />
            <button onClick={createProduct}>Create Product</button>
            <button onClick={goBack}>Go Back</button>
        </div>
    );
};

export default CreateProductContainer;
