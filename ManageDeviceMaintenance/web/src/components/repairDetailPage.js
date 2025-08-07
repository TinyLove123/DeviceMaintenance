import React, { useEffect, useState } from "react";
import { Alert, Button, Card, Col, Form, Modal, Row, Spinner, Table } from "react-bootstrap";
import { useLocation, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";

const RepairDetailPage = () => {
    const { id: repairId } = useParams();
    const location = useLocation();
    const deviceId = location.state?.deviceId;

    const [repair, setRepair] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [repairCosts, setRepairCosts] = useState([]);

    const [showModal, setShowModal] = useState(false);
    const [selectedRepairCostId, setSelectedRepairCostId] = useState(null);
    const [description, setDescription] = useState("");



    const fetchRepair = async () => {
        try {
            const res = await authApis().get(endpoints['getDetailRepair'](repairId));
            setRepair(res.data);
        } catch (err) {
            console.error(err);
            setError("Không thể tải dữ liệu sửa chữa.");
        } finally {
            setLoading(false);
        }
    };

    const fetchRepairCosts = async () => {
        try {
            const res = await authApis().get(endpoints['getRepairCost'](deviceId));
            setRepairCosts(res.data);
        } catch (err) {
            console.error(err);
            setError("Không thể tải dữ liệu chi phí.");
        }
    };

    const handleDeleteRepairDetail = async (repairDetailId) => {
        if (window.confirm("Bạn có chắc muốn xoá chi tiết này?")) {
            try {
                await authApis().delete(endpoints['deleteRepairDetail'](repairDetailId));
                alert("Xoá thành công!");
                fetchRepair();
            } catch (err) {
                console.error("Lỗi khi xoá chi tiết sửa chữa:", err);
                alert("Xoá thất bại.");
            }
        }
    };

    const handleAddRepairDetail = async () => {
        const payload = [
            {
                repairCostId: Number(selectedRepairCostId),
                description: description.trim()
            }
        ];

        try {
            await authApis().post(endpoints['addRepairDetail'](repairId), payload);
            alert("Đã thêm chi tiết sửa chữa!");
            setShowModal(false);
            setDescription("");
            setSelectedRepairCostId(null);
            fetchRepair();
        } catch (err) {
            console.error("Lỗi khi thêm chi tiết:", err);
            alert("Thêm thất bại.");
        }
    };

    const handleShowModal = (repairCostId) => {
        setSelectedRepairCostId(repairCostId);
        setShowModal(true);
    };

    useEffect(() => {
        fetchRepair();
        fetchRepairCosts();
    }, [repairId, deviceId]);

    if (loading) return <Spinner animation="border" />;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!repair) return <Alert variant="warning">Không có dữ liệu</Alert>;

    return (
        <div className="container mt-4">
            <h3 className="mb-4">Chi tiết báo cáo sửa chữa</h3>

            <Card className="mb-4 shadow">
                <Card.Body>
                    <h5>Nhân viên phụ trách</h5>
                    <p><strong>Họ tên:</strong> {repair.employeeId?.lastName} {repair.employeeId?.firstName}</p>
                    <p><strong>Email:</strong> {repair.employeeId?.email}</p>
                    <p><strong>SĐT:</strong> {repair.employeeId?.phone}</p>
                </Card.Body>
            </Card>

            <h5 className="mb-3">Chi tiết sửa chữa</h5>
            <Table striped bordered hover responsive className="shadow-sm">
                <thead>
                    <tr>
                        <th>Mã</th>
                        <th>Loại sửa chữa</th>
                        <th>Mô tả</th>
                        <th>Chi phí (VND)</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    {repair.repairDetailSet?.length > 0 ? (
                        repair.repairDetailSet.map((detail, idx) => (
                            <tr key={detail.id || idx}>
                                <td>{detail.id}</td>
                                <td>{detail.repairCostId?.repairTypeId?.type}</td>
                                <td>{detail.descriptionDetail}</td>
                                <td>{detail.repairCostId?.price?.toLocaleString()}</td>
                                <td>
                                    <Button
                                        variant="danger"
                                        size="sm"
                                        onClick={() => handleDeleteRepairDetail(detail.id)}
                                    >
                                        Xoá
                                    </Button>
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan={5} className="text-center">Không có chi tiết sửa chữa nào.</td>
                        </tr>
                    )}
                </tbody>
            </Table>

            <h5 className="mb-3 mt-4">Thêm chi tiết sửa chữa</h5>
            <Row>
                {repairCosts.map((cost, idx) => (
                    <Col md={4} key={idx} className="mb-3">
                        <Card className="shadow-sm h-100">
                            <Card.Body>
                                <p><strong>Loại sửa chữa:</strong> {cost.repairTypeId?.type}</p>
                                <p><strong>Giá:</strong> {cost.price?.toLocaleString()} VND</p>
                                <Button variant="primary" onClick={() => handleShowModal(cost.id)}>
                                    Chọn
                                </Button>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {/* Modal nhập mô tả */}
            <Modal show={showModal} onHide={() => setShowModal(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Thêm mô tả chi tiết</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="description">
                            <Form.Label>Mô tả</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                placeholder="Nhập mô tả chi tiết..."
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Hủy
                    </Button>
                    <Button
                        variant="success"
                        onClick={handleAddRepairDetail}
                        disabled={!description.trim()}
                    >
                        Thêm
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default RepairDetailPage;
