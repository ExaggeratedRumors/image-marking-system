import React, { useState } from 'react';
import axios from 'axios';

function ImageUpload() {
    const [name, setName] = useState('');
    const [image, setImage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('name', name);
        formData.append('image', image);

        try {
            const response = await axios.post('/api/upload', formData, {
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
                type="text"
                placeholder="Image Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
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
