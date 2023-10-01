import React, { useState, useEffect } from "react";
import styles from "./CultureFriendPost.module.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";

function CultureFriendPost() {
  const { id } = useParams();
  const { state } = useLocation();
  //   console.log(location);
  const navigate = useNavigate();

  const onGoReviewBoard = () => {
    // const form = document.querySelector("#");
    // console.log(form);
    // const formData = new FormData(form);
    // const contents = {};
    // formData.forEach((value, key) => {
    //   contents[key] = value;
    // });
    // axios.post("", contents);
    navigate(`/CultureFriend`);
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
                  <select name="age">
                    <option value="10">10대</option>
                    <option value="20">20대</option>
                    <option value="30">30대</option>
                    <option value="40">40대</option>
                    <option value="50">50대</option>
                    <option value="60">60대</option>
                    <option value="70">70대</option>
                    <option value="80">80대</option>
                    <option value="90">90대</option>
                    <option value="100">100대</option>
                  </select>
                  <span>~</span>
                  <select name="age2">
                    <option value="10">10대</option>
                    <option value="20">20대</option>
                    <option value="30">30대</option>
                    <option value="40">40대</option>
                    <option value="50">50대</option>
                    <option value="60">60대</option>
                    <option value="70">70대</option>
                    <option value="80">80대</option>
                    <option value="90">90대</option>
                    <option value="100">100대</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.sex}>
                  <select name="sex">
                    <option value="상관없음">상관없음</option>
                    <option value="남">남</option>
                    <option value="여">여</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.join_date_div}>
                  <input className={styles.join_date} type="text" name="date" />
                </div>
              </form>
            </div>
          </div>
          <div className={styles.footer_content}>
            <form
              name=""
              id="cultureFriendForm"
              action=""
              method="post"
              target=""
            >
              <textarea
                name=""
                id="cultureFriendForm"
                cols="30"
                rows="5"
                placeholder="글 작성 공간 간단한 내용 작성"
              ></textarea>
            </form>
          </div>
          <div className={styles.post_button}>
            <button
              className={styles.text_link}
              type="submit"
              form="cultureFriendForm"
              onClick={onGoReviewBoard}
            >
              작성완료
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendPost;
