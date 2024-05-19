import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
//import './Styles/styles1.css';

function RegisterPage() {
    const [firstname, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [role, setRole] = useState('USER');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleRegister= async () => {
        try {
            if (!firstname || !lastname || !email || !password || !confirmPassword) {
                setError('Please fill in all fields.');
                return;
            }
            if (password !== confirmPassword) {
                throw new Error("Passwords do not match");
            }

            const response = await axios.post('http://localhost:8080/api/register', {
                firstname,
                lastname,
                email,
                password,
                role
            });
            console.log(response.data);
            navigate('/home');
        } catch (error) {
            console.error('Register failed:', error.response ? error.response.data : error.message);
            setError(error.response ? error.response.data : error.message);
        }
    };

    return (
        <div className="container">
            <div className="border rounded-lg p-4" style={{width: '500px', height: 'auto'}}>
                <div className="p-3">
                    <h2 className="mb-4 text-center">Register Page</h2>
                    {error && <p className="text-danger">{error}</p>}
                    <input className='mb-3 form-control' placeholder='First Name' value={firstname} type='text' onChange={(e) => setFirstName(e.target.value)} />
                    <input className='mb-3 form-control' placeholder='Last Name' value={lastname} type='text' onChange={(e) => setLastName(e.target.value)} />
                    <input className='mb-3 form-control' placeholder='Email Address' value={email} type='email' onChange={(e) => setEmail(e.target.value)} />
                    <input className='mb-3 form-control' placeholder='Password' value={password} type='password' onChange={(e) => setPassword(e.target.value)} />
                    <input className='mb-3 form-control' placeholder='Confirm Password' value={confirmPassword} type='password' onChange={(e) => setConfirmPassword(e.target.value)} />
                    <label className="form-label mb-1">Role:</label>
                    <select className="form-select mb-4" value={role} onChange={(e) => setRole(e.target.value)}>
                        <option value="USER">User</option>
                        <option value="ADMIN">Admin</option>
                    </select>
                    <button className="mb-4 d-block mx-auto fixed-action-btn btn-primary" style={{height: '40px', width: '100%'}} onClick={handleRegister}>Register</button>
                    <div className="text-center">
                        <p>Already have an account? <a href="/">Login</a></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default RegisterPage;
