import React from "react";
import styles from "./ReviewContent.module.css";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function ReviewContent(props) {
  const navigate = useNavigate();
  const [like, setLike] = useState(true);
  const [likeCount, setLikeCount] = useState(0);
  const [isLogin, setIsLogin] = useState(false);

  //   useEffect(async () => {
  //     const fetchData = async () => {
  //       const res = await axios.get("");
  //       if (res.data.type === "liked") setLike(true);
  //     };
  //     fetchData();
  //   }, []);

  const toggleLike = async () => {
    // const res = await axios.post(
    //   ""
    // ); /* [POST] 사용자가 좋아요를 누름 -> DB 갱신 */
    axios.get(`/api/review/jim/${props.id}?userId=${sessionStorage.getItem("userId")}`);
    setLike(!like);
    if (like === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };

  const onClickReviewItem = () => {
    navigate(`/reviewDetail/${props.id}`, {
      replace: false,
      state: props,
    });
  };

  useEffect(() => {
    setLikeCount(props.likeCount);
    if (props.likeId.includes(sessionStorage.getItem("userId"))) {
      setLike(false);
    }
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.content_img}>
          <img src={props.poster} onClick={onClickReviewItem} />
        </div>
        <div className={styles.content_box}>
          <div className={styles.content_box_inner} id={styles.culture_title}>
            {props.title}
          </div>
          <div className={styles.content_box_inner} id={styles.user_name}>
            작성자: {props.nickname}
          </div>
          <div className={styles.content_box_inner} id={styles.date}>
            작성 날짜: {new Date(props.date).toLocaleDateString()}
          </div>
          <div className={styles.content_box_inner} id={styles.user_content}>
            내용: {props.contents}
          </div>
          <div
            className={isLogin ? styles.content_box_inner : styles.none_content}
            id={isLogin ? styles.like : styles.none_content}
          >
            <img src={like ? "img/heart.png" : "img/heart (2).png"} onClick={toggleLike} />
            <span>{likeCount}</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReviewContent;
