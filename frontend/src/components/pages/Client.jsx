import './Client.css'

export const Client = () => {
    return (
        <>
            <div className="container container-full-screen">
                <h1>Wprowadź dane</h1>
                <div className='form-content'>
                    <span>Nick: Ala_ma_kota</span>
                    <label for="w3review">Opis:</label>
                    <textarea rows="4" cols="50"></textarea>
                </div>
                <button className='client-btn'>Dodaj</button>
            </div>

            <div className="container container-full-screen">
                <h1>Wprowadzone dane</h1>
                <div className='content-section'>
                    <span>Nick: Arek</span>
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus repudiandae, ducimus aliquam porro alias quis ab atque error enim labore sed facere nisi soluta vel obcaecati reprehenderit impedit corrupti vero.</p>
                </div>
            </div>
        </>
    )
}