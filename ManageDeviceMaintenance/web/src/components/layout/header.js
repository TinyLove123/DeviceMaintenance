import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useContext } from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import cookie from 'react-cookies';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { MyUserContext } from '../../configs/Context';

const Header = () => {
    const [user, dispatch] = useContext(MyUserContext);
    const location = useLocation();
    const pathname = location.pathname;
    const nav = useNavigate();

    const logout = () => {
        cookie.remove('token'); // Xoá token
        dispatch({ type: "logout" }); // Xoá user trong context
        nav("/"); // Quay về trang chủ
    };

    return (
        <Navbar bg="info" variant="light" expand="lg" sticky="top" className="shadow-sm">
            <Container>
                <Navbar.Brand as={Link} to="/" className="fw-bold fs-4">
                    Quản lý sửa chữa và bào trì
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/" active={pathname === '/'}>
                            Trang chủ
                        </Nav.Link>
                        {user?.userRole === 'CUSTOMER' && (
                            <>
                                <Nav.Link as={Link} to="/devices" active={pathname === '/devices'}>
                                    Thuê thiết bị
                                </Nav.Link>



                                <Nav.Link as={Link} to="/my-rented-devices" active={pathname === '/my-rented-devices'}>
                                    Thiết bị đã thuê
                                </Nav.Link>
                                <Nav.Link as={Link} to="/report-incident" active={pathname === '/report-incident'}>
                                    Báo cáo sự cố
                                </Nav.Link>

                                <Nav.Link as={Link} to="/report-incident" active={pathname === '/report-incident'}>
                                    Tiến trình xử lý xự cố
                                </Nav.Link>

                            </>
                        )}

                        {user?.userRole === 'EMPLOYEE' && (
                            <>
                                <Nav.Link as={Link} to="/my-maintenance-schedule" active={pathname === '/my-maintenance-schedule'}>
                                    Lịch bảo trì
                                </Nav.Link>
                                <Nav.Link as={Link} to="/my-report-handle/" active={pathname === '/my-report-handle'}>
                                    Xử lý sự cố
                                </Nav.Link>
                                <Nav.Link disabled>
                                    Lịch sửa chữa
                                </Nav.Link>
                            </>
                        )}


                    </Nav>

                    <Nav>
                        {user ? (
                            <Nav.Link onClick={logout} className="text-light">
                                Đăng xuất
                            </Nav.Link>
                        ) : (
                            <Nav.Link as={Link} to="/login" active={pathname === '/login'} className="text-light">
                                Đăng nhập
                            </Nav.Link>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
