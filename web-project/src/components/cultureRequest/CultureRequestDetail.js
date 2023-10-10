import React, { useState, useEffect } from "react";
import styles from "./CultureRequestDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";

function CultureRequestDetail(props) {
  const location = useLocation();
  const request = location.state;
  const [isLogin, setIsLogin] = useState(false);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              문화요청
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {request.title} <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.inner_content}>
              <div className={styles.info}>
                <div>작성자 : {request.nickname}</div>
              </div>
            </div>
          </div>
          <div className={styles.lower_content}>
            <div className={styles.lower_content_text}>{`${request.contents}`}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureRequestDetail;
