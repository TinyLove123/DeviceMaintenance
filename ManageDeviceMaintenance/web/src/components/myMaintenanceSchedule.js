import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { useContext, useEffect, useState } from "react";
import { Badge, Button, ButtonGroup, Col, Container, Row, Table } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import IncidentModal from "./reportMaintenanceDevice";

dayjs.extend(customParseFormat);

const MyMaintenanceSchedule = () => {
  const [user] = useContext(MyUserContext);
  const [schedules, setSchedules] = useState([]);
  const [statusFilter, setStatusFilter] = useState("in_completed");
  const [openIncidentModal, setOpenIncidentModal] = useState(false);
  const [selectedScheduleId, setSelectedScheduleId] = useState(null);
  const navigate = useNavigate();

  const loadSchedules = async () => {
    try {
      let res = await authApis().get(endpoints.myMaintenanceSchedules);
      let filtered = res.data
        .filter(s => s.progress?.toLowerCase() === statusFilter.toLowerCase())
        .sort((a, b) => new Date(a.startDate) - new Date(b.startDate));
      setSchedules(filtered);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (user) loadSchedules();
  }, [user, statusFilter]);

  const updateProgress = async (schedule) => {
    const { id, progress, startDate } = schedule;
    let nextProgress = null;

    if (progress === "in_completed") {
      nextProgress = "in_progress";
    } else if (progress === "in_progress") {
      nextProgress = "completed";
    } else {
      return;
    }

    const today = dayjs();
    const scheduledDate = dayjs(startDate, "DD/MM/YYYY HH:mm:ss");

    if (progress === "in_completed" && scheduledDate.isAfter(today, "day")) {
      alert("⚠️ Chưa đến ngày bảo trì, không thể bắt đầu.");
      return;
    }

    if (!window.confirm(`Xác nhận chuyển trạng thái sang "${nextProgress}"?`)) return;

    try {
      const res = await authApis().post(endpoints.maintenanceScheduleDetail(id), {
        progress: nextProgress
      });

      const updated = res.data;
      if (!updated || updated.progress === progress) return;

      setSchedules(prev =>
        prev.map(s => (s.id === id ? { ...s, progress: nextProgress } : s))
      );
    } catch (err) {
      console.error("Lỗi hệ thống:", err);
    }
  };

  const renderProgressBadge = (progress) => {
    switch (progress) {
      case "completed":
        return <Badge bg="success">Đã hoàn thành</Badge>;
      case "in_progress":
        return <Badge bg="warning" text="dark">Đang thực hiện</Badge>;
      case "in_completed":
      default:
        return <Badge bg="secondary">Chưa bắt đầu</Badge>;
    }
  };

  return (
    <Container className="py-5">
      <Row className="justify-content-center mb-4">
        <Col md={8}>
          <h2 className="text-center mb-3">Lịch bảo trì của tôi</h2>
          <ButtonGroup className="d-flex justify-content-center mb-3">
            {["in_completed", "in_progress", "completed"].map(status => (
              <Button
                key={status}
                variant={statusFilter === status ? "dark" : "outline-secondary"}
                onClick={() => setStatusFilter(status)}
              >
                {status === "in_completed"
                  ? "Chưa hoàn thành"
                  : status === "in_progress"
                    ? "Đang thực hiện"
                    : "Đã hoàn thành"}
              </Button>
            ))}
          </ButtonGroup>
        </Col>
      </Row>

      {schedules.length === 0 ? (
        <Row className="justify-content-center">
          <Col md="auto">
            <p className="text-muted fst-italic">Không có lịch nào.</p>
          </Col>
        </Row>
      ) : (
        <Row>
          <Col>
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Thiết bị</th>
                  <th>Ngày bảo trì</th>
                  <th>Trạng thái</th>
                  {["in_completed", "in_progress"].includes(statusFilter) && <th>Hành động</th>}
                </tr>
              </thead>
              <tbody>
                {schedules.map((s, idx) => (
                  <tr key={s.id}>
                    <td>{idx + 1}</td>
                    <td>{s.deviceName}</td>
                    <td>{dayjs(s.startDate, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY")}</td>
                    <td>{renderProgressBadge(s.progress)}</td>
                    {["in_completed", "in_progress"].includes(statusFilter) && (
                      <td>
                        <Button
                          size="sm"
                          variant="danger"
                          className="me-2"
                          onClick={() => {
                            setSelectedScheduleId(s.id);
                            setOpenIncidentModal(true);
                          }}
                        >
                          Báo cáo sự cố
                        </Button>

                        <Button
                          size="sm"
                          variant={s.progress === "in_completed" ? "warning" : "success"}
                          className="me-2"
                          onClick={() => updateProgress(s)}
                        >
                          {s.progress === "in_completed" ? "Bắt đầu" : "Hoàn thành"}
                        </Button>

                        {s.progress === "in_progress" && (
                          <Button
                            size="sm"
                            variant="info"
                            onClick={() => navigate(`/my-maintenance-report/${s.id}`)}
                          >
                            Chỉnh sửa báo cáo
                          </Button>
                        )}
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </Table>
          </Col>
        </Row>
      )}

      {/* Modal sự cố */}
      {openIncidentModal && (
        <IncidentModal
          isOpen={openIncidentModal}
          onClose={() => setOpenIncidentModal(false)}
          onSubmit={() => {
            setOpenIncidentModal(false);
            loadSchedules();
          }}
          scheduleId={selectedScheduleId}
        />
      )}
    </Container>
  );
};

export default MyMaintenanceSchedule;
