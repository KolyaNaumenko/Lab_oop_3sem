import React, {useEffect, useState} from 'react';
import {jwtDecode} from "jwt-decode";
import '../UserPage.css';

function UserProfile() {
    const [userData, setUserData] = useState({
        name: '',
        email: '',
        password: ''
    });

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            const decoded = jwtDecode(token);
            const {id} = decoded;

            fetch(`http://localhost:2525/api/user?action=get&id=${id}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {
                    setUserData({
                        name: data.name,
                        email: data.email,
                        password: data.password
                    });
                })
                .catch(error => console.error('Error:', error));
        }
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Saving Data:', userData);

        const token = localStorage.getItem('jwtToken');
        if (token) {
            const decoded = jwtDecode(token);
            const {id} = decoded;
            const {role} = decoded;
            const {money} = decoded;

            fetch(`http://localhost:2525/api/user?action=update&id=${id}&role=${role}&money=${money}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userData)
            })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Something went wrong');
                    }
                })
                .then(data => {
                    console.log('Success:', data.message);
                    alert('Profile updated successfully!');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to update profile!');
                });
        }
    };


    return (
        <div className="user-profile">
            <h2>Edit Profile</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input type="text" name="name" value={userData.name} onChange={handleInputChange}/>
                </div>
                <div>
                    <label>Email:</label>
                    <input type="email" name="email" value={userData.email} onChange={handleInputChange}/>
                </div>
                <div>
                    <label>Password:</label>
                    <input type="text" name="password" value={userData.password} onChange={handleInputChange}/>
                </div>
                <button type="submit">Save Changes</button>
            </form>
        </div>
    );
}

export default UserProfile;
