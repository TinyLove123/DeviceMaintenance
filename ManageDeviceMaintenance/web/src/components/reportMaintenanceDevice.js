import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";

const IncidentModal = ({ isOpen, onClose, onSubmit, scheduleId }) => {
  const [title, setTitle] = useState("");
  const [detailDescribe, setDetailDescribe] = useState("");
  const [isEmergency, setIsEmergency] = useState(false);
  const [linkedAt, setLinkedAt] = useState(new Date().toISOString().slice(0, 16)); // yyyy-MM-ddTHH:mm
  const [note, setNote] = useState("");

  const handleSubmit = async () => {
    try {
      await authApis().post(endpoints["addIncidentByMaintenanceSchedule"](scheduleId), {
        incident: {
          title: title.trim(),
          detailDescribe: detailDescribe.trim(),
          isEmergency,
        },
        note: note.trim(),
        linkedAt: new Date(linkedAt).toISOString(),
      });

      alert("✅ Báo cáo sự cố thành công!");
      onSubmit();
      onClose();
    } catch (err) {
      console.error("❌ Lỗi khi gửi báo cáo:", err);
      alert("Gửi báo cáo thất bại!");
    }
  };

  return (
    <Modal show={isOpen} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>🛠 Báo cáo sự cố thiết bị</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3">
            <Form.Label>Tiêu đề</Form.Label>
            <Form.Control
              type="text"
              placeholder="Nhập tiêu đề sự cố..."
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Mô tả chi tiết</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              placeholder="Nhập mô tả chi tiết sự cố..."
              value={detailDescribe}
              onChange={(e) => setDetailDescribe(e.target.value)}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Check
              type="checkbox"
              label="Sự cố khẩn cấp"
              checked={isEmergency}
              onChange={(e) => setIsEmergency(e.target.checked)}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Thời điểm ghi nhận sự cố</Form.Label>
            <Form.Control
              type="datetime-local"
              value={linkedAt}
              onChange={(e) => setLinkedAt(e.target.value)}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Ghi chú</Form.Label>
            <Form.Control
              type="text"
              placeholder="Ghi chú thêm (nếu có)..."
              value={note}
              onChange={(e) => setNote(e.target.value)}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          Hủy
        </Button>
        <Button variant="danger" onClick={handleSubmit}>
          Gửi báo cáo
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default IncidentModal;
