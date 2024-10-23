import { useEffect, useState } from "react"
import "./Registration.css"


export const Registration = () => {

    var [login, setLogin] = useState('');
    var [password, setPassword]  = useState('');
    var [matchPwd, setMatchPwd] = useState('');

    const [validMatchPwd, setValidMatchPwd] = useState(true);

    function handleRegistration(){
        setValidMatchPwd(password === matchPwd);
        if(validMatchPwd && password != '' && password != null){
            const header = new Headers();
            header.append("Content-Type", "application/json");
            const requestOptions = {
                method: "POST",
                headers: header,
                body: JSON.stringify({
                    "name": login,
                    "password": password,
                    "role": "USER"
                })
            }

            const res = fetch("http://localhost:8080/auth/register", requestOptions)
            .then(res => res.text())
            .then(result => console.log(result))
            .catch((err) => console.log(err));
        }
        console.log(validMatchPwd);
    }

    // useEffect(() => {
    //     setValidMatchPwd(password === matchPwd);
    // }, [password, matchPwd]);

    return (
        <>
            <div className="reg-container">
                {!validMatchPwd ? (
                    <div>Hasła nie są takie same spróbuj ponownie!</div>
                ):(
                    <div></div>
                )}
                <div className="reg-box">
                    <h1>Rejestracja</h1>
                    <label htmlFor="">Login</label>
                    <input onChange={(e) => {setLogin(e.target.value)}} value={login || ''} type="text" />
                    <label htmlFor="">Hasło</label>
                    <input onChange={(e) => {setPassword(e.target.value)}} value={password || ''} type="text" />
                    <label htmlFor="">Powtórz Hasło</label>
                    <input onChange={(e) => {setMatchPwd(e.target.value)}} value={matchPwd || ''} type="text" />
                    <button onClick={() => handleRegistration()}>Register</button>
                </div>
            </div>
        </>
    )
}