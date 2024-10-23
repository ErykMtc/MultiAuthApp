import { useState } from 'react';
import './Login.css';

export const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginProblem, setLoginProblem] = useState('');

    const handleLogin = async () => {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
    
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: JSON.stringify({
                "login": username,
                "pwd": password
            }),
            redirect: "follow"
        };
    
        try {
            const response = await fetch("http://localhost:8080/auth/login", requestOptions);
            const result = await response.json();
    
            if (result === "Invalid credentials") {
                setLoginProblem(result);
            } else {
                const base64Credentials = btoa(`${username}:${password}`);
                localStorage.setItem('userRole', result.role);
                localStorage.setItem('credentials', base64Credentials)
                localStorage.setItem('userId', result.userId)
                window.location.replace("http://localhost:5173/");
            }
        } catch (error) {}
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
