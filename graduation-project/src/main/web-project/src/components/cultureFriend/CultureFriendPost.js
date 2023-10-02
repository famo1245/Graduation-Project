import React, { useState, useEffect } from "react";
import styles from "./CultureFriendPost.module.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

function CultureFriendPost() {
  const { id } = useParams();
  const { state } = useLocation();
  //   console.log(location);
  const navigate = useNavigate();

  const onGoReviewBoard = (e) => {
    e.preventDefault();
    const form = document.querySelector("#cultureFriendForm");
    const formData = new FormData(form);
    const contents = {};
    formData.forEach((value, key) => {
      contents[key] = value;
    });
    const friendContent = document.querySelector("#cultureFriendContent");
    contents.friendContents = friendContent.value;
    axios
      .post(`/api//chats/create/${state.id}?userId=${sessionStorage.getItem("userId")}`, { chatRoomDTO: contents })
      .then((res) => navigate(`/CultureFriend`));
  };

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
              <div className={styles.text_detail}>상세설정</div>
              <div className={styles.text}>나이대 | </div>
              <div className={styles.text}>성별 | </div>
              <div className={styles.text}>관람일자 | </div>
            </div>
            <div className={styles.inner_content2}>
              <form name="profile" target="" encType="" id="cultureFriendForm">
                <div className={styles.text} id={styles.age}>
                  <select name="availableAgeRange">
                    <option value="10~19">10대</option>
                    <option value="20~29">20대</option>
                    <option value="30~39">30대</option>
                    <option value="40~49">40대</option>
                    <option value="50~59">50대</option>
                    <option value="60~69">60대</option>
                    <option value="70~79">70대</option>
                    <option value="80~89">80대</option>
                    <option value="90~99">90대</option>
                    <option value="100~109">100대</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.sex}>
                  <select name="gender">
                    <option value="상관없음">상관없음</option>
                    <option value="male">남</option>
                    <option value="female">여</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.join_date_div}>
                  <input className={styles.join_date} type="date" name="meetDate" />
                </div>
              </form>
            </div>
          </div>
          <div className={styles.footer_content}>
            <form action="" method="post" target="">
              <textarea
                name=""
                id="cultureFriendContent"
                cols="30"
                rows="5"
                placeholder="글 작성 공간 간단한 내용 작성"
              ></textarea>
            </form>
          </div>
          <div className={styles.post_button}>
            <button className={styles.text_link} type="submit" form="cultureFriendForm" onClick={onGoReviewBoard}>
              작성완료
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendPost;
