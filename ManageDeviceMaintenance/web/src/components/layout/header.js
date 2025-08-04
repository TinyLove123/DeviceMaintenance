import Nav from 'react-bootstrap/Nav';
import { Link, useLocation } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const Header = () => {
    const location = useLocation();
    const pathname = location.pathname;

    // Ánh xạ pathname thành eventKey
    const pathToKey = {
        '/': '1',
        '/devices': '2',
        '/my-rented-devices': '3',
        '/my-maintenance-schedule': '4',
        '/login': '6',
    };

    return (
        <Nav variant="pills" activeKey={pathToKey[pathname]}>
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
                <Nav.Link as={Link} to="/my-rented-devices" eventKey="3">
                    Thiết bị đã thuê
                </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link as={Link} to="/my-maintenance-schedule" eventKey="4">
                    Lịch bảo trì cá nhân
                </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link disabled>
                    Lịch sửa chữa cá nhân
                </Nav.Link>
            </Nav.Item>
            <Nav.Item>
                <Nav.Link as={Link} to="/login" eventKey="6" className="text-info">
                    Đăng nhập
                </Nav.Link>
            </Nav.Item>
        </Nav>
    );
};

export default Header;
