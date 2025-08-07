import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { useContext, useEffect, useState } from "react";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import { useNavigate } from "react-router-dom";

const MyMaintenanceSchedule = () => {
  const [user] = useContext(MyUserContext);
  const [schedules, setSchedules] = useState([]);
  const [statusFilter, setStatusFilter] = useState("in_completed");
  const navigate = useNavigate();


  dayjs.extend(customParseFormat);

  useEffect(() => {
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

    // ✅ Kiểm tra ngày thực hiện
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


  return (
    <div className="min-h-screen bg-white text-black flex items-center justify-center px-4">
      <div className="w-full max-w-6xl bg-white border rounded-xl shadow p-6">
        <h2 className="text-2xl font-bold mb-4 text-center">🛠️ Lịch bảo trì của tôi</h2>

        <div className="flex justify-center space-x-4 mb-6">
          <button
            className={`px-4 py-2 rounded-md border font-medium ${statusFilter === "in_completed"
              ? "border-black bg-gray-200"
              : "border-gray-400 bg-white hover:bg-gray-100"
              }`}
            onClick={() => setStatusFilter("in_completed")}
          >
            ⏳ Chưa hoàn thành
          </button>
          <button
            className={`px-4 py-2 rounded-md border font-medium ${statusFilter === "in_progress"
              ? "border-black bg-gray-200"
              : "border-gray-400 bg-white hover:bg-gray-100"
              }`}
            onClick={() => setStatusFilter("in_progress")}
          >
            Đang thực hiện
          </button>
          <button
            className={`px-4 py-2 rounded-md border font-medium ${statusFilter === "completed"
              ? "border-black bg-gray-200"
              : "border-gray-400 bg-white hover:bg-gray-100"
              }`}
            onClick={() => setStatusFilter("completed")}
          >
            ✅ Đã hoàn thành
          </button>
        </div>

        {schedules.length === 0 ? (
          <p className="text-center text-gray-500 italic">Không có lịch nào.</p>
        ) : (
          <div className="overflow-x-auto border border-gray-200 rounded-md">
            <table className="min-w-full text-sm text-black bg-white">
              <thead className="bg-gray-100 border-b">
                <tr>
                  <th className="px-4 py-3 text-left border-r border-gray-200">#</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Thiết bị</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Ngày bảo trì</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Trạng thái</th>
                  {["in_completed", "in_progress"].includes(statusFilter) && (
                    <th className="px-4 py-3 text-left">Hành động</th>
                  )}
                </tr>
              </thead>
              <tbody>
                {schedules.map((s, idx) => (
                  <tr key={s.id} className="border-t hover:bg-gray-50">
                    <td className="px-3 py-2">{idx + 1}</td>
                    <td className="px-3 py-2">{s.deviceName}</td>
                    <td className="px-3 py-2">
                      {dayjs(s.startDate, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY")}
                    </td>
                    <td className="px-3 py-2">
                      <span
                        className={`inline-block px-3 py-1 rounded-full text-xs font-semibold ${s.progress === "completed"
                          ? "bg-green-100 text-green-800"
                          : s.progress === "in_progress"
                            ? "bg-yellow-100 text-yellow-800"
                            : "bg-gray-200 text-gray-700"
                          }`}
                      >
                        {s.progress === "completed"
                          ? "Đã hoàn thành"
                          : s.progress === "in_progress"
                            ? "Đang thực hiện"
                            : "Chưa bắt đầu"}
                      </span>
                    </td>
                    {["in_completed", "in_progress"].includes(statusFilter) && (
                      <td className="px-4 py-3">
                        <button
                          className={`px-3 py-1 rounded border text-sm font-medium ${s.progress === "in_completed"
                            ? "bg-yellow-400 text-black hover:bg-yellow-500 border-yellow-500"
                            : "bg-green-500 text-white hover:bg-green-600 border-green-600"
                            }`}
                          onClick={() => updateProgress(s)}
                        >
                          {s.progress === "in_completed" ? "Bắt đầu" : "Hoàn thành"}
                        </button>

                        {s.progress === "in_progress" && (
                          <button
                            className="px-3 py-1 rounded border text-sm font-medium bg-blue-500 text-white hover:bg-blue-600 border-blue-600"
                            onClick={() => navigate(`/my-maintenance-report/${s.id}`)}
                          >
                            Chỉnh sửa báo cáo
                          </button>
                        )}
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default MyMaintenanceSchedule;
