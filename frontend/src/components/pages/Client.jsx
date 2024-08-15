import { useEffect, useState } from 'react'
import './Client.css'

export const Client = () => {
    const [postList, setPostList] = useState(null);
    const [title, setTitle] = useState("");
    const [descrition, setDescription] = useState("");

    const handleLogin = () =>{
        try{
            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");
            
            const raw = JSON.stringify({
                "title": title,
                "content": descrition,
                "userId": 1 
            });

            const requestOptions = {
                method: "POST",
                headers: myHeaders,
                body: raw,
                redirect: "follow"
            };

            fetch("http://localhost:8080/posts/add", requestOptions)
            .then((response) => response.text())
            .then((result) => console.log(result))

        } catch(error){
            console.error("Error fetching data:", error);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const requestOptions = {
                    method: "GET",
                    redirect: "follow"
                };

                const response = await fetch("http://localhost:8080/posts/", requestOptions);
                const result = await response.json();
                setPostList(result);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);

    if (postList == null) return <p>Loading...</p>;

    return (
        <>
            <div className="container container-full-screen">
                <h1>Wprowadź dane</h1>
                <div className='form-content'>
                    <span>Nick: Ala_ma_kota</span>
                    <label htmlFor="">Tytuł</label>
                    <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}/>
                    <label htmlFor="w3review">Opis:</label>
                    <textarea rows="4" cols="50" value={descrition} onChange={(e) => setDescription(e.target.value)}></textarea>
                </div>
                <button className='client-btn' onClick={() => handleLogin()}>Dodaj</button>
            </div>

            <div className="container container-full-screen">
                <h1>Wprowadzone dane</h1>
                {
                    Array.isArray(postList) ? postList.map((item, iteration) => (
                        <div key={iteration} className='content-section'>
                            <span>{item.userName}</span>
                            <span>{item.title}</span>
                            <p>{item.content}</p>
                        </div>
                    )) : <p>No posts available</p>
                }
            </div>
        </>
    );
}