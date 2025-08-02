import { useContext, useState } from "react";
import { Button, Form } from "react-bootstrap";
import cookie from 'react-cookies';
import { useNavigate, useSearchParams } from "react-router-dom";


import Apis, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import MySpinner from "./layout/mySpinner";



const Login = () => {
    const [,dispatch] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);

    const info = [ {
        "title": "Tên đăng nhập",
        "field": "username",
        "type": "text"
    }, {
        "title": "Mật khẩu",
        "field": "password",
        "type": "password"
    }];

    
    const [user, setUser] = useState({});
    const nav = useNavigate();
    const [q] = useSearchParams();

    const login = async (e) => {
        e.preventDefault();

        try {
            setLoading(true);
            let res = await Apis.post(endpoints['login'], {
                ...user
            });
            console.log("==> Toàn bộ res.data:", res.data);

            console.info(res.data);
            cookie.save('token', res.data.token,{ path: '/' });
   

            let u = await authApis().get(endpoints['profile']);
            console.info(u.data);
            dispatch({
                "type": "login",
                "payload": u.data
            });

            
        let next = q.get('next');
        nav(next ? next : "/profile");
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false)
        }
    }

    return (
        <>
            <h1 className="text-center text-success mt-2">ĐĂNG NHẬP</h1>
            <Form onSubmit={login}>
                {info.map(i => <Form.Group key={i.field} className="mb-3" controlId={i.field}>
                    <Form.Label>{i.title}</Form.Label>
                    <Form.Control required value={user[i.field]} onChange={e => setUser({...user, [i.field]: e.target.value})} type={i.type} placeholder={i.title} />
                </Form.Group>)}


              
                {loading ? <MySpinner/>:<Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                    <Button type="submit" variant="success">Đăng nhập</Button>
                </Form.Group>}
                
            </Form>
        </>
    );
}

export default Login;