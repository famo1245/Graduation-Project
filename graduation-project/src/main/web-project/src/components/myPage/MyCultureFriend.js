import React, { Component, useEffect, useState } from "react";
import styles from "./MycultureFriend.module.css";
import axios from "axios";
import { Link, useLocation, useNavigate } from "react-router-dom";

function MyCultureFriend() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const info = location.state;

  useEffect(() => {
    setLoading(true);
    axios.get(`/api/chatRooms/user/${sessionStorage.getItem("userId")}`).then((res) => {
      setData(res.data.chatRooms);
      setLoading(false);
    });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (!data) return null;

  return (
    <div className={styles.body_inner_bottom}>
      <div>
        <div>
          <h4>내가 작성한 문화친구 글</h4>
        </div>
        <div className={styles.home_upper}>
          <div className={styles.home_upper_content}>
            {data.map((room) => {
              const url = `/CultureFriend/${room.roomId}`;
              return (
                <div className={styles.box}>
                  <div key={room.roomId}>
                    <Link to={url}>
                      <img src={room.cultureImg} alt="상세페이지" />
                    </Link>
                  </div>
                  <div className={styles.title}>{room.title}</div>
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
