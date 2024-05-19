import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ListProductsContainer = () => {
    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const token = localStorage.getItem('token');

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
                    Authorization: `Bearer ${token}`
                }
            });

            setProducts(response.data);
            setIsLoading(false);
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

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="container">
            <h2>Product List</h2>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1rem' }}>
                <button onClick={fetchData}>Refresh</button>
                <button onClick={goBack}>Go Back</button>
            </div>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        {product.name} - {product.price}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ListProductsContainer;
