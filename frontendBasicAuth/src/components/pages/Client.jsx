import { useEffect, useState } from 'react'
import './Client.css'
import { RxAvatar } from "react-icons/rx";

export const Client = () => {
    const [postList, setPostList] = useState(null);
    const [title, setTitle] = useState("");
    const [descrition, setDescription] = useState("");
    const [username, setUsername] = useState("");

    const userId = localStorage.getItem("userId");
    // let userName = "";

    const handleLogin = () => {
        try {
            const myHeaders = new Headers();
            myHeaders.append("Content-Type", "application/json");

            const raw = JSON.stringify({
                "title": title,
                "content": descrition,
                "userId": userId
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

        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const credentials = localStorage.getItem('credentials');

                if (!credentials) {
                    console.error("No credentials found");
                    return;
                }

                const myHeaders = new Headers();
                myHeaders.append("Authorization", "Basic " + credentials);

                setUsername(atob(credentials).split(':')[0]);

                const requestOptions = {
                method: "GET",
                headers: myHeaders,
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
                <div className="form-content content-section">
                    <span>Nick: {username}</span>
                    <label htmlFor="title">Tytuł:</label>
                    <input 
                        id="title" 
                        type="text" 
                        value={title} 
                        onChange={(e) => setTitle(e.target.value)} 
                        className="input-field"
                    />
                    <label htmlFor="description">Opis:</label>
                    <textarea 
                        id="description" 
                        rows="4" 
                        cols="50" 
                        value={descrition} 
                        onChange={(e) => setDescription(e.target.value)} 
                        className="input-field"
                    ></textarea>
                    <button className="client-btn" onClick={() => handleLogin()}>Dodaj</button>
                </div>
            </div>

            <div className="container container-full-screen">
                <h1>Wprowadzone dane</h1>
                {
                    Array.isArray(postList) ? postList.map((item, iteration) => (
                        <div key={iteration} className="content-section">
                            <div className="post-header">
                                <div className='post-user'>
                                    <RxAvatar className="avatar-icon" />
                                    <span className="user-name">{item.userName}</span>
                                </div>
                                <div className='post-title-box'>
                                    <span className="post-title">{item.title}</span>
                                </div>
                            </div>

                            <p className="post-content">{item.content}</p>
                        </div>
                    )) : <p>No posts available</p>
                }
            </div>
        </>
    );
}