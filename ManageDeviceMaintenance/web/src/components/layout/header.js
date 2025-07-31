import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
    return (
        <Navbar bg="light" expand="lg" className="shadow-sm">
            <Container>
                <Navbar.Brand as={Link} to="/" className="fw-bold text-primary">
                    Thuê Thiết Bị
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="main-navbar-nav" />
                <Navbar.Collapse id="main-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/" eventKey="1">
                            Trang chủ
                        </Nav.Link>
                        <Nav.Link as={Link} to="/devices" eventKey="2">
                            Danh sách sản phẩm
                        </Nav.Link>
                        <Nav.Link disabled>Thuê thiết bị</Nav.Link>
                        <Nav.Link disabled>Thiết bị đã thuê</Nav.Link>
                        <Nav.Link disabled>Lịch bảo trì cá nhân</Nav.Link>
                        <Nav.Link disabled>Lịch sửa chữa cá nhân</Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link as={Link} to="/login" className="text-info">
                            Đăng nhập
                        </Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
