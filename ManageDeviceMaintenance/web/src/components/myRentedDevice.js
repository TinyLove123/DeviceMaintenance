import { useContext, useEffect, useState } from "react";
import { Alert, Button, Card, Col, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyRentedDevice = () => {
    const [user] = useContext(MyUserContext);
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const loadDevices = async () => {
            setLoading(true);
            try {
                const res = await authApis().get(endpoints['myRentedDevice']);
                setDevices(res.data);
                console.log(res.data)
            } catch (err) {
                console.error("Error loading rented devices:", err);
            } finally {
                setLoading(false);
            }
        };

        if (!user) {
            navigate("/login");
        } else {
            loadDevices();
        }
    }, [user, navigate]);

    if (!user) return null;

    if (loading)
        return <Spinner animation="border" variant="primary" className="mt-3" />;

    if (devices.length === 0)
        return <Alert variant="info" className="mt-3 mx-3">Bạn chưa thuê thiết bị nào!</Alert>;

    return (
        <Row className="m-2">
            {devices.map(d => (
                <Col key={d.id} md={3} xs={6} className="p-2">
                    <Card>
                        <Card.Img
                            variant="top"
                            src={d.image}
                        />
                        <Card.Body>
                            <Card.Title>{d.deviceName}</Card.Title>
                            <Card.Text>Hãng: {d.manufacturer}</Card.Text>
                            <Card.Text>
                                Ngày thuê: {new Date(d.startDate).toLocaleDateString()}
                            </Card.Text>
                            <Button
                                variant="primary"
                                onClick={() => navigate(`/my-rented-device-detail/${d.id}`)}
                            >
                                Chi tiết
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            ))}
        </Row>
    );
};

export default MyRentedDevice;
