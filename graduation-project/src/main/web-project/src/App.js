import { func } from "prop-types";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./routes/Home";
import Login from "./routes/Login";
import Sign_up from "./routes/Sign_up";
import { Link } from "react-router-dom";
import Index_home from "./pages/Index_home";
import Layout from "./components/layout/Layout";
import Whyso from "./routes/Whyso";

function App(props) {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Layout>
        <Routes>
          <Route path="/" element={<Index_home />} />
          <Route path="/Login" element={<Login />} />
          <Route path="/Sign_up" element={<Sign_up />} />
          <Route path="/Whyso" element={<Whyso />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
