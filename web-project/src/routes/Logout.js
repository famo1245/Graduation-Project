import React from "react";
import styles from "./Logout.module.css";
import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();
  const onClick = () => {
    sessionStorage.removeItem("userId");
    window.location.href = "http://localhost:3000";
  };

  const onClick2 = () => {
    navigate("/");
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              로그아웃 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.a}>
              <div className={styles.logout_text}>로그아웃 하시겠습니까?</div>
              <div className={styles.logout}>
                <div className={styles.logout_ok} onClick={onClick}>
                  예
                </div>
                <div className={styles.logout_no} onClick={onClick2}>
                  아니오
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Logout;
