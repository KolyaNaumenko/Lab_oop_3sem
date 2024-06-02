import React, { useEffect, useState } from 'react';
import '../UserPage.css';
import {jwtDecode} from 'jwt-decode';

function MyBets() {
    const [bets, setBets] = useState([]);
    const [betTypes, setBetTypes] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            const decoded = jwtDecode(token);
            const {id} = decoded;

            fetch(`http://localhost:2525/api/bet?action=list&userId=${id}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {setBets(data)})
                .catch(error => console.error('Error fetching bets:', error));

            fetch(`http://localhost:2525/api/bet-type?action=list`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => response.json())
                .then(data => {setBetTypes(data)
                console.log(data)})
                .catch(error => console.error('Error fetching bets:', error));
        }
    }, []);

    return (
        <div className="bets-container">
            <h1>My Bets</h1>
            <table className="bets-table">
                <thead>
                <tr>
                    <th>Bet ID</th>
                    <th>Bet Type ID</th>
                    <th>Amount</th>
                    <th>Status</th>
                    <th>Potential Win</th>
                </tr>
                </thead>
                <tbody>
                {bets.map(bet => (
                    <tr key={bet.betId}>
                        <td>{bet.betId}</td>
                        <td>{bet.betTypeId}</td>
                        <td>${bet.amount.toFixed(2)}</td>
                        <td>{bet.status}</td>
                        <td>${bet.potential_win.toFixed(2)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <h1>Bet Types</h1>
            <table className="bets-table">
                <thead>
                <tr>
                    <th>Bet Type ID</th>
                    <th>Race ID</th>
                    <th>Horse Id</th>
                    <th>Odd</th>
                    <th>Is First</th>
                </tr>
                </thead>
                <tbody>
                {betTypes.map(betType => (
                    <tr key={betType.betTypeId}>
                        <td>{betType.betTypeId}</td>
                        <td>{betType.raceId}</td>
                        <td>{betType.horseId}</td>
                        <td>${betType.odd.toFixed(2)}</td>
                        <td>{betType.is_first.toString()}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>

    );
}

export default MyBets;
