import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import { useContext, useEffect, useState } from "react";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Context";

const MyMaintenanceSchedule = () => {
  const [user] = useContext(MyUserContext);
  const [schedules, setSchedules] = useState([]);
  const [statusFilter, setStatusFilter] = useState("in_completed"); // Mặc định: chưa hoàn thành

 

  // Nếu cần dùng định dạng cụ thể:
  
  dayjs.extend(customParseFormat);

  useEffect(() => {
    const loadSchedules = async () => {
      try {
        let res = await authApis().get(endpoints.myMaintenanceSchedules);
  
        let filtered = res.data
          .filter(s => s.progress?.toLowerCase() === statusFilter.toLowerCase())
          .sort((a, b) => new Date(a.startDate) - new Date(b.startDate));
  
        setSchedules(filtered); // ❗️ Thiết yếu!
      } catch (err) {
        console.error(err);
      }
    };
  
    if (user) loadSchedules();
  }, [user, statusFilter]);
  

  const updateProgress = async (scheduleId) => {
    if (!window.confirm("Xác nhận đã hoàn thành bảo trì?")) return;

    try {
      let  res =await authApis().put(endpoints.updateScheduleStatus(scheduleId), {
        progress: "completed"
      });

      // Reload danh sách
      setSchedules(prev => prev.filter(s => s.id !== scheduleId));
    } catch (err) {
      console.error("Lỗi cập nhật tiến trình:", err);
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Lịch bảo trì của tôi</h2>

      <div className="mb-4">
        <button
          className={`px-4 py-2 mr-2 rounded ${statusFilter === "in_completed" ? "bg-blue-500 text-white" : "bg-gray-200"}`}
          onClick={() => setStatusFilter("in_completed")}
        >
          Chưa hoàn thành
        </button>
        <button
          className={`px-4 py-2 rounded ${statusFilter === "completed" ? "bg-green-500 text-white" : "bg-gray-200"}`}
          onClick={() => setStatusFilter("completed")}
        >
          Đã hoàn thành
        </button>
      </div>

      {schedules.length === 0 ? (
        <p>Không có lịch nào phù hợp.</p>
      ) : (
        <ul className="space-y-4">
          {schedules.map((s) => (
            <li key={s.id} className="border p-4 rounded shadow">
              <p><strong>Thiết bị:</strong> {s.deviceName}</p>
              <p><strong>Ngày bảo trì:</strong> {dayjs(s.startDate, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY")}</p>
              <p><strong>Trạng thái:</strong> {s.progress === "completed" ? "Đã hoàn thành" : "Đang tiến hành"}</p>

              {s.progress === "in_completed" && (
                <button
                  className="mt-2 px-4 py-2 bg-green-600 text-white rounded hover:bg-green-700"
                  onClick={() => updateProgress(s.id)}
                >
                  Đánh dấu hoàn thành
                </button>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MyMaintenanceSchedule;
