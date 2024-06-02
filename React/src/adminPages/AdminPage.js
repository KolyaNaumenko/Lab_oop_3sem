import React, { useState } from 'react';
import '../UserPage.css';

function AdminPage() {
    const [raceId, setRaceId] = useState('');
    const [statusMessage, setStatusMessage] = useState('');
    const [newResult, setNewResult] = useState({
        raceId: '',
        horseId: '',
        position: ''
    });
    const [resultMessage, setResultMessage] = useState('');

    const handleStatusChange = (newStatus) => {
        const token = localStorage.getItem('jwtToken');
        fetch(`http://localhost:2525/api/race?action=update&race_id=${raceId}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status: newStatus })
        })
            .then(response => response.json())
            .then(data => setStatusMessage(data.message))
            .catch(error => {
                console.error('Failed to update race status:', error);
                setStatusMessage('Failed to update race status');
            });
    };

    const handleAddResult = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('jwtToken');
        fetch('http://localhost:2525/api/result?action=add', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newResult)
        })
            .then(response => response.json())
            .then(data => {
                setResultMessage('Result added successfully!');
                setNewResult({ raceId: '', horseId: '', position: '' });
            })
            .catch(error => {
                console.error('Failed to add result:', error);
                setResultMessage('Failed to add result');
            });
    };

    return (
        <div className="user-profile">
            <div>
                <h2>Enter Race ID:</h2>
                <input
                    id="text"
                    type="text"
                    value={raceId}
                    onChange={e => setRaceId(e.target.value)}
                />
                <button type="submit" onClick={() => handleStatusChange('active')}>Set Active</button>
                <button type="submit" onClick={() => handleStatusChange('end')}>Set End</button>
            </div>
            {statusMessage && <p>{statusMessage}</p>}

            <h2>Add New Result</h2>
            <form onSubmit={handleAddResult}>
                <input
                    type="text"
                    placeholder="Race ID"
                    value={newResult.raceId}
                    onChange={e => setNewResult({...newResult, raceId: e.target.value})}
                />
                <input
                    type="text"
                    placeholder="Horse ID"
                    value={newResult.horseId}
                    onChange={e => setNewResult({...newResult, horseId: e.target.value})}
                />
                <input
                    type="text"
                    placeholder="Position"
                    value={newResult.position}
                    onChange={e => setNewResult({...newResult, position: e.target.value})}
                />
                <button type="submit">Add Result</button>
            </form>
            {resultMessage && <p>{resultMessage}</p>}
        </div>
    );
}

export default AdminPage;
