import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import LocationSelector from "./locationSelector";

const RentFormModal = ({ show, onHide, onSubmit }) => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [wardId, setWardId] = useState("");
    const [address, setAddress] = useState("");

    const handleSubmit = () => {
        if (!startDate || !endDate || !wardId || !address) {
            alert("Vui lòng nhập đầy đủ thông tin");
            return;
        }

        const data = {
            rentedDevice: {
                startDate,
                endDate,
                isRented: true
            },
            location: {
                wardId,
                address
            }
        };

        onSubmit(data);
        onHide(); // đóng modal
    };

    const handleLocationChange = (location) => {
        setWardId(location.wardId); // chỉ cần wardId
    };

    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title>Thuê thiết bị</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    <Form.Group className="mb-3">
                        <Form.Label>Ngày bắt đầu</Form.Label>
                        <Form.Control
                            type="date"
                            value={startDate}
                            onChange={(e) => setStartDate(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Ngày kết thúc</Form.Label>
                        <Form.Control
                            type="date"
                            value={endDate}
                            onChange={(e) => setEndDate(e.target.value)}
                        />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Chọn địa chỉ (Tỉnh → Xã)</Form.Label>
                        <LocationSelector onLocationChange={handleLocationChange} />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>Địa chỉ chi tiết</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="VD: 123 Lê Lợi, Q.1"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                        />
                    </Form.Group>
                </Form>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={onHide}>
                    Hủy
                </Button>
                <Button variant="primary" onClick={handleSubmit}>
                    Thuê thiết bị
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default RentFormModal;
