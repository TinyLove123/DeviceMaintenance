import { useEffect, useState } from "react";
import { Button, Card, Col, Row } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";



const Devices = () => {
    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const loadDevices = async () => {
        setLoading(true);
        let res = await Apis.get(endpoints['devices'])
        console.log(res.data);
        setDevices(res.data)
    }

    useEffect(() => {
        loadDevices();
    }, []);

    return (
        <>
            <Row>
                {devices.map(d => <Col key={d.id} md={3} xs={6} className="p-1">
                    <Card>
                        <Card.Img variant="top" src={d.image} />
                        <Card.Body>
                            <Card.Title>{d.nameDevice}</Card.Title>
                            <Card.Text>{d.price} VNĐ</Card.Text>
                            <Button variant="primary me-1" onClick={() => navigate(`/devices/${d.id}`)} >Xem chi tiết</Button>

                        </Card.Body>
                    </Card>
                </Col>)}
            </Row>
        </>
    )

}

export default Devices;

