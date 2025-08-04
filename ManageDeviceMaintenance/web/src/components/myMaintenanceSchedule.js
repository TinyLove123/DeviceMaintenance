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
  
    if (progress === "in_completed") {
      nextProgress = "in_progress";
    } else if (progress === "in_progress") {
      nextProgress = "completed";
    } else {
      return;
    }
  
    if (!window.confirm(`Xác nhận chuyển trạng thái sang "${nextProgress}"?`)) return;
  
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
      <div className="w-full max-w-5xl p-6 rounded-xl shadow border border-gray-300">
        <h2 className="text-2xl font-bold mb-6 text-center border-b border-gray-300 pb-2">
          🛠️ Lịch bảo trì của tôi
        </h2>

        <div className="flex justify-center space-x-4 mb-6">
          <button
            className={`px-4 py-2 rounded-md border font-medium ${
              statusFilter === "in_completed"
                ? "border-black bg-gray-200"
                : "border-gray-400 bg-white hover:bg-gray-100"
            }`}
            onClick={() => setStatusFilter("in_completed")}
          >
            ⏳ Chưa hoàn thành
          </button>
          <button
            className={`px-4 py-2 rounded-md border font-medium ${
              statusFilter === "in_progress"
                ? "border-black bg-gray-200"
                : "border-gray-400 bg-white hover:bg-gray-100"
            }`}
            onClick={() => setStatusFilter("in_progress")}
          >
            Đang thực hiên
          </button>
          <button
            className={`px-4 py-2 rounded-md border font-medium ${
              statusFilter === "completed"
                ? "border-black bg-gray-200"
                : "border-gray-400 bg-white hover:bg-gray-100"
            }`}
            onClick={() => setStatusFilter("completed")}
          >
            ✅ Đã hoàn thành
          </button>
        </div>

        {schedules.length === 0 ? (
          <p className="text-center text-gray-500 italic">Không có lịch nào phù hợp.</p>
        ) : (
          <div className="overflow-x-auto rounded-md border border-gray-200">
            <table className="min-w-full text-black bg-white">
              <thead className="bg-gray-100 border-b border-gray-200">
                <tr>
                  <th className="px-4 py-3 text-left border-r border-gray-200">#</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Thiết bị</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Ngày bảo trì</th>
                  <th className="px-4 py-3 text-left border-r border-gray-200">Trạng thái</th>
                  {statusFilter === "in_completed" && (
                    <th className="px-4 py-3 text-left">Hành động</th>
                  )}
                </tr>
              </thead>
              <tbody>
                {schedules.map((s, idx) => (
                  <tr key={s.id} className="border-t border-gray-200 hover:bg-gray-50">
                    <td className="px-4 py-3 border-r border-gray-200">{idx + 1}</td>
                    <td className="px-4 py-3 border-r border-gray-200">{s.deviceName}</td>
                    <td className="px-4 py-3 border-r border-gray-200">
                      {dayjs(s.startDate, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY")}
                    </td>
                    <td className="px-4 py-3 border-r border-gray-200">
                      <span
                        className={`inline-block px-3 py-1 rounded-full text-sm font-medium ${
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
                      <td className="px-4 py-3">
                        <button
                          className={`px-3 py-1 rounded border text-sm font-medium ${
                            s.progress === "completed"
                              ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                              : s.progress === "in_completed"
                              ? "bg-yellow-400 text-black hover:bg-yellow-500 border-yellow-500"
                              : "bg-green-500 text-white hover:bg-green-600 border-green-600"
                          }`}
                          onClick={() => updateProgress(s)}
                          disabled={s.progress === "completed"}
                        >
                          {s.progress === "in_completed"
                            ? "Bắt đầu"
                            : s.progress === "in_progress"
                            ? "Hoàn thành"
                            : "Hoàn thành"}
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
