import Layout from "../components/layout/Layout";
import Home from "../routes/Home";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "../routes/Login";
import Sign_up from "../routes/Sign_up";
import { Link } from "react-router-dom";

function Index_home(props) {
  return (
    <div>
      <Home />
    </div>
  );
}

export default Index_home;
