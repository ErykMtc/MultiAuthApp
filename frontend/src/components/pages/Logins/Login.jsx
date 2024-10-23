import { useState } from 'react';
import './Login.css';
import Cookies from 'js-cookie';
import ReCAPTCHA from "react-google-recaptcha";

export const Login = () => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loginProblem, setLoginProblem] = useState('');
    const [val, setVal] = useState(null);

    function onChange(value) {
        setVal(value);
    }
      

    const handleLogin = async () => {
        if(!val){
            return;
        }

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
    .then((response) => response.json()) // Parse the response as JSON
    .then((result) => {
        // Now result is an object and you can access its properties
        if(result === "Invalid credentials"){
            setLoginProblem(result);
        } else {
            Cookies.set('AuthApp', JSON.stringify({login: result.login, pwd: result.pwd, role: result.role, userId: result.userId}));
            window.location.replace("http://localhost:5173/");
        }
    })
    .catch((error) => {});
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
                    <ReCAPTCHA
                    className='captcha'
                    sitekey="6LfhxTYqAAAAACSQvkLMNSstz5iuyDFS_yLmWtcH"
                    onChange={onChange}
                    />
                    <button disabled={!val} onClick={handleLogin}>Login</button>
                </div>
            </div>
        </>
    );
}
