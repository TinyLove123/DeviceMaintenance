import { useReducer } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import DeviceDetail from "./components/deviceDetail";
import Devices from "./components/devices";
import Home from "./components/home";
import Footer from "./components/layout/footer";
import Header from "./components/layout/header";
import Login from "./components/login";
import MyMaintenanceSchedule from "./components/myMaintenanceSchedule";
import MyRentedDevice from "./components/myRentedDevice";
import Profile from "./components/profile";
import { MyUserContext } from "./configs/Context";
import MyUserReducer from "./reducers/MyUserReducer";

const App = () => {

  let [user, dispatch] = useReducer(MyUserReducer, null);
  return (
    <MyUserContext.Provider value={[user, dispatch]}>
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/devices" element={<Devices />} />
        <Route path="/devices/:id" element={<DeviceDetail />} /> 
        <Route path="/my-rented-devices" element={<MyRentedDevice/>} />
        <Route path="/my-maintenance-schedule" element={<MyMaintenanceSchedule/>} />
        <Route path="/my-rented-device-detail/:id" element={<MyRentedDeviceDetail/>} />
      </Routes>
      <Footer />
    </BrowserRouter>
    </MyUserContext.Provider>
  );
};

export default App;