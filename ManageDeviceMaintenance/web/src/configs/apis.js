import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL="http://localhost:8080/ManageDeviceMaintenance/api/";


export const endpoints={
    'login':'/login',
    'categories':'/categories',
    'devices':'/devices',
    'register': '/users',
    'profile':'/secure/profile',
    'rented_devices':'/secure/rented_devices',
    'maintenance_schedule':'/secure/maintenance_schedule',
    'repair_schedule':'/secure/repair_schedule',
    'deviceDetail':'/deviceDetail',
    'deviceDetail': (id) => `/devices/${id}`

}

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL:BASE_URL
})