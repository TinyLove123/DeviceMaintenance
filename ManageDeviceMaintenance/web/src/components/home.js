import { useEffect, useState } from "react";
import { Button, Card, Col, Container, Row, Spinner } from "react-bootstrap";
import { useNavigate, useSearchParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";


const Home = () => {

    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [q, setQ] = useState();
    const [page, setPage] = useState(1);
    const [params] = useSearchParams();
    const navigate = useNavigate();
    // const [categories,setCategories] = useState([]);

    // const loadCategories = async () => {

    //     let url=`${endpoints['categories']}?page=${page}`;

    //     try {
    //         setLoading(true);

    //       const res = await Apis.get(endpoints['categories']); // API categories
    //       setCategories(res.data);
    //     } catch (ex) {
    //       console.error(ex);
    //     }finally{
    //         setLoading(false);
    //     }
    //   };


    const loadDevice = async ()=>{

        let url=`${endpoints['devices']}?page=${page}`;

        try {
            setLoading(true);

            let res = await Apis.get(endpoints['devices']);
            setDevices(res.data)

        }catch(ex){
            console.error(ex);
        }finally{
            setLoading(false);
        }

        
    }

    useEffect(()=>{
        loadDevice();
        // loadCategories();
    },[page]);

    return (
        <>
           <Container className="my-4">
      <h2 className="text-center mb-4">Danh sách thiết bị</h2>

      {loading ? ( <div className="text-center"><Spinner variant="primary" animation="border" /></div>
      ) : (
        <Row className="g-4">
          {devices.map((d) => (
            <Col key={d.id} lg={3} md={4} sm={6} xs={12}>
              <Card className="h-100 shadow-sm">
                <Card.Img
                  variant="top"
                  src={d.image}
                  style={{ height: "200px", objectFit: "cover" }}/>
                <Card.Body className="d-flex flex-column">
                  <Card.Title className="text-truncate">{d.nameDevice}</Card.Title>
                  <Card.Text className="text-primary fw-bold">
                    {d.statusDevice}
                  </Card.Text>
                 
                  <div className="mt-auto d-flex justify-content-between">
                  <Button variant="primary me-1" onClick={() => navigate(`/devices/${d.id}`)} >Xem chi tiết</Button>
                    <Button variant="danger" size="sm">Thuê</Button>
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      )}
    </Container>
        </>
        

        
    )
}
export default Home;