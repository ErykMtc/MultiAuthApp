import { useEffect, useState } from 'react';
import { FaTrashCan } from "react-icons/fa6";
import { RxAvatar } from "react-icons/rx";
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import "./Admin.css";

export const Admin = () => {
    const [userList, setUserList] = useState(null);
    const axiosPrivate = useAxiosPrivate();

    const handleRoleChange = async (e, userId) => {
        const newRole = e.target.value;

        try {
            const response = await axiosPrivate.put(`/users/role/${userId}?role=${newRole}`);
            console.log("Role updated:", response.data);
        } catch (error) {
            console.error("Error updating role:", error);
        }
    }

    const handleDelete = async (id) => {
        try {
            await axiosPrivate.delete(`/users/${id}`);
            setUserList(prevUserList => prevUserList.filter(user => user.id !== id));
            console.log("User deleted:", id);
        } catch (error) {
            console.error("Error deleting user:", error);
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axiosPrivate.get("/users/");
                setUserList(response.data);
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        };

        fetchData();
    }, [axiosPrivate]);

    if (userList == null) return <p>Loading...</p>;

    return (
        <>
            <div className="admin-container">
                <h1>Zarządzaj użytkownikami</h1>
                <div className="admin-user-list">
                    {Array.isArray(userList) ? userList.map((item, iteration) => (
                        <div key={item.id} className="admin-user-item">
                            <div className="admin-user-info">
                                <span className="admin-user-index">{iteration + 1}.</span>
                                <RxAvatar className="admin-avatar-icon" />
                                <span className="admin-user-name">{item.name}</span>
                            </div>
                            <div className="admin-user-actions">
                                <select 
                                    className="admin-role-select" 
                                    value={item.role} 
                                    onChange={(e) => handleRoleChange(e, item.id)}
                                >
                                    <option value="USER">User</option>
                                    <option value="ADMIN">Admin</option>
                                </select>
                                <FaTrashCan 
                                    className="admin-delete-icon" 
                                    onClick={() => handleDelete(item.id)} 
                                />
                            </div>
                        </div>
                    )) : <p>No users available</p>}
                </div>
            </div>
        </>
    );
}
