import { useContext, useEffect, useState } from "react";
import { Button, Container, Modal, Table } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyIncidentReport = () => {
  const [user] = useContext(MyUserContext);
  const [incidentReports, setIncidentReports] = useState([]);
  const [show, setShow] = useState(false);
  const [incidentDetail, setIncidentDetail] = useState(null);

  useEffect(() => {
   
    const loadIncidentReports = async () => {
      try {
        const res = await authApis().get(endpoints["getMyIncidentReport"]);
        setIncidentReports(res.data);
      } catch (err) {
        console.error("Lỗi khi tải danh sách xử lý:", err);
      }
    };

    loadIncidentReports();
  }, []);

  const formatDate = (timestamp) => {
    if (!timestamp) return "Chưa có";
    const date = new Date(timestamp);
    return date.toLocaleDateString("vi-VN");
  };

  const handleClose = () => setShow(false);

  const handleShow = async (id) => {
    try {
      let res = await authApis().get(endpoints["repairDetail"](id)); 
      setIncidentDetail(res.data);
      setShow(true);
    } catch (err) {
      console.error("Lỗi khi tải chi tiết sự cố:", err);
    }
  };

  return (
    <Container className="mt-4">
      <h2 className="mb-4">Báo cáo sự cố của tôi</h2>

      {incidentReports.length === 0 ? (
        <p>Không có báo cáo nào.</p>
      ) : (
        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>Tiêu đề</th>
              <th>Mô tả</th>
              <th>Trạng thái</th>
              <th>Khẩn cấp</th>
              <th>Ngày báo cáo</th>
              <th>Ngày bắt đầu</th>
              <th>Ngày kết thúc</th>
              <th>Thiết bị</th>
              <th>Nhân viên xử lý</th>
              <th>Trạng thái tiếp nhận</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            {incidentReports.map((report) => (
              <tr key={report.id}>
                <td>{report.title || "Không có tiêu đề"}</td>
                <td>{report.detailDescribe || "Không có mô tả"}</td>
                <td>{report.status || "Chưa có"}</td>
                <td>{report.isEmergency ? "Có" : "Không"}</td>
                <td>{formatDate(report.reportDate)}</td>
                <td>{formatDate(report.startDate)}</td>
                <td>{formatDate(report.endDate)}</td>
                <td>{report.device?.nameDevice || "Chưa có"}</td>
                <td>
                  {report.employee
                    ? `${report.employee.lastName} ${report.employee.firstName}`
                    : "Chưa phân công"}
                </td>
                <td>{report.receptStatus || "Chưa cập nhật"}</td>
                <td>
                  <Button
                    variant="info"
                    size="sm"
                    onClick={() => handleShow(report.id)}
                  >
                    Chi tiết
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}

      {/* Modal Chi tiết */}
      <Modal show={show} onHide={handleClose} size="lg" centered>
        <Modal.Header closeButton>
          <Modal.Title>Chi tiết sự cố</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {incidentDetail ? (
            <>
              <p><strong>Nhân viên:</strong> {incidentDetail.employeeId?.lastName} {incidentDetail.employeeId?.firstName}</p>
              <p><strong>Trạng thái:</strong> {incidentDetail.progress}</p>
              <p><strong>Ngày kết thúc:</strong> {formatDate(incidentDetail.endDate)}</p>

              <h5>Danh sách sửa chữa</h5>
              <Table bordered hover>
                <thead>
                  <tr>
                    <th>Loại sửa chữa</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                  </tr>
                </thead>
                <tbody>
                  {incidentDetail.repairDetailSet?.map(d => (
                    <tr key={d.id}>
                      <td>{d.repairCostId?.repairTypeId?.type}</td>
                      <td>{d.descriptionDetail}</td>
                      <td>{d.repairCostId?.price?.toLocaleString()} VNĐ</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </>
          ) : (
            <p>Đang tải dữ liệu...</p>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>Đóng</Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default MyIncidentReport;
