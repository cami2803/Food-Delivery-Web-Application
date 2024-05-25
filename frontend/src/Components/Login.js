import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
//import './Styles/styles1.css';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/authenticate', {
                email,
                password
            });
            const data = response.data;
            const token = data.token;
            
            localStorage.setItem('token', token);
            setError('');
            navigate('/home');
        } catch (error) {
            setError('Invalid email or password');
        }
    };

    const handleRegister = () => {
        navigate('/register');
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="border rounded-lg p-4" style={{ width: '500px', height: 'auto' }}>
                <div className="container">
                    <h2 className="mb-4 text-center">Login Page</h2>
                    {error && <p className="text-danger">{error}</p>}
                    <input className="mb-4 form-control" id='email' placeholder='Email address' value={email} type='email' onChange={(e) => setEmail(e.target.value)} />
                    <input className="mb-4 form-control" placeholder='Password' id='password' type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                    <button className="mb-4 d-block btn btn-primary" style={{ height: '50px', width: '100%' }} onClick={handleLogin}>Sign in</button>
                    <div className="text-center">
                        <p>You do not have an account? <button className="btn btn-link" onClick={handleRegister}>Create account</button></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;
