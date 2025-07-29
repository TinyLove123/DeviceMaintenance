import axios from "axios";

const BASE_URL="http://localhost:8080/ManageDeviceMaintenance/";


export const endpoint={
    'login':'api/login',
    'categories':'/categories',
    'devices':'/devices',
}

export default axios.create({
    baseURL:BASE_URL
})