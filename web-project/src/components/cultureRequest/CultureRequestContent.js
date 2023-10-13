import styles from "./CultureRequestContent.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useState, useEffect } from "react";

function CultureRequestContent(props) {
  const [like, setLike] = useState(true);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const onClickCultureRequest = () => {
    navigate(`/CultureRequestDetail/${props.id}`, {
      replace: false,
      state: props,
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.content_box}>
          <div className={styles.content_box_inner} id={styles.culture_title} onClick={onClickCultureRequest}>
            제목 : {props.title}
          </div>
          <div className={styles.content_box_inner} id={styles.user_name}>
            요청자 : {props.nickname}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureRequestContent;
