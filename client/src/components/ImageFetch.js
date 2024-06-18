import React, { useState } from 'react';

function ImageFetch() {
    const [imageName, setImageName] = useState('');
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [image, setImage] = useState(null);
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        try {
            e.preventDefault();
            const response = await fetch("http://localhost:8081/fetch", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({imageName, login, password}),
            });
            const blob = await response.blob();
            const imageObjectURL = URL.createObjectURL(blob);
            setImage(imageObjectURL);
        } catch (err) {
            setError('Error fetching the image');
        }

    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <h2>Fetch image</h2>
                <input
                    type="text"
                    placeholder="ImageName.png"
                    value={imageName}
                    onChange={(e) => setImageName(e.target.value)}
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
                <button type="submit">Fetch</button>
            </form>
            <div className="image-list">
                <div className="image-item">
                    <h3>Fetched image</h3>
                    <div>
                        {error && <p>{error}</p>}
                        {image ? <img src={image} alt="Fetched"/> : <p>Here you will show image...</p>}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ImageFetch;
