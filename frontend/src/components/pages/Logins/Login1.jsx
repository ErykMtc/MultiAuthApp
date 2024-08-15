import { useState } from 'react';
import './Login.css';

export const Login1 = () => {

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

        fetch("http://localhost:8080/auth/", requestOptions)
            .then((response) => response.text())
            .then((result) => {
                console.log(result); 
                if(result === "Invalid credentials"){
                    setLoginProblem(result);
                } else{
                    setLoginProblem("");
                }
            })
            .catch((error) => console.error(error));
    }

    console.log(password, username);

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
