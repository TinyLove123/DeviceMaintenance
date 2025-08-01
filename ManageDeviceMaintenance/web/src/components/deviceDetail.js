import { useEffect, useState } from "react";
import { Card, Spinner } from "react-bootstrap"
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";


const DeviceDetail = () =>{

  const { id } = useParams();
  const [device, setDevice] = useState(null);
  const [loading, setLoading] = useState(false);
  const [category, setCategory] = useState(null);
  const categoryName = categories.find(c => c.id === device.categoryId)?.type;

  const loadCategory = async (CategoryId) => {
    let res = await Apis.get(endpoints['categories']);
    setCategories(res.data);
  }

  const load1Device = async () => {
    try {
        setLoading(true);
        let res = await Apis.get(endpoints.deviceDetail(id));
        console.log(res.data);
        setDevice(res.data);
    } catch (ex) {
        console.error(ex);
    }finally{
      setLoading(false);
    }
  };


  useEffect(() => {
      

      load1Device();
      loadCategory();
    },[id]);

    

    if (!device) {
      return <Spinner animation="border" variant="primary" className="m-5" />;
    }

    return(
      <>
      <Card className="mt-4 p-3 shadow-sm">
          <Card.Img variant="top" src={device.image} style={{ height: "330px", objectFit: "contain" }} />
          <Card.Body>
            <Card.Title>{device.nameDevice}</Card.Title>
            <Card.Text><strong>Giá:</strong> {device.price}</Card.Text>
            <Card.Text><strong>Trạng thái:</strong> {device.statusDevice}</Card.Text>
            <Card.Text><strong>Mô tả:</strong> {device.description || "Không có mô tả."}</Card.Text>
            <Card.Text><strong>Loại sản phẩm:</strong> {categoryName}</Card.Text>
            <Card.Text><strong>Nhà sản xuất:</strong> {device.manufacturer}</Card.Text>
            <Card.Text><strong>Tần suất bảo trì:</strong> {device.frequency + " ngày" }</Card.Text>
            <Card.Text><strong>Vị trí hiện tại:</strong> {device.currentLocationId}</Card.Text>
          </Card.Body>
        </Card>
      </>
        
    )
}



export default DeviceDetail