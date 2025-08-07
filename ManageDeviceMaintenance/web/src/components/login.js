import { useContext, useState } from "react";
import { Button, Form } from "react-bootstrap";
import cookie from 'react-cookies';
import { useNavigate } from "react-router-dom";

import Apis, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import MySpinner from "./layout/mySpinner";

const Login = () => {
    const [, dispatch] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [user, setUser] = useState({});
    const nav = useNavigate();

    const info = [
        { title: "Tên đăng nhập", field: "username", type: "text" },
        { title: "Mật khẩu", field: "password", type: "password" }
    ];

    const login = async (e) => {
        e.preventDefault();
        setError(null);

        try {
            setLoading(true);

            let res = await Apis.post(endpoints['login'], user);
            cookie.save('token', res.data.token, { path: '/' });

            let u = await authApis().get(endpoints['profile']);
            const expectedRole = user.userRole;
            console.log("Vai trò từ người dùng chọn:", expectedRole);
            console.log("Vai trò từ API /profile:", u.data.userRole);
            console.log("So sánh:", expectedRole === u.data.user_role);
            if (expectedRole && u.data.userRole !== expectedRole) {
                setError("Thông tin đăng nhập không hợp lệ với vai trò đã chọn!");
                cookie.remove("token", { path: '/' });
                return;
            }

            dispatch({
                type: "login",
                payload: u.data
            });

            nav("/profile");
        } catch (ex) {
            console.error(ex);
            setError("Đăng nhập thất bại! Vui lòng kiểm tra lại.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h1 className="text-center text-success mt-2">ĐĂNG NHẬP</h1>
            <Form onSubmit={login}>
                {info.map(i => (
                    <Form.Group key={i.field} className="mb-3" controlId={i.field}>
                        <Form.Label>{i.title}</Form.Label>
                        <Form.Control
                            required
                            value={user[i.field] || ""}
                            onChange={e => setUser({ ...user, [i.field]: e.target.value })}
                            type={i.type}
                            placeholder={i.title}
                        />
                    </Form.Group>
                ))}

                <Form.Group className="mb-3" controlId="userRole">
                    <Form.Label>Vai trò đăng nhập</Form.Label>
                    <Form.Select
                        required
                        value={user.userRole || ""}
                        onChange={(e) => setUser({ ...user, userRole: e.target.value })}
                    >
                        <option value="">-- Chọn vai trò --</option>
                        <option value="CUSTOMER">Khách hàng</option>
                        <option value="EMPLOYEE">Nhân viên</option>

                    </Form.Select>
                </Form.Group>

                {error && <div className="alert alert-danger">{error}</div>}

                {loading ? <MySpinner /> :
                    <Form.Group className="mb-3">
                        <Button type="submit" variant="success">Đăng nhập</Button>
                    </Form.Group>
                }
            </Form>
        </>
    );
};

export default Login;
