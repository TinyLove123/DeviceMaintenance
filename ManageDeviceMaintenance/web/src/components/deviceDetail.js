import { useEffect, useState } from "react";
import { Badge, Card, Col, Row, Spinner } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";




const DeviceDetail = () => {

  const { id } = useParams();
  const [device, setDevice] = useState(null);
  const [loading, setLoading] = useState(false);



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


  useEffect(() => {


    load1Device();
  }, [id]);



  if (!device) {
    return <Spinner animation="border" variant="primary" className="m-5" />;
  }

  return (
    <>
      <Card className="mt-4 p-3 shadow-sm">
        {device.image ? (
          <Card.Img
            variant="top"
            src={device.image}
            style={{ height: "330px", objectFit: "contain" }}
          />
        ) : (
          <div
            style={{
              height: "330px",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              backgroundColor: "#f0f0f0",
              border: "1px dashed #ccc",
            }}
          >
            <span>Không có ảnh</span>
          </div>
        )}

        <Card.Body>
          <Card.Title className="mb-3">
            <h5 className="fw-bold">{device.nameDevice}</h5>
          </Card.Title>

          <Row className="mb-2">
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Giá:</strong> {device.price.toLocaleString("vi-VN")} VNĐ
              </Card.Text>
            </Col>
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Ngày mua:</strong>{" "}
                {new Date(device.purchaseDate).toLocaleDateString("vi-VN")}
              </Card.Text>
            </Col>
          </Row>

          <Row className="mb-2">
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Trạng thái:</strong>{" "}
                <Badge bg={device.statusDevice === "Tốt" ? "success" : "warning"}>
                  {device.statusDevice}
                </Badge>
              </Card.Text>
            </Col>
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Tần suất bảo trì:</strong>{" "}
                {device.frequency} ngày
              </Card.Text>
            </Col>
          </Row>

          <Card.Text className="mb-2">
            <strong className="text-primary">Chi phí sửa chữa:</strong>
            <ul className="ms-3 mb-0">
              {device.repairCostSet?.length > 0 ? (
                device.repairCostSet.map((rc, idx) => (
                  <li key={idx}>
                    {rc.repairTypeId?.type}: {rc.price.toLocaleString("vi-VN")} VNĐ
                  </li>
                ))
              ) : (
                <li>Không có chi phí sửa chữa</li>
              )}
            </ul>
          </Card.Text>

          <Row className="mb-2">
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Loại sản phẩm:</strong>{" "}
                {device.categoryId?.type}
              </Card.Text>
            </Col>
            <Col md={6}>
              <Card.Text>
                <strong className="text-primary">Nhà sản xuất:</strong>{" "}
                {device.manufacturer}
              </Card.Text>
            </Col>
          </Row>

          <Card.Text className="mb-2">
            <strong className="text-primary">Mô tả:</strong>{" "}
            {device.description || "Không có mô tả."}
          </Card.Text>

          <Card.Text>
            <strong className="text-primary">Địa chỉ:</strong>{" "}
            {device.currentLocationId?.wardId?.fullName},{" "}
            {device.currentLocationId?.wardId?.provinceCode?.fullName}
          </Card.Text>
        </Card.Body>
      </Card>
    </>

  )
}



export default DeviceDetail