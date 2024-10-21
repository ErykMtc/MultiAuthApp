import { useEffect, useState } from 'react'
import './Client.css'
import { RxAvatar } from "react-icons/rx";

import useAuth from '../../hooks/useAuth';
import useAxiosPrivate from '../../hooks/useAxiosPrivate'

// import axios from 'axios';

export const Client = () => {
    const [postList, setPostList] = useState(null);
    const [title, setTitle] = useState("");
    const [descrition, setDescription] = useState("");

    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();

    const handleLogin = () => {
        try {
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

        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosPrivate.get("/posts/");
                setPostList(response.data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
        console.log(auth?.accessToken);
        fetchData();
    }, [axiosPrivate]);

    if (postList == null) return <p>Loading...</p>;

    return (
        <>
            <div className="container container-full-screen">
                <h1>Wprowadź dane</h1>
                <div className="form-content content-section">
                    <span>Nick: {auth?.username}</span>
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