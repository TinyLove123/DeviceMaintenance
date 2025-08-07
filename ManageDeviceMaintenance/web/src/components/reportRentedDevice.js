import React, { useContext, useState } from "react";
import { Button, Form } from "react-bootstrap";

import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const IncidentReportForm = ({ rentedDeviceId, onClose }) => {
    const [user] = useContext(MyUserContext);
    const [title, setTitle] = useState("");
    const [detailDescribe, setDetailDescribe] = useState("");
    const [isEmergency, setIsEmergency] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await authApis().post(endpoints['reportRentedDevice'](rentedDeviceId), {
                title: title.trim(),
                detailDescribe: detailDescribe.trim(),
                isEmergency: isEmergency
            });

            alert("Báo cáo sự cố thành công!");
            onClose(); // ✅ đóng modal
        } catch (err) {
            const msg = err?.response?.data?.error || "Lỗi gửi báo cáo!";
            alert(msg);
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group controlId="title" className="mb-3">
                <Form.Label>Tiêu đề</Form.Label>
                <Form.Control
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                />
            </Form.Group>

            <Form.Group controlId="detailDescribe" className="mb-3">
                <Form.Label>Mô tả chi tiết</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={4}
                    value={detailDescribe}
                    onChange={(e) => setDetailDescribe(e.target.value)}
                    required
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="isEmergency">
                <Form.Check
                    type="checkbox"
                    label="Là sự cố khẩn cấp"
                    checked={isEmergency}
                    onChange={(e) => setIsEmergency(e.target.checked)}
                />
            </Form.Group>

            <div className="d-flex justify-content-end gap-2">
                <Button variant="secondary" onClick={onClose}>
                    Hủy
                </Button>
                <Button type="submit" variant="primary">
                    Gửi báo cáo
                </Button>
            </div>
        </Form>
    );
};

export default IncidentReportForm;
