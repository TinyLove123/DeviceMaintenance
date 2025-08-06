import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { useContext, useEffect, useState } from "react";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyMaintenanceSchedule = () => {
  const [user] = useContext(MyUserContext);
  const [schedules, setSchedules] = useState([]);
  const [statusFilter, setStatusFilter] = useState("in_completed");

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
    const { id, progress } = schedule;
    let nextProgress = null;

    if (progress === "in_completed") nextProgress = "in_progress";
    else if (progress === "in_progress") nextProgress = "completed";
    else return;

    if (!window.confirm(`Xác nhận chuyển sang "${nextProgress}"?`)) return;

    try {
      await authApis().post(endpoints.maintenanceScheduleDetail(schedule.id), {
        progress: nextProgress
      });

      setSchedules(prev =>
        prev.map(s => (s.id === id ? { ...s, progress: nextProgress } : s))
      );
    } catch (err) {
      console.error("Lỗi cập nhật tiến trình:", err);
    }
  };

  return (
    <div className="min-h-screen bg-white text-black flex items-center justify-center px-4">
      <div className="w-full max-w-6xl bg-white border rounded-xl shadow p-6">
        <h2 className="text-2xl font-bold mb-4 text-center">🛠️ Lịch bảo trì của tôi</h2>

        <div className="flex justify-center mb-4 space-x-2">
          {["in_completed", "in_progress", "completed"].map(status => (
            <button
              key={status}
              className={`px-4 py-2 rounded-md border font-medium ${
                statusFilter === status
                  ? "border-black bg-gray-200"
                  : "border-gray-400 bg-white hover:bg-gray-100"
              }`}
              onClick={() => setStatusFilter(status)}
            >
              {status === "in_completed" && "⏳ Chưa hoàn thành"}
              {status === "in_progress" && "🚧 Đang thực hiện"}
              {status === "completed" && "✅ Đã hoàn thành"}
            </button>
          ))}
        </div>

        {schedules.length === 0 ? (
          <p className="text-center text-gray-500 italic">Không có lịch nào.</p>
        ) : (
          <div className="overflow-x-auto border border-gray-200 rounded-md">
            <table className="min-w-full text-sm text-black bg-white">
              <thead className="bg-gray-100 border-b">
                <tr>
                  <th className="px-3 py-2 text-left">#</th>
                  <th className="px-3 py-2 text-left">Thiết bị</th>
                  <th className="px-3 py-2 text-left">Ngày bảo trì</th>
                  <th className="px-3 py-2 text-left">Trạng thái</th>
                  {statusFilter === "in_completed" && (
                    <th className="px-3 py-2 text-left">Hành động</th>
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
                        className={`inline-block px-3 py-1 rounded-full text-xs font-semibold ${
                          s.progress === "completed"
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
                    {statusFilter === "in_completed" && (
                      <td className="px-3 py-2">
                        <button
                          onClick={() => updateProgress(s)}
                          disabled={s.progress === "completed"}
                          className={`px-3 py-1 text-xs font-semibold rounded border ${
                            s.progress === "in_completed"
                              ? "bg-yellow-400 text-black border-yellow-500 hover:bg-yellow-500"
                              : "bg-green-500 text-white border-green-600 hover:bg-green-600"
                          }`}
                        >
                          {s.progress === "in_completed" ? "Bắt đầu" : "Hoàn thành"}
                        </button>
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
