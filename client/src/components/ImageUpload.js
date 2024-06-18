import React, { useState } from 'react';
import axios from 'axios';

function ImageUpload() {
    const [file, setImage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post('http://localhost:8081/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            alert('Image uploaded successfully');
        } catch (error) {
            alert('Image upload failed');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Upload Image</h2>
            <input
                type="file"
                onChange={(e) => setImage(e.target.files[0])}
                required
            />
            <button type="submit">Upload</button>
        </form>
    );
}

export default ImageUpload;
