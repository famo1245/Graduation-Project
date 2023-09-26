import React, { useState, useEffect } from "react";
import styles from "./CultureFriendDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";

function CultureFriendDetail() {
  const location = useLocation();
  const reviewInfo = location.state;
  console.log(reviewInfo);
  const navigate = useNavigate();
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
              [{reviewInfo.codeName}]{reviewInfo.title}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.inner_content}>
              여기는 문화친구방에 입장하여 소통하는 곳이에용
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendDetail;
