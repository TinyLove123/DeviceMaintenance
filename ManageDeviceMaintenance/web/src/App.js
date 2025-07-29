import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./components/home";
import Footer from "./components/layout/footer";
import Header from "./components/layout/header";

  const App=()=>{
    <BrowserRouter>
    <Header/>
    <Routes>
      <Route path="/" element={<Home/>}/>
    </Routes>

    <Footer/>
    </BrowserRouter>
  }

  export default App;