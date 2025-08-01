import { Container, Navbar, NavDropdown } from 'react-bootstrap';
import Nav from 'react-bootstrap/Nav';
import { Link } from 'react-router-dom';

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
                        Danh sách sản phẩm
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/rented_devices">
                        Thuê thiết bị
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/rented_devices">
                        Thiết bị đã thuê
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/maintenance_schedule">
                        Lịch bảo trì cá nhân
                    </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                    <Nav.Link as={Link} to="/repair_schedule">
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
