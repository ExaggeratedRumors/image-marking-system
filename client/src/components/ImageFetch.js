import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ImageFetch() {
    const [images, setImages] = useState([]);

    useEffect(() => {
        const fetchImages = async () => {
            try {
                const response = await axios.get('/api/images');
                setImages(response.data);
            } catch (error) {
                console.error('Error fetching images', error);
            }
        };

        fetchImages();
    }, []);

    return (
        <div className="image-list">
            {images.map((image) => (
                <div key={image.id} className="image-item">
                    <h3>{image.name}</h3>
                    <img src={`data:image/jpeg;base64,${image.data}`} alt={image.name} />
                    <p>{new Date(image.date).toLocaleString()}</p>
                </div>
            ))}
        </div>
    );
}

export default ImageFetch;
