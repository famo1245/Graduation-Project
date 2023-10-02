import React, { Component, useEffect, useState } from "react";
import styles from "./MyCultureFriend.module.css";
import axios from "axios";
import { Link, useLocation, useNavigate } from "react-router-dom";

function MyCultureFriend(dummyData) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const info = location.state;
  console.log(dummyData);

  return (
    <div className={styles.body_inner_bottom}>
      <div>
        <div>
          <h4>내가 찜한 문화</h4>
        </div>
        <div className={styles.home_upper}>
          <div className={styles.home_upper_content}>
            {dummyData.dummyData.guList.map((culture) => {
              const url = `/munhwaRow/${culture.id}`;
              return (
                <div className={styles.box}>
                  <div key={culture.id}>
                    <Link to={url}>
                      <img src={culture.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                  <div className={styles.title}>{culture.title}</div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyCultureFriend;
