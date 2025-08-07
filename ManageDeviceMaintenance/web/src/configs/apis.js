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
    'myMaintenanceReport':(id) => `/secure/persional-maintenance-schedule/${id}/detail-maintenance-report`

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