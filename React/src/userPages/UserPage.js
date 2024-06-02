import React from 'react';
import '../UserPage.css';
import {jwtDecode} from "jwt-decode";
import UserProfile from "./UserProfile";
import MyBets from "./MyBets";
import AddBet from "./AddBet";

function UserPage() {
    const token = localStorage.getItem('jwtToken');
    const decodedToken = jwtDecode(token);
    const { money } = decodedToken;
    const { name } = decodedToken;

    const user = {
        name: name,
        balance: money
    };

    return (
        <div className="user-page">
            <header className="header">
                <div className="header-left">
                    HBet
                </div>
                <div className="header-right">
                    {user.name} - {user.balance}$
                </div>
            </header>
            <UserProfile/>
            <AddBet/>
            <MyBets/>
        </div>
    );
}

export default UserPage;
