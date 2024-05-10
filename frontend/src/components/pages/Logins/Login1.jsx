import './Login.css'

export const Login1 = () => {
    return (
        <>
            <div className='login-container'>
                <div className="login-box">
                    <h1>Logowanie</h1>
                    <label htmlFor="">Login</label>
                    <input type="text" placeholder="Username" />
                    <label htmlFor="">Hasło</label>
                    <input type="text" placeholder="Password" />
                    <button>Login</button>
                </div>
            </div>
        </>
    )
}