import { useContext, useEffect, useState } from "react";
import { Alert, Button, Card, Col, Row, Spinner } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyRentedDevice = () => {
    const [user] = useContext(MyUserContext);
    const [rentedDevices, setRentedDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const loadRentedDevices = async () => {
            setLoading(true);
            try {
                const res = await authApis().get(endpoints['myRentedDevice']);
                setRentedDevices(res.data);
                console.log(res.data);
            } catch (err) {
                console.error("Error loading rented devices:", err);
            } finally {
                setLoading(false);
            }
        };

        if (!user) {
            navigate("/login");
        } else {
            loadRentedDevices();
        }
    }, [user, navigate]);

    if (!user) return null;

    if (loading)
        return <Spinner animation="border" variant="primary" className="mt-3" />;

    if (rentedDevices.length === 0)
        return <Alert variant="info" className="mt-3 mx-3">Bạn chưa thuê thiết bị nào!</Alert>;

    return (
        <Row className="m-2">
            {rentedDevices.map(rentedDevice => (
                <Col key={rentedDevice.id} md={3} xs={6} className="p-2">
                    <Card>
                        <Card.Img
                            variant="top"
                            src={rentedDevice.image}
                            onError={(e) => e.target.src = "https://via.placeholder.com/200x200?text=No+Image"}
                        />
                        <Card.Body>
                            <Card.Title>{rentedDevice.deviceName}</Card.Title>
                            <Card.Text>Hãng: {rentedDevice.manufacturer}</Card.Text>
                            <Card.Text>
                                Ngày thuê: {new Date(rentedDevice.startDate).toLocaleDateString()}
                            </Card.Text>
                            <Button
                                variant="primary"
                                onClick={() => navigate(`/my-rented-device-detail/${rentedDevice.id}`)}
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
