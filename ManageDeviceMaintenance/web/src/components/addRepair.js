const addRepair =({ IncidentId, onClose })=>{


    const handleSubmit = async () => {
            e.preventDefault();
    
            try {
                await authApis().post(endpoints['addRepairByIncident'](IncidentId), {
                    title: title.trim(),
                    detailDescribe: detailDescribe.trim(),
                    isEmergency: isEmergency
                });
    
                alert("Báo cáo sự cố thành công!");
                onClose(); // ✅ đóng modal
            } catch (err) {
                const msg = err?.response?.data?.error || "Lỗi gửi báo cáo!";
                alert(msg);
            }
        };


}
export default addRepair;