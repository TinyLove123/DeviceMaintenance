import moment from "moment";
import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyReportHandle = () => {
    const [user] = useContext(MyUserContext);
    const [incidentHandles, setIncidentHandles] = useState([]);

    useEffect(() => {
        const loadIncidentHandles = async () => {
            try {
                let res = await authApis().get(endpoints.myReportHandle);
                setIncidentHandles(res.data);
            } catch (err) {
                console.error("Lỗi khi tải danh sách xử lý:", err);
            }
        };

        loadIncidentHandles();
    }, []);

    const handleViewDetails = (incident) => {
        // Ví dụ hiện alert, bạn có thể mở modal hoặc chuyển trang chi tiết
        alert(`Chi tiết sự cố:\n\nTiêu đề: ${incident.title}\nMô tả: ${incident.detailDescribe}`);
    };

    return (
        <div className="container mt-4">
            <h1 className="mb-4">Danh sách xử lý sự cố</h1>

            {incidentHandles.length === 0 ? (
                <p>Không có sự cố nào đã xử lý.</p>
            ) : (
                <table className="table table-bordered table-hover">
                    <thead className="table-primary">
                        <tr>
                            <th>Tiêu đề</th>
                            <th>Thiết bị</th>
                            <th>Trạng thái</th>
                            <th>Khẩn cấp</th>
                            <th>Ngày báo cáo</th>
                            <th>Nhân viên xử lý</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        {incidentHandles.map((inc) => (
                            <tr key={inc.id}>
                                <td>{inc.title}</td>
                                <td>{inc.device?.nameDevice}</td>
                                <td>{inc.status}</td>
                                <td>{inc.isEmergency ? "Có" : "Không"}</td>
                                <td>{moment(inc.reportDate).format("DD-MM-YYYY HH:mm")}</td>
                                <td>{inc.employee ? `${inc.employee.firstName} ${inc.employee.lastName}` : ""}</td>
                                <td>
                                    <Link to={`/incident/${inc.id}`} className="btn btn-outline-primary mt-2">
                                        Xem chi tiết
                                    </Link>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default MyReportHandle;
