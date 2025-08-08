import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import RentFormModal from "./rentForm";

const Devices = () => {
    const [devices, setDevices] = useState([]);
    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [user] = useContext(MyUserContext);
    const navigate = useNavigate();

    const [showModal, setShowModal] = useState(false);
    const [selectedDeviceId, setSelectedDeviceId] = useState(null);
    const [keyword, setKeyword] = useState("");
    const [debouncedKeyword, setDebouncedKeyword] = useState("");

    const loadDevices = async () => {
        try {
            setLoading(true);
            const params = { page };

            if (debouncedKeyword) {
                params.kw = debouncedKeyword;
            }

            const res = await Apis.get(endpoints['devices'], { params });

            if (res.data.length === 0 && page > 1) {
                setPage(0);
                return;
            }

            const sorted = res.data.sort((a, b) =>
                a.statusDevice === 'active' && b.statusDevice !== 'active' ? -1 : 1
            );

            setDevices(prev => page === 1 ? sorted : [...prev, ...sorted]);
        } catch (err) {
            console.error(err);
            setError("Không thể tải thiết bị.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        const delayDebounce = setTimeout(() => {
            setDebouncedKeyword(keyword);
            setPage(1);
        }, 500);

        return () => clearTimeout(delayDebounce);
    }, [keyword]);

    useEffect(() => {
        loadDevices();
    }, [page, debouncedKeyword]);
    const openRentModal = (deviceId) => {
        if (!user) {
            alert("Vui lòng đăng nhập để thuê thiết bị.");
            navigate(`/login?next=/devices`);
            return;
        }
        setSelectedDeviceId(deviceId);
        setShowModal(true);
    };

    const handleRentSubmit = async (formData) => {
        try {
            await authApis().post(endpoints['rentDeviceRequest'](selectedDeviceId), formData);
            alert("Thuê thiết bị thành công!");
            setShowModal(false);
            setPage(1);
        } catch (err) {
            alert("Lỗi khi thuê thiết bị.");
            console.error(err);
        }
    };

    return (
        <Container className="mt-4">
            <h2 className="mb-4">Danh sách thiết bị</h2>

            {loading && page === 1 ? (
                <p>Đang tải...</p>
            ) : error ? (
                <p className="text-danger">{error}</p>
            ) : (

                <Row>
                    <Form className="mb-3">
                        <Form.Control
                            type="text"
                            placeholder="Tìm kiếm thiết bị..."
                            value={keyword}
                            onChange={(e) => setKeyword(e.target.value)}
                        />
                    </Form>
                    {devices.map(device => (
                        <Col key={device.id} md={3} xs={6} className="p-2">
                            <Card className="h-100">
                                <Card.Img
                                    variant="top"
                                    src={device.image}
                                    style={{ height: '200px', objectFit: 'cover' }}
                                />
                                <Card.Body className="d-flex flex-column">
                                    <Card.Title>{device.nameDevice}</Card.Title>
                                    <Card.Text className="text-danger fw-bold">
                                        {new Intl.NumberFormat('vi-VN').format(device.price)} VNĐ
                                    </Card.Text>
                                    <Card.Text>
                                        Trạng thái:
                                        <span className={`ms-2 badge ${device.statusDevice === 'active' ? 'bg-success' : 'bg-secondary'}`}>
                                            {device.statusDevice === 'active' ? 'Có sẵn' : 'Đã thuê'}
                                        </span>
                                    </Card.Text>
                                    <div className="mt-auto d-flex gap-2 flex-wrap">
                                        <Button variant="outline-primary" onClick={() => navigate(`/devices/${device.id}`)}>
                                            Chi tiết
                                        </Button>
                                        <Button
                                            variant="success"
                                            disabled={device.statusDevice !== 'active'}
                                            onClick={() => openRentModal(device.id)}
                                        >
                                            Thuê ngay
                                        </Button>
                                    </div>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            )}

            {page > 0 && !loading && (
                <div className="text-center mt-3">
                    <Button variant="outline-primary" onClick={() => setPage(prev => prev + 1)}>
                        Xem thêm...
                    </Button>
                </div>
            )}

            {/* Modal thuê */}
            <RentFormModal
                show={showModal}
                onHide={() => setShowModal(false)}
                onSubmit={handleRentSubmit}
            />
        </Container>
    );
};

export default Devices;
