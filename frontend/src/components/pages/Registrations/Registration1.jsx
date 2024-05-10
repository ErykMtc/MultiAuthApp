import "./Registration.css"


export const Registration1 = () => {
    return (
        <>
            <div className="reg-container">
                <div className="reg-box">
                    <h1>Rejestracja</h1>
                    <label htmlFor="">Login</label>
                    <input type="text" />
                    <label htmlFor="">Hasło</label>
                    <input type="text" />
                    <label htmlFor="">Powtórz Hasło</label>
                    <input type="text" />
                    <button>Register</button>
                </div>
            </div>
        </>
    )
}