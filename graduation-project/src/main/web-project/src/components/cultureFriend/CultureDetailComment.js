import React from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import styles from "./CultureDetailComment.module.css";

function ReviewDetailComment({ addList }) {
  const [value, setValue] = useState("");
  const [isLogin, setIsLogin] = useState(false);
  const [time, setTime] = useState();

  const todayTime = () => {
    let now = new Date();
    let todayYear = now.getFullYear();
    let todayMonth = now.getMonth() + 1 > 9 ? now.getMonth() + 1 : "0" + (now.getMonth() + 1);
    let todayDate = now.getDate() > 9 ? now.getDate() : "0" + now.getDate();
    let hours = now.getHours() > 9 ? now.getHours() : "0" + now.getHours();
    let minutes = now.getMinutes() > 9 ? now.getMinutes() : "0" + now.getMinutes();

    return todayYear + "/" + todayMonth + "/" + todayDate + " " + hours + " : " + minutes;
  };

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  const handleSubmit = (e) => {
    if (!value) {
      e.preventDefault();
      return;
    } else {
      e.preventDefault();
      addList((addList.content = value));
      setValue("");
    }
  };

  return (
    <div className={styles.comment_form}>
      <form onSubmit={handleSubmit}>
        <div className={styles.ps_box}>
          <input type="text" className={styles.int} placeholder="입력..." onChange={handleChange} value={value} />
          <button type="submit" className={styles.comment_btn} value="댓글등록">
            {/* <img src="/img/right-chevron.png" id={styles.s_comment_btn} /> */}
            {/* <span id={styles.s_comment_btn}> */}
            {/* › */}
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="23"
              height="20"
              viewBox="0 0 23 20"
              fill="none"
              style={{ marginTop: 4 + "px" }}
            >
              <path d="M0.0109524 20L23 10L0.0109524 0L0 7.77778L16.4286 10L0 12.2222L0.0109524 20Z" fill="#FDFDFD" />
            </svg>
            {/* </span> */}
          </button>
        </div>
      </form>
    </div>
  );
}

export default ReviewDetailComment;
