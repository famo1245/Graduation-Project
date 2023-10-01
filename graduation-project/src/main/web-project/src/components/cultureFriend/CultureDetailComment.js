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
    let todayMonth =
      now.getMonth() + 1 > 9 ? now.getMonth() + 1 : "0" + (now.getMonth() + 1);
    let todayDate = now.getDate() > 9 ? now.getDate() : "0" + now.getDate();
    let hours = now.getHours() > 9 ? now.getHours() : "0" + now.getHours();
    let minutes =
      now.getMinutes() > 9 ? now.getMinutes() : "0" + now.getMinutes();

    return (
      todayYear +
      "/" +
      todayMonth +
      "/" +
      todayDate +
      " " +
      hours +
      " : " +
      minutes
    );
  };

  const handleChange = (e) => {
    setValue(e.target.value);
    setTime(todayTime());
  };

  const handleSubmit = (e) => {
    if (!value) {
      e.preventDefault();
      return;
    } else {
      e.preventDefault();
      addList((addList.content = value), (addList.date = time));
      setValue("");
    }
  };

  return (
    <div className={styles.comment_form}>
      <form onSubmit={handleSubmit}>
        <div className={styles.ps_box}>
          <input
            type="text"
            className={styles.int}
            placeholder="입력..."
            onChange={handleChange}
            value={value}
          />
          <button type="submit" className={styles.comment_btn} value="댓글등록">
            {/* <img src="/img/right-chevron.png" id={styles.s_comment_btn} /> */}
            <span id={styles.s_comment_btn}>›</span>
          </button>
        </div>
      </form>
    </div>
  );
}

export default ReviewDetailComment;
