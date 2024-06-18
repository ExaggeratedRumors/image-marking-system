import React, { useState } from 'react';
import axios from 'axios';

function RegisterForm() {
    const [name, setUsername] = useState('');
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await fetch("http://localhost:8083/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, login, password }),
        });
        if (response.ok) {
            alert('Registration successful');
        } else {
            alert('Registration failed');
        }
    };

/*    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response =
                await axios.post('http://localhost:8083/register', { login, username, password });
            alert('Registration successful');
        } catch (error) {
            alert('Registration failed');
        }
    };*/

    return (
        <form onSubmit={handleSubmit}>
            <h2>Register</h2>
            <input
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
            <input
                type="text"
                placeholder="Login"
                value={login}
                onChange={(e) => setLogin(e.target.value)}
                required
            />
            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            <button type="submit">Register</button>
        </form>
    );
}

export default RegisterForm;
