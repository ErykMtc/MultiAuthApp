import { useState } from 'react';
import './Login.css';
import useAuth from "../../../hooks/useAuth";
import { FaGoogle, FaGithub } from 'react-icons/fa';
import { getSocialLoginUrl } from '../OAuthUtils/OAuthHelper';
import axios from 'axios';
import { parseJwt } from '../OAuthUtils/OAuthHelper';

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
                  headers: { 'Content-Type': 'application/json' }
                }
            );

            const accessToken = response?.data;
            const dataRole = parseJwt(accessToken);
            const role = dataRole.rol[0];

            console.log(role)

            setAuth({ username, password, role, accessToken});

            setPassword("");
            setUsername("");

        } catch (error) {
            console.error(error);
        }
    }

    return (
        <div className='login-container'>
            <div className="login-box">
                <h1>Logowanie</h1>
                <span>{loginProblem}</span>

                <label htmlFor="username">Login</label>
                <input 
                    onChange={(e) => setUsername(e.target.value)} 
                    value={username} 
                    name="username" 
                    type="text" 
                    placeholder="Username" 
                />

                <label htmlFor="password">Hasło</label>
                <input 
                    onChange={(e) => setPassword(e.target.value)} 
                    value={password} 
                    name="password" 
                    type="password" 
                    placeholder="Password" 
                />

                <button onClick={handleLogin}>Login</button>

                <div className="oauth-container">
                    <a className="google-login" href={getSocialLoginUrl('google')}>
                        <FaGoogle style={{ marginRight: '8px' }} /> Login with Google
                    </a>
                    <a className="github-login" href={getSocialLoginUrl('github')}>
                        <FaGithub style={{ marginRight: '8px' }} /> Login with GitHub
                    </a>
                </div>
            </div>
        </div>
    );
}