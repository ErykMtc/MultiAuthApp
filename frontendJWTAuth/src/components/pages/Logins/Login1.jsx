import { useState } from 'react';
import './Login.css';

import useAuth from "../../../hooks/useAuth";
import axios from 'axios';

export const Login1 = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginProblem, setLoginProblem] = useState('');

    const { setAuth } = useAuth();

    const handleLogin = async () => {
    
        try {
            const response = await axios.post("/auth/login", 
                JSON.stringify({
                    "login": username,
                    "pwd": password
                }), 
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );

              const accessToken = response?.data?.token;
              const role = response?.data?.role;
              const userid = response?.data?.id;

              setAuth({ username, password, role, accessToken, userid });

              setPassword("");
              setUsername("");

              


        } catch (error) {
            console.error(error);
        }
    }

    return (
        <>
            <div className='login-container'>
                <div className="login-box">
                    <h1>Logowanie</h1>
                    <span>{loginProblem}</span>
                    <label htmlFor="username">Login</label>
                    <input onChange={(e) => setUsername(e.target.value)} value={username} name="username" type="text" placeholder="Username" />
                    <label htmlFor="password">Hasło</label>
                    <input onChange={(e) => setPassword(e.target.value)} value={password} name="password" type="password" placeholder="Password" />
                    <button onClick={handleLogin}>Login</button>
                </div>
            </div>
        </>
    );
}
