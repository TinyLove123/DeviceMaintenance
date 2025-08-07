import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import RentFormModal from "./rentForm";

const Devices = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [user, dispatch] = useContext(MyUserContext);
    const navigate = useNavigate();

    // State cho modal
    const [showModal, setShowModal] = useState(false);
    const [selectedDeviceId, setSelectedDeviceId] = useState(null);

    const loadDevices = async () => {
        try {
            setLoading(true);
            setError(null);

            let res = await Apis.get(endpoints['devices']);

            let sortedDevices = res.data.sort((a, b) => {
                if (a.statusDevice === 'active' && b.statusDevice !== 'active') return -1;
                if (a.statusDevice !== 'active' && b.statusDevice === 'active') return 1;
                return 0;
            });

            setDevices(sortedDevices);
        } catch (err) {
            setError("Không thể tải danh sách thiết bị");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

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
            const res = await authApis().post(
                endpoints['rentDeviceRequest'](selectedDeviceId),
                formData // body đúng định dạng yêu cầu
            );

            alert("Thuê thiết bị thành công!");
            loadDevices();
        } catch (err) {
            alert("Lỗi khi thuê thiết bị. Vui lòng thử lại.");
            console.error(err);
        }
    };

    useEffect(() => {
        loadDevices();
    }, []);

    return (
        <div className="container mt-3">
            <h2 className="mb-4">Danh sách thiết bị</h2>

            {loading ? (
                <p>Đang tải...</p>
            ) : error ? (
                <p className="text-danger">{error}</p>
            ) : (
                <Row>
                    {devices.map(d => (
                        <Col key={d.id} md={3} xs={6} className="p-2">
                            <Card className="h-100">
                                <Card.Img variant="top" src={d.image} style={{ height: '200px', objectFit: 'cover' }} />
                                <Card.Body className="d-flex flex-column">
                                    <Card.Title>{d.nameDevice}</Card.Title>
                                    <Card.Text className="text-danger fw-bold">
                                        {new Intl.NumberFormat('vi-VN').format(d.price)} VNĐ
                                    </Card.Text>
                                    <Card.Text>
                                        Trạng thái:
                                        <span className={`ms-2 badge ${d.statusDevice === 'active' ? 'bg-success' : 'bg-secondary'}`}>
                                            {d.statusDevice === 'active' ? 'Có sẵn' : 'Đã thuê'}
                                        </span>
                                    </Card.Text>
                                    <div className="mt-auto d-flex gap-2 flex-wrap">
                                        <Button variant="outline-primary" onClick={() => navigate(`/devices/${d.id}`)}>
                                            Chi tiết
                                        </Button>
                                        <Button
                                            variant="success"
                                            onClick={() => openRentModal(d.id)}
                                            disabled={d.statusDevice !== 'active'}
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

            {/* Modal thuê */}
            <RentFormModal
                show={showModal}
                onHide={() => setShowModal(false)}
                onSubmit={handleRentSubmit}
            />
        </div>
    );
};

export default Devices;
