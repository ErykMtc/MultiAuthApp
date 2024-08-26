import { useEffect, useState } from 'react'
import { FaTrashCan } from "react-icons/fa6";
import { RxAvatar } from "react-icons/rx";
import "./Admin.css"

export const Admin = () => {
    const [userList, setUserList] = useState(null);


    const handleRoleChange = async (e) => {
        const requestOptions = {
            method: "PUT",
            redirect: "follow"
        };

        var newRole = e.target.value;

        const response = await fetch("http://localhost:8080/users/role/52?role=" + newRole, requestOptions)
        const result = await response.json();
    }

    const handleDelete = async (id) => {
        const raw = "";

        const requestOptions = {
            method: "DELETE",
            body: raw,
            redirect: "follow"
        };

        const response = await fetch("http://localhost:8080/users/" + id, requestOptions)
        const result = await response.json();
    }

    useEffect(() => {

        const fetchData = async () => {
            try {
                const requestOptions = {
                    method: "GET",
                    redirect: "follow"
                };

                const response = await fetch("http://localhost:8080/users/", requestOptions);
                const result = await response.json();
                setUserList(result);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);

    if (userList == null) return <p>Loading...</p>;

    return (
        <>
            <div className="admin-container">
                <h1>Zarządzaj użytkownikami</h1>
                <div className="admin-user-list">
                    {Array.isArray(userList) ? userList.map((item, iteration) => (
                        <div key={iteration} className="admin-user-item">
                            <div className="admin-user-info">
                                <span className="admin-user-index">{iteration + 1}.</span>
                                <RxAvatar className="admin-avatar-icon" />
                                <span className="admin-user-name">{item.name}</span>
                            </div>
                            <div className="admin-user-actions">
                                <select className="admin-role-select" value={item.role} onChange={(e) => handleRoleChange(e)}>
                                    <option value="USER">User</option>
                                    <option value="ADMIN">Admin</option>
                                </select>
                                <FaTrashCan className="admin-delete-icon" onClick={() => handleDelete(item.id)} />
                            </div>
                        </div>
                    )) : <p>No users available</p>}
                </div>
            </div>
        </>
    );    
}