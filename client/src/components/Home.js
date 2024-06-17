import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

function Home() {
    return (
        <div className="home">
            <h1>Welcome to the Image-Marker-System App</h1>
            <nav>
                <ul>
                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/register">Register</Link></li>
                    <li><Link to="/upload">Upload Image</Link></li>
                    <li><Link to="/images">Fetch Image</Link></li>
                </ul>
            </nav>
        </div>
    );
}

export default Home;