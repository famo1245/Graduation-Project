import styles from "./CultureFriendContent.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useState, useEffect } from "react";

function CultureFriendContent(props) {
  const [like, setLike] = useState(true);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const onClickCultureFriendItem = () => {
    navigate(`/CultureFriendDetail/${props.id}`, {
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
        <div className={styles.content_img}>
          <img src={props.poster} />
        </div>
        <div className={styles.content_box}>
          <div className={styles.content_box_inner} id={styles.culture_title}>
            [{props.codeName}]{props.title}
          </div>
          <div className={styles.content_box_inner} id={styles.user_name}>
            문화친구제목: {props.place}
          </div>
          <div className={styles.content_box_inner} id={styles.date}>
            나이대: 상관없음 / 10-20
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            성별: 남
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            날짜: {props.date}
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            간단한 작성 내용~~~~~
          </div>
          <div
            className={isLogin ? styles.content_box_inner : styles.none_content}
            id={isLogin ? styles.together : styles.none_content}
            onClick={onClickCultureFriendItem}
          >
            <div>같이보기</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendContent;
