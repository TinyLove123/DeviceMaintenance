// src/pages/MyRentedDeviceDetail.jsx
import React, { useEffect, useState } from "react";
import { Alert, Button, Card, Col, Container, Image, ListGroup, Modal, Row, Spinner } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import IncidentReportForm from "./reportRentedDevice"; // ✅ Import form


const MyRentedDeviceDetail = () => {
    const { id } = useParams();
    const [rentedDeviceDetail, setrentedDeviceDetail] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [showReportModal, setShowReportModal] = useState(false);



    useEffect(() => {
        const fetchRentedDeviceDetail = async () => {
            try {
                const res = await authApis().get(endpoints.rentedDeviceDetail(id));
                setrentedDeviceDetail(res.data);
            } catch (err) {
                setError("Lỗi khi tải dữ liệu");
            } finally {
                setLoading(false);
            }
        };

        fetchRentedDeviceDetail();
    }, [id]);

    if (loading)
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" />
                <div>Đang tải dữ liệu...</div>
            </Container>
        );

    if (error)
        return (
            <Container className="mt-5">
                <Alert variant="danger">{error}</Alert>
            </Container>
        );

    if (!rentedDeviceDetail || !rentedDeviceDetail.deviceId)
        return (
            <Container className="mt-5 text-center">
                <Alert variant="warning">Không có dữ liệu thiết bị</Alert>
            </Container>
        );

    const device = rentedDeviceDetail.deviceId;

    return (
        <Container className="my-4">
            <Card className="shadow-sm">
                <Card.Header as="h4" className="bg-secondary text-white">
                    Chi tiết thiết bị thuê
                </Card.Header>
                <Card.Body>
                    <Row>
                        <Col md={5} className="text-center">
                            {device.image ? (
                                <Image
                                    src={device.image}
                                    alt="Hình ảnh thiết bị"
                                    fluid
                                    rounded
                                    style={{ maxHeight: "350px", objectFit: "contain", border: "1px solid #ddd", padding: "10px" }}
                                />
                            ) : (
                                <div className="text-muted">Không có hình ảnh thiết bị</div>
                            )}
                        </Col>

                        <Col md={7}>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Tên thiết bị:</strong> {device.nameDevice}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Hãng sản xuất:</strong> {device.manufacturer}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Trạng thái:</strong> {device.statusDevice}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Ngày mua:</strong>{" "}
                                    {device.purchaseDate ? new Date(device.purchaseDate).toLocaleDateString() : "Chưa có"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Giá thuê:</strong> {(rentedDeviceDetail.price ?? 0).toLocaleString()} VND
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Thời gian thuê:</strong>{" "}
                                    {rentedDeviceDetail.startDate && rentedDeviceDetail.endDate
                                        ? `${new Date(rentedDeviceDetail.startDate).toLocaleDateString()} - ${new Date(
                                            rentedDeviceDetail.endDate
                                        ).toLocaleDateString()}`
                                        : "Chưa có"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Col>
                    </Row>

                    <h5 className="mt-4">Chi phí sửa chữa</h5>
                    <ListGroup>
                        {device.repairCostSet && device.repairCostSet.length > 0 ? (
                            device.repairCostSet.map((item) => (
                                <ListGroup.Item key={item.id}>
                                    {item.repairTypeId.type} - {item.price.toLocaleString()} VND
                                </ListGroup.Item>
                            ))
                        ) : (
                            <ListGroup.Item>Không có chi phí sửa chữa</ListGroup.Item>
                        )}
                    </ListGroup>
                    <div className="d-flex gap-2 mt-3">
                        <Button
                            variant="danger"
                            onClick={() => setShowReportModal(true)}
                        >
                            Báo cáo sự cố
                        </Button>

                        <Button
                            variant="secondary"
                            onClick={() => navigate(`/my-rented-device-detail/incident-report/${rentedDeviceDetail.id}`)}
                        >
                            Chi tiết sự cố
                        </Button>
                    </div>
                </Card.Body>
            </Card>
            <Modal show={showReportModal} onHide={() => setShowReportModal(false)} size="lg" centered>
                <Modal.Header closeButton>
                    <Modal.Title>Báo cáo sự cố</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <IncidentReportForm
                        rentedDeviceId={rentedDeviceDetail.deviceId.id} 
                        onClose={() => setShowReportModal(false)} 
                    />
                </Modal.Body>
            </Modal>
        </Container>

    );
};

export default MyRentedDeviceDetail;
