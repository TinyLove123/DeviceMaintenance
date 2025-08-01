import { useReducer } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Devices from "./components/devices";
import Home from "./components/home";
import Footer from "./components/layout/footer";
import Header from "./components/layout/header";
import Login from "./components/login";
import Profile from "./components/profile";
import { MyUserContext } from "./configs/Context";
import MyUserReducer from "./reducers/MyUserReducer";
import 'bootstrap/dist/css/bootstrap.min.css';
import DeviceDetail from "./components/deviceDetail";

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
        <Route path="/rented_devices" element={<Devices />} />
        <Route path="/maintenance_schedule" element={<Devices />} />
        <Route path="/repair_schedule" element={<Devices />} />
        <Route path="/devices/:id" element={<DeviceDetail />} /> 
      </Routes>
      <Footer />
    </BrowserRouter>
    </MyUserContext.Provider>
  );
};

export default App;