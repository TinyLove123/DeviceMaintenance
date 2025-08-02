import Nav from 'react-bootstrap/Nav';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';


const Header = () => {
    return (
        <>
            <Nav variant="pills" activeKey="1">
                <Nav.Item>
                    <Nav.Link as={Link} to="/" eventKey="1">
                        Trang chủ
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>

                    <Nav.Link as={Link} to="/devices" eventKey="2">
                        Thuê thiết bị
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/my-rented-devices">
                        Thiết bị đã thuê
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/my-maintenance-schedule">
                        Lịch bảo trì cá nhân
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link disabled>
                        Lịch sửa chữa cá nhân
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/login" className="text-info">
                        Đăng nhập
                    </Nav.Link>
                </Nav.Item>
            </Nav>
        </>
    );
};

export default Header;
