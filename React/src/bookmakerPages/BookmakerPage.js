import React, { useState } from 'react';
import '../UserPage.css';

function BookmakerPage() {
    const [betId, setBetId] = useState('');
    const [newStatus, setNewStatus] = useState('');
    const [betTypeId, setBetTypeId] = useState('');
    const [newOdd, setNewOdd] = useState('');

    const handleStatusChange = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('jwtToken');

        fetch(`http://localhost:2525/api/bet?action=update&bet_id=${betId}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status: newStatus })
        })
            .then(response => {
                if (response.ok) {
                    alert('Bet status updated successfully!');
                    setBetId('');
                    setNewStatus('');
                } else {
                    alert('Failed to update bet status');
                }
            })
            .catch(error => alert('Error updating bet status:', error));
    };

    const handleOddChange = (event) => {
        event.preventDefault();
        const token = localStorage.getItem('jwtToken');
        fetch(`http://localhost:2525/api/bet-type?action=update&betType_id=${betTypeId}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ odd: newOdd })
        })
            .then(response => {
                if (response.ok) {
                    alert('Odd updated successfully!');
                    setBetTypeId('');
                    setNewOdd('');
                } else {
                    alert('Failed to update odd');
                }
            })
            .catch(error => alert('Error updating odd:', error));
    };

    return (
        <div className="user-profile">
            <form onSubmit={handleStatusChange}>
                <h2>Update Bet Status</h2>
                <input type="text" placeholder="Bet ID" value={betId} onChange={e => setBetId(e.target.value)} />
                <input type="text" placeholder="New Status" value={newStatus} onChange={e => setNewStatus(e.target.value)} />
                <button type="submit">Update Status</button>
            </form>

            <form onSubmit={handleOddChange}>
                <h2>Update Bet Type Odd</h2>
                <input type="text" placeholder="Bet Type ID" value={betTypeId} onChange={e => setBetTypeId(e.target.value)} />
                <input type="number" placeholder="New Odd" value={newOdd} onChange={e => setNewOdd(e.target.value)} />
                <button type="submit">Update Odd</button>
            </form>
        </div>
    );
}

export default BookmakerPage;
