import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { authApis, endpoints } from '../configs/Apis';
import moment from 'moment';
import { useNavigate } from "react-router-dom";

const MyMaintenanceReport = () => {
    const { id } = useParams(); // id của lịch bảo trì
    const [report, setReport] = useState({
        description: '',
        price: '',
        maintenanceRate: '',
        reportDate: '',
    });
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const navigate = useNavigate();


    // Gọi API để lấy báo cáo nếu có
    useEffect(() => {
        const fetchReport = async () => {
            try {
                const res = await authApis().get(endpoints.myMaintenanceReport(id));
                setReport(res.data); // Nếu đã có báo cáo thì đổ vào form

            } catch (error) {
                if (error.response && error.response.status === 404) {
                    setMessage('Chưa có báo cáo. Bạn có thể tạo mới.');
                } else if (error.response && error.response.status === 403) {
                    setMessage('Bạn không có quyền xem báo cáo này.');
                } else {
                    setMessage('Lỗi khi tải báo cáo.');
                }
            } finally {
                setLoading(false);
            }
        };

        fetchReport();
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setReport((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formattedDate = moment().format("DD/MM/YYYY HH:mm:ss");
        console.log({
            description: report.description,
            price: parseFloat(report.price),
            maintenanceRate: report.maintenanceRate,
            reportDate: formattedDate
        });
        try {
            let res = await authApis().post(endpoints.myMaintenanceReport(id), {
                description: report.description,
                price: report.price,
                maintenanceRate: report.maintenanceRate
            });

            setMessage('Cập nhật báo cáo thành công!');
            setReport(res.data);

            setTimeout(() => {
                navigate("/my-maintenance-schedule");
            }, 1500);
        } catch (error) {
            if (error.response && error.response.data && error.response.data.error) {
                setMessage(`Lỗi: ${error.response.data.error}`);
            } else {
                setMessage('Đã xảy ra lỗi khi gửi báo cáo.');
            }
        }
    };

    if (loading) return <p>Đang tải dữ liệu...</p>;

    return (
        <div className="container mt-4">
            <h2>Báo cáo bảo trì</h2>
            {message && <div className="alert alert-info">{message}</div>}

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label>Mô tả công việc</label>
                    <textarea
                        name="description"
                        value={report.description}
                        onChange={handleChange}
                        className="form-control"
                        required
                    />
                </div>

                <div className="mb-3">
                    <label>Chi phí (VNĐ)</label>
                    <input
                        type="number"
                        name="price"
                        value={report.price}
                        onChange={handleChange}
                        className="form-control"
                        required
                        min="0"
                    />
                </div>

                <div className="mb-3">
                    <label>Đánh giá mức độ hoàn thành</label>
                    <select
                        name="maintenanceRate"
                        value={report.maintenanceRate}
                        onChange={handleChange}
                        className="form-control"
                        required
                    >
                        <option value="">-- Chọn đánh giá --</option>
                        <option value="excellent">excellent</option>
                        <option value="major_issues">major_issues</option>
                        <option value="minor_issues">minor_issues</option>
                        <option value="unusable">unusable</option>
                    </select>
                </div>

                <button type="submit" className="btn btn-primary" >
                    Cập nhật báo cáo
                </button>
            </form>
        </div>
    );
};

export default MyMaintenanceReport;
