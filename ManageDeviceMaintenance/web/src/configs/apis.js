import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL="http://localhost:8080/ManageDeviceMaintenance/api/";


export const endpoints={
    'login':'/login',
    'categories':'/categories',
    'devices':'devices',
    'register': '/users',
    'profile':'/secure/profile',
    'deviceDetail': (id) => `devices/${id}`,
    'myRentedDevice':'secure/my-rented-devices',
    'rentedDevice': (id) => `/secure/my-rented-devices/${id}/detail-device`,
    'myMaintenanceSchedules': 'secure/persional-maintenance-schedule/',
    'maintenanceScheduleDetail':(id) => `secure/persional-maintenance-schedule/${id}/detail-maintenance-schedule`

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