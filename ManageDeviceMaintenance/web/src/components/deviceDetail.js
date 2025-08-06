import { useEffect, useState } from "react";
import { Badge, Button, Card, Col, Row, Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";

const DeviceDetail = () => {
  const { id } = useParams();
  const [device, setDevice] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const load1Device = async () => {
      try {
        setLoading(true);
        let res = await Apis.get(endpoints.deviceDetail(id));
        console.log(res.data);
        setDevice(res.data);
      } catch (ex) {
        console.error(ex);
      } finally {
        setLoading(false);
      }
    };
  
    load1Device();
  }, [id]);

  if (loading || !device) {
    return (
      <div className="d-flex justify-content-center mt-5">
        <Spinner animation="border" variant="primary" />
      </div>
    );
  }

  return (
    <Card className="mt-4 shadow-sm">
      <Row className="g-0">
        <Col md={5}>
          {device.image ? (
            <Card.Img
              src={device.image}
              alt={device.nameDevice}
              className="img-fluid h-100"
              style={{ objectFit: "cover" }}
            />
          ) : (
            <div
              className="h-100 d-flex align-items-center justify-content-center text-muted"
              style={{ background: "#b3e5fc", minHeight: "300px" }}
            >
              Không có ảnh
            </div>
          )}
        </Col>

        <Col md={7}>
          <Card.Body>
            <Card.Title className="fs-4 fw-bold text-primary">
              {device.nameDevice}
            </Card.Title>

            <Row className="mb-2">
              <Col md={6}>
                <strong>Giá:</strong>{" "}
                {device.price?.toLocaleString("vi-VN")} VNĐ
              </Col>
              <Col md={6}>
                <strong>Ngày mua:</strong>{" "}
                {new Date(device.purchaseDate).toLocaleDateString("vi-VN")}
              </Col>
            </Row>

            <Row className="mb-2">
              <Col md={6}>
                <strong>Trạng thái:</strong>{" "}
                <Badge bg={device.statusDevice === "Tốt" ? "success" : "warning"}>
                  {device.statusDevice}
                </Badge>
              </Col>
              <Col md={6}>
                <strong>Tần suất bảo trì:</strong> {device.frequency} ngày
              </Col>
            </Row>

            <Row className="mb-2">
              <Col md={6}>
                <strong>Loại:</strong> {device.categoryId?.type}
              </Col>
              <Col md={6}>
                <strong>Hãng:</strong> {device.manufacturer}
              </Col>
            </Row>

            <div className="mb-2">
              <strong>Chi phí sửa chữa:</strong>
              <ul className="ms-3">
                {device.repairCostSet?.length > 0 ? (
                  device.repairCostSet.map((rc, i) => (
                    <li key={i}>
                      {rc.repairTypeId?.type}: {rc.price.toLocaleString("vi-VN")} VNĐ
                    </li>
                  ))
                ) : (
                  <li>Không có</li>
                )}
              </ul>
            </div>

            <div className="mb-2">
              <strong>Mô tả:</strong>{" "}
              {device.description || "Không có mô tả."}
            </div>

            <div className="mb-3">
              <strong>Địa chỉ:</strong>{" "}
              {device.currentLocationId?.wardId?.fullName},{" "}
              {device.currentLocationId?.wardId?.provinceCode?.fullName}
            </div>

            <Button variant="primary">Thuê thiết bị</Button>
          </Card.Body>
        </Col>
      </Row>
    </Card>
  );
};

export default DeviceDetail;
