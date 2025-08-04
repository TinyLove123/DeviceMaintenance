import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const Devices = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true); // Sửa thành true ban đầu
    const [error, setError] = useState(null); // Thêm state error
    const navigate = useNavigate();
    const [user, dispatch] = useContext(MyUserContext);

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
    }

    const rentDevice = async (deviceId) => {
        if (!user) {
            alert("Vui lòng đăng nhập để thuê thiết bị.");
            navigate(`/login?next=/devices`);
            return;
        }

        try {
            const res = await authApis().post(
                endpoints['rentedDevice'](deviceId),
                {}
            );
           
            
            alert("Thuê thiết bị thành công!");
            loadDevices(); // Load lại danh sách sau khi thuê
            
        } catch (err) {
            if (err.response && err.response.status === 401) {
                alert("Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.");
                navigate('/login');
            } else {
                alert(`Lỗi khi thuê thiết bị: ${err.response?.data?.message || 'Vui lòng thử lại sau'}`);
            }
            console.error(err);
        }
    };

    useEffect(() => {
        loadDevices();
    }, []);

    

    return (
        <div className="container mt-3">
            <h2 className="mb-4">Danh sách thiết bị</h2>
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
                                <div className="mt-auto d-flex gap-2">
                                    <Button variant="outline-primary" onClick={() => navigate(`/devices/${d.id}`)}>
                                        Chi tiết
                                    </Button>
                                    <Button 
                                        variant="success"
                                        onClick={() => rentDevice(d.id)}
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
        </div>
    )
}

export default Devices;