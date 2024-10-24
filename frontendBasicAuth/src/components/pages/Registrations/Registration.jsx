import { useEffect, useState } from "react"
import "./Registration.css"

const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/;

export const Registration = () => {

    var [login, setLogin] = useState('');
    var [password, setPassword] = useState('');
    var [matchPwd, setMatchPwd] = useState('');

    const [validPwd, setValidPwd] = useState(false);
    const [validMatchPwd, setValidMatchPwd] = useState(true);
    const [pwdFocus, setPwdFocus] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);
    const [errMsg, setErrMsg] = useState("");

    useEffect(() => {
        const result = PWD_REGEX.test(password);
        setValidPwd(result);

        setValidMatchPwd(password === matchPwd);
    }, [password, matchPwd]);

    useEffect(() => {
        setErrMsg("");
    }, [password, matchPwd]);

    async function handleRegistration(){
        setValidMatchPwd(password === matchPwd);
        if(validMatchPwd && password != '' && password != null){
            const header = new Headers();
            header.append("Content-Type", "application/json");
            const requestOptions = {
                method: "POST",
                headers: header,
                body: JSON.stringify({
                    "login": login,
                    "pwd": password,
                    "role": "USER"
                })
            }

            try {
                const res = await fetch("http://localhost:8080/auth/register", requestOptions);
                const result = await res.text();
            } catch (err) {
                console.error("Error during fetch:", err);
            }

            window.location.replace("http://localhost:5173/")
        }
        
    }

    return (
        <>
            <div className="reg-container">
                {errMsg && <div className="errmsg" ref={errRef}>{errMsg}</div>}
                <div className="reg-box">
                    <h1>Rejestracja</h1>
                    <label htmlFor="login">Login</label>
                    <input onChange={(e) => { setLogin(e.target.value) }} value={login || ''} type="text" />
                    
                    <label htmlFor="password">Hasło</label>
                    <input
                        onChange={(e) => { setPassword(e.target.value) }}
                        value={password || ''}
                        type="password"
                        onFocus={() => setPwdFocus(true)}
                        onBlur={() => setPwdFocus(false)}
                        aria-invalid={validPwd ? "false" : "true"}
                    />
                    <p className="instructions">
                        Hasło musi zawierać 8-30 znaków, w tym przynajmniej jedną małą literę, jedną dużą literę, znak oraz jedną cyfrę.
                    </p>

                    <label htmlFor="matchPwd">Powtórz Hasło</label>
                    <input
                        onChange={(e) => { setMatchPwd(e.target.value) }}
                        value={matchPwd || ''}
                        type="password"
                        onFocus={() => setMatchFocus(true)}
                        onBlur={() => setMatchFocus(false)}
                        aria-invalid={validMatchPwd ? "false" : "true"}
                    />
                    <p className={matchFocus && !validMatchPwd ? "instructions" : "offscreen"}>
                        Hasła muszą być takie same.
                    </p>

                    <button onClick={handleRegistration}>Zarejestruj się</button>
                </div>
            </div>
        </>
    )
}