import { useContext, useEffect, useState } from "react";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";
import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { CheckCircle, Clock } from "lucide-react";

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

  const updateProgress = async (scheduleId) => {
    if (!window.confirm("Xác nhận đã hoàn thành bảo trì?")) return;

    try {
      await authApis().put(endpoints.updateScheduleStatus(scheduleId), {
        progress: "completed"
      });

      setSchedules(prev => prev.filter(s => s.id !== scheduleId));
    } catch (err) {
      console.error("Lỗi cập nhật tiến trình:", err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 text-white flex items-center justify-center px-4">
  <div className="w-full max-w-5xl bg-gray-800 p-6 rounded-xl shadow-lg border border-gray-600">
    <h2 className="text-2xl font-bold mb-6 text-center border-b border-gray-600 pb-2">
      🛠️ Lịch bảo trì của tôi
    </h2>

    <div className="flex justify-center space-x-4 mb-6">
      <button
        className={`px-4 py-2 rounded-md border font-medium shadow-sm ${
          statusFilter === "in_completed"
            ? "bg-blue-600 border-blue-700 text-white"
            : "bg-gray-700 border-gray-600 text-gray-300 hover:bg-gray-600"
        }`}
        onClick={() => setStatusFilter("in_completed")}
      >
        ⏳ Chưa hoàn thành
      </button>
      <button
        className={`px-4 py-2 rounded-md border font-medium shadow-sm ${
          statusFilter === "completed"
            ? "bg-green-600 border-green-700 text-white"
            : "bg-gray-700 border-gray-600 text-gray-300 hover:bg-gray-600"
        }`}
        onClick={() => setStatusFilter("completed")}
      >
        ✅ Đã hoàn thành
      </button>
    </div>

    {schedules.length === 0 ? (
      <p className="text-center text-gray-400 italic">Không có lịch nào phù hợp.</p>
    ) : (
      <div className="overflow-x-auto rounded-md border border-gray-700">
        <table className="min-w-full bg-gray-800 text-white">
          <thead className="bg-gray-700 text-gray-200 border-b border-gray-600">
            <tr>
              <th className="px-4 py-3 text-left border-r border-gray-600">#</th>
              <th className="px-4 py-3 text-left border-r border-gray-600">Thiết bị</th>
              <th className="px-4 py-3 text-left border-r border-gray-600">Ngày bảo trì</th>
              <th className="px-4 py-3 text-left border-r border-gray-600">Trạng thái</th>
              {statusFilter === "in_completed" && <th className="px-4 py-3 text-left">Hành động</th>}
            </tr>
          </thead>
          <tbody>
            {schedules.map((s, idx) => (
              <tr key={s.id} className="border-t border-gray-700 hover:bg-gray-700 transition">
                <td className="px-4 py-3 border-r border-gray-700">{idx + 1}</td>
                <td className="px-4 py-3 border-r border-gray-700">{s.deviceName}</td>
                <td className="px-4 py-3 border-r border-gray-700">
                  {dayjs(s.startDate, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY")}
                </td>
                <td className="px-4 py-3 border-r border-gray-700">
                  <div className={`inline-flex items-center gap-2 px-2 py-1 rounded-md font-medium text-sm ${
                    s.progress === "completed" ? "bg-green-700 text-green-100" : "bg-yellow-700 text-yellow-100"
                  }`}>
                    {s.progress === "completed" ? <CheckCircle className="w-4 h-4" /> : <Clock className="w-4 h-4" />}
                    {s.progress === "completed" ? "Đã hoàn thành" : "Đang tiến hành"}
                  </div>
                </td>
                {statusFilter === "in_completed" && (
                  <td className="px-4 py-3">
                    <button
                      className="bg-green-600 text-white px-3 py-1 rounded border border-green-700 hover:bg-green-700 transition"
                      onClick={() => updateProgress(s.id)}
                    >
                      Hoàn thành
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
