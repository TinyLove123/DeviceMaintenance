import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL="http://localhost:8080/ManageDeviceMaintenance/api/";


export const endpoints={
    'provinces':'/provinces',
    'wards':(id)=>`/provinces/${id}/wards`,
    'login':'/login',
    'categories':'/categories',
    'devices':'/devices',
    'register': '/users',
    'profile':'/secure/profile',
    'deviceDetail': (id) => `devices/${id}`,
    'myRentedDevice':'secure/my-rented-devices',
    // 'rentedDevice': (id) => `secure/devices/${id}/rented-device`,
    // 'rentedDeviceDetail': (id,rentedId) => `secure/devices/${id}/rented-device/${rentedId}/`,
    // 'rentedDevice': (id) => `/secure/my-rented-devices/${id}/detail-device`,
    'rentedDeviceDetail': (id) => `/secure/my-rented-devices/${id}/detail-device`,
    'rentedDevice': (id) => `/secure/my-rented-devices/${id}/detail-device`,
    'rentDeviceRequest': (id) => `/secure/devices/${id}/rented-device`,
    'myMaintenanceSchedules': 'secure/persional-maintenance-schedule/',
    'maintenanceScheduleDetail':(id) => `/secure/persional-maintenance-schedule/${id}/detail-maintenance-schedule`,
    'reportRentedDevice':(id)=>`/secure/my-rented-devices/${id}/add-report`,
    'myReportHandle':'/secure/my-report-handle',
    'myReportHandleDetail':(id)=>`/secure/my-report-handle/${id}/incident-detail`,
    'updateIncidentStatus':(id)=>`/secure/my-report-handle/${id}/incident-update-status`,
    'addRepairByIncident':(id)=> `/secure/my-report-handle/${id}/add-repair`,
    // 'addRepairDetail':(id)=> `secure/report-handle/${id}/add-repair-detail`,
    'getDetailRepair':(id)=> `/secure/my-report-handle/${id}/repair-detail`,
    'getRepairCost':(id)=>`/secure/${id}/repair-cost`,
    'addRepairDetail':(id)=>`/secure/report-handle/${id}/add-repair-detail`,
    'deleteRepairDetail':(id)=>`/secure/my-report-handle/${id}/delete-repair-detail`,
    'myMaintenanceReport':(id) => `/secure/persional-maintenance-schedule/${id}/detail-maintenance-report`,
    'addIncidentByMaintenanceSchedule':(id)=>`/secure/persional-maintenance-schedule/maintenance-schedule/${id}/add-link`,
    'getMyIncidentReport':`/secure/my-incident`,
    'repairDetail':(id)=>`/secure/my-incident/${id}/detail`

}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL: BASE_URL
})