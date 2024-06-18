import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import RegisterForm from './components/RegisterForm';
import ImageUpload from './components/ImageUpload';
import ImageFetch from './components/ImageFetch';
import './App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/register" element={<RegisterForm />} />
                    <Route path="/upload" element={<ImageUpload />} />
                    <Route path="/images" element={<ImageFetch />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
