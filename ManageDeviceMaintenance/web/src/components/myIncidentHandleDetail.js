import moment from "moment";
import { useEffect, useState } from "react";
import { Button, Card, Col, Image, ListGroup, Row, Spinner } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";

const IncidentDetailPage = () => {
    const { id } = useParams();
    const [incident, setIncident] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [selectedCosts, setSelectedCosts] = useState([]);
    const [repair, setRepair] = useState(null);
    const navigate = useNavigate();
    const updateStatusIncident = async (id, incidentStatus) => {
        try {
            const res = await authApis().put(
                `${endpoints.updateIncidentStatus(id)}?status=${incidentStatus}`
            );
            setIncident(res.data);  // Cập nhật lại dữ liệu sự cố sau khi đổi trạng thái
        } catch (err) {
            console.error("Lỗi khi cập nhật sự cố:", err);
        }
    };

    const getRepair = async () => {
        try {
            await authApis().get(endpoints.getDetailRepair(id));

        } catch (err) {

        }
    }

    const addRepair = async () => {
        if (repair) {
            alert("Đã tồn tại báo cáo sửa chữa cho sự cố này!");
            return;
        }

        try {
            const repairData = {
                endDate: moment().add(1, 'days').format("YYYY-MM-DDTHH:mm:ss"),
            };

            await authApis().post(endpoints.addRepairByIncident(id), repairData);
            alert("Tạo báo cáo sửa chữa thành công!");
        } catch (err) {
            console.error("Lỗi khi tạo báo cáo sửa chữa:", err);
            alert("Lỗi khi tạo báo cáo sửa chữa!");
        }
    };



    useEffect(() => {
        const loadIncident = async () => {
            try {
                const res = await authApis().get(endpoints.myReportHandleDetail(id));
                setIncident(res.data);
            } catch (err) {
                console.error("Lỗi khi lấy chi tiết sự cố:", err);
            }
        };

        const loadRepair = async () => {
            try {
                const res = await authApis().get(endpoints.getDetailRepair(id));
                setRepair(res.data);  // ✅ Cập nhật state repair
            } catch (err) {
                setRepair(null); // Không có repair thì giữ null
            }
        };

        loadIncident();
        loadRepair();
    }, [id]);

    if (!incident) {
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" variant="primary" />
                <p>Đang tải chi tiết sự cố...</p>
            </div>
        );
    }

    const formatDate = (date) =>
        date ? moment(date).format("DD-MM-YYYY HH:mm") : "Chưa duyệt";

    const formatPrice = (price) =>
        price ? price.toLocaleString() + " VND" : "N/A";

    const UserCard = ({ user, title }) => (
        <Card className="mb-3 shadow">
            <Card.Header className="bg-secondary text-white">{title}</Card.Header>
            <Card.Body>
                <Row>
                    <Col md={4} className="d-flex justify-content-center align-items-start">
                        <Image
                            src={user?.avatar || "https://via.placeholder.com/100x100?text=Avatar"}
                            roundedCircle
                            width={100}
                            height={100}
                            onError={(e) => e.target.src = "https://via.placeholder.com/100x100?text=No+Image"}
                        />
                    </Col>
                    <Col md={8}>
                        <Card.Text><strong>Họ tên:</strong> {user?.lastName} {user?.firstName}</Card.Text>
                        <Card.Text><strong>Email:</strong> {user?.email}</Card.Text>
                        <Card.Text><strong>SĐT:</strong> {user?.phone}</Card.Text>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    );

    const DeviceCard = ({ device }) => (
        <Card className="shadow mb-4">
            <Card.Header className="bg-success text-white">Thiết bị liên quan</Card.Header>
            <Card.Body>
                <Row>
                    <Col md={4}>
                        <Card.Img
                            src={device?.image}
                            onError={(e) => e.target.src = "https://via.placeholder.com/200x200?text=No+Image"}
                            style={{ borderRadius: "8px" }}
                        />
                    </Col>
                    <Col md={8}>
                        <Card.Text><strong>Tên:</strong> {device?.nameDevice}</Card.Text>
                        <Card.Text><strong>Hãng:</strong> {device?.manufacturer}</Card.Text>
                        <Card.Text><strong>Giá:</strong> {formatPrice(device?.price)}</Card.Text>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    );

    const IncidentInfo = ({ incident }) => (
        <Card className="mb-4 shadow">
            <Card.Header className="bg-info text-white">Thông tin sự cố</Card.Header>
            <ListGroup variant="flush">
                <ListGroup.Item><strong>Tiêu đề:</strong> {incident.title}</ListGroup.Item>
                <ListGroup.Item><strong>Mô tả:</strong> {incident.detailDescribe}</ListGroup.Item>
                <ListGroup.Item><strong>Trạng thái:</strong> {incident.status}</ListGroup.Item>
                <ListGroup.Item><strong>Khẩn cấp:</strong> {incident.isEmergency ? "Có" : "Không"}</ListGroup.Item>
                <ListGroup.Item><strong>Ngày báo cáo:</strong> {formatDate(incident.reportDate)}</ListGroup.Item>
                <ListGroup.Item><strong>Ngày duyệt:</strong> {formatDate(incident.approvalDate)}</ListGroup.Item>
            </ListGroup>
        </Card>
    );

    return (
        <div className="container mt-4">
            <h2 className="text-primary mb-4">Chi tiết sự cố</h2>
            <Row>
                <Col md={8}>
                    <IncidentInfo incident={incident} />
                    <DeviceCard device={incident.deviceId} />
                </Col>
                <Col md={4}>
                    <UserCard user={incident.senderId} title="Người gửi" />
                    <UserCard user={incident.employeeId} title="Người xử lý" />
                    <UserCard user={incident.approvedBy} title="Người duyệt" />
                </Col>
            </Row>

            <div className="mt-3 d-flex gap-3">
                {/* Nút xử lý */}
                <Button
                    variant="secondary"
                    onClick={() => updateStatusIncident(incident.id, "IN_PROGRESS")}
                    disabled={incident.status === "IN_PROGRESS" || incident.status === "RESOLVED"} // disable nếu đã xử lý xong
                >
                    Xử lý
                </Button>

                {/* Nút hoàn tất, chỉ hiện khi đang xử lý */}
                {incident.status === "IN_PROGRESS" && (
                    <Button
                        variant="success"
                        onClick={() => updateStatusIncident(incident.id, "RESOLVED")}
                    >
                        Hoàn tất
                    </Button>
                )}

                {!repair ? (
                    <Button variant="primary" onClick={addRepair}>
                        Tạo báo cáo sửa chữa
                    </Button>
                )
                    : (
                        <Button variant="outline-success"
                            onClick={() => navigate(`/repair-detail/${incident.id}`, {
                                state: {
                                    deviceId: incident.deviceId.id
                                }
                            })}
                        >
                            Đã có báo cáo sửa chữa
                        </Button>
                    )}

            </div>
        </div>
    );
};

export default IncidentDetailPage;
