import { useEffect, useState } from "react";
import Apis, { endpoints } from "../configs/Apis";

const LocationSelector = ({ onLocationChange }) => {
    const [provinces, setProvinces] = useState([]);
    const [wards, setWards] = useState([]);
    const [selectedProvince, setSelectedProvince] = useState("");
    const [selectedWard, setSelectedWard] = useState("");

    useEffect(() => {
        const fetchProvinces = async () => {
            try {
                const res = await Apis.get("/provinces");
                setProvinces(res.data);
            } catch (err) {
                console.error("Lỗi khi load provinces:", err);
            }
        };

        fetchProvinces();
    }, []);

    useEffect(() => {
        const fetchWards = async () => {
            if (!selectedProvince) return;

            try {
                const res = await Apis.get(endpoints['wards'](selectedProvince));
                setWards(res.data);
                setSelectedWard(""); // Reset ward khi chọn tỉnh khác
            } catch (err) {
                console.error("Lỗi khi load wards:", err);
            }
        };

        fetchWards();
    }, [selectedProvince]);

    useEffect(() => {
        onLocationChange({
            provinceId: selectedProvince,
            wardId: selectedWard
        });
    }, [selectedProvince, selectedWard]);

    return (
        <div className="mb-3">
            <label className="form-label">Tỉnh/Thành phố:</label>
            <select
                className="form-select mb-2"
                value={selectedProvince}
                onChange={(e) => setSelectedProvince(e.target.value)}
            >
                <option value="">-- Chọn tỉnh --</option>
                {provinces.map(p => (
                    <option key={p.code} value={p.code}>{p.fullName}</option>
                ))}
            </select>

            <label className="form-label">Phường/Xã:</label>
            <select
                className="form-select"
                value={selectedWard}
                onChange={(e) => setSelectedWard(e.target.value)}
                disabled={!wards.length}
            >
                <option value="">-- Chọn xã --</option>
                {wards.map(w => (
                    <option key={w.code} value={w.code}>{w.fullName}</option>
                ))}
            </select>
        </div>
    );
};

export default LocationSelector;
