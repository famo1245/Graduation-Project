import React, { useState, useEffect } from "react";
import styles from "./CultureFriendPost.module.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";

function CultureFriendPost() {
  const { id } = useParams();
  const { state } = useLocation();
  //   console.log(location);
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
              [{state.codeName}]{state.title}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.inner_content}>
              hello 여기는 문화친구방 생성하는 곳이에용
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendPost;
