import React, { useState, useEffect } from "react";
import styles from "./CultureRequestDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";

function CultureRequestDetail(props) {
  const location = useLocation();
  const reviewInfo = location.state;
  console.log(reviewInfo);
  const [isLogin, setIsLogin] = useState(false);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  console.log(reviewInfo);

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              문화친구
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {reviewInfo.cultureTitle}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.body_left}>
              <div className={styles.body_left_top}>
                <div className={styles.left_top_img}>
                  <img src={reviewInfo.cultureImg} alt="" />
                </div>
                <div className={styles.left_top_content}>
                  <div className={styles.inner_title}>
                    {reviewInfo.cultureTitle}
                  </div>
                  <div id={styles.inner_title}>
                    <div>{reviewInfo.title}</div>
                  </div>
                  <div className={styles.info}>
                    <div>나이대 | {reviewInfo.availableAgeRange}</div>
                  </div>
                  <div className={styles.info}>
                    <div>성별 | {reviewInfo.gender}</div>
                  </div>
                  <div className={styles.info}>
                    <div>날짜 | {reviewInfo.meetDate}</div>
                  </div>
                </div>
              </div>
              <div className={styles.body_left_bottom}>
                <span>
                  사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                  사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                  사용자가 적은 간단한 내용이 들어올 위치사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureRequestDetail;
