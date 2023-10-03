import styles from "./CultureFriendContent.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import React, { useState, useEffect } from "react";

function CultureFriendContent(props) {
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const onClickCultureFriendItem = () => {
    axios.get(`/api/chats/${props.roomId}?userId=${sessionStorage.getItem("userId")}`).then((res) => {
      if (res.data.accessible) {
        alert("채팅방에 입장합니다");
        navigate(`/CultureFriendDetail/${props.roomId}`, {
          replace: false,
          state: { ...props, chats: res.data.chats },
        });
        return;
      }

      alert(res.data.message);
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
          <img src={props.cultureImg} />
        </div>
        <div className={styles.content_box}>
          <div className={styles.content_box_inner} id={styles.culture_title}>
            {props.cultureTitle}
          </div>
          <div className={styles.content_box_inner} id={styles.user_name}>
            제목: {props.title}
          </div>
          <div className={styles.content_box_inner} id={styles.date}>
            나이대: {props.availableAgeRange === "any" ? "상관없음" : props.availableAgeRange}
          </div>
          <div className={styles.content_box_inner} id={styles.date}>
            참여 인원: {`${props.participants}/${props.max}`}
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            성별: {props.gender === "any" ? "아무나" : props.gender === "male" ? "남" : "여"}
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            관람 일자: {new Date(props.meetDate).toLocaleDateString()}
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
