import React from "react";
import styles from "./Logout.module.css";
import { Link } from "react-router-dom";

function Logout() {
  return (
    <div>
      <div>로그아웃 하시겠습니까?</div>
      <div>
        <div>
          <a href="http://localhost:3000"></a>
        </div>
        <div>
          <Link to={`/`}>아니요</Link>
        </div>
      </div>
    </div>
  );
}

export default Logout();
