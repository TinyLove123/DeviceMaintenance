import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";

const Home = () => {

    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(true);
    const [q, setQ] = useState();
    const [page, setPage] = useState(1);
    const [params] = useSearchParams();


    const loadDevice = async ()=>{
        let res = await Apis.get(endpoints['devices']);
        setDevices(res.date)
    }

    return (
        <>
            <h1>Home</h1>
        </>
    )
}
export default Home;