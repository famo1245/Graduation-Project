import axios from "axios";
import { Link, useNavigate, useLocation } from "react-router-dom";
import React, { Component, useEffect, useState } from "react";
import styles from "./MyPickCulture.module.css";

const url = "/dataList/OA-15486/S/1/datasetView.do";

function MyPickCulture(dummyData) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const info = location.state;

  useEffect(() => {
    setLoading(true);
    axios.get(`/api/cultures/user/${sessionStorage.getItem("userId")}`).then((res) => {
      setData(res.data.likedCultures);
      setLoading(false);
    });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (!data) return null;

  return (
    <div className={styles.body_inner_bottom}>
      <div>
        <div>
          <h4>내가 찜한 문화</h4>
        </div>
        <div className={styles.home_upper}>
          <div className={styles.home_upper_content}>
            {data.map((culture) => {
              const url = `/munhwaRow/${culture.id}`;
              return (
                <div key={culture.id}>
                  <Link to={url}>
                    <img src={culture.main_img} alt="상세페이지" />
                  </Link>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyPickCulture;
