import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { MyUserContext } from "../configs/Context";

const Profile = () => {
  const [user] = useContext(MyUserContext);

  if (!user)
    return <Navigate to="/login" replace />; // chưa login thì chuyển hướng đến login

  return (
    <div className="container mt-4">
      <h1 className="text-primary">Trang cá nhân</h1>

      {user.avatar ? (
        <img
          src={user.avatar}
          alt="avatar"
          width="120"
          height="120"
          className="mb-3 rounded-circle shadow"
        />
      ) : (
        <div
          className="mb-3 rounded-circle bg-secondary d-flex align-items-center justify-content-center"
          style={{ width: "120px", height: "120px", color: "white" }}
        >
          No Avatar
        </div>
      )}

      <ul className="list-group">
        <li className="list-group-item"><strong>Họ tên:</strong> {user.lastName} {user.firstName}</li>
        <li className="list-group-item"><strong>Username:</strong> {user.username}</li>
        <li className="list-group-item"><strong>Email:</strong> {user.email}</li>
        <li className="list-group-item"><strong>Phone:</strong> {user.phone}</li>
        <li className="list-group-item"><strong>Giới tính:</strong> {user.sex}</li>
        <li className="list-group-item"><strong>Vai trò:</strong> {user.userRole}</li>
        <li className="list-group-item">
          <strong>Ngày tham gia:</strong> {user.joinDate ? new Date(user.joinDate).toLocaleDateString() : ""}
        </li>
      </ul>
    </div>
  );
};

export default Profile;
