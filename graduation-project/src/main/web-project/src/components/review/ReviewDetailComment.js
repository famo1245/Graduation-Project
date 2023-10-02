import React from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import styles from "./ReviewDetailComment.module.css";

function ReviewDetailComment({ addList }) {
  const [value, setValue] = useState("");
  const [isLogin, setIsLogin] = useState(false);

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (value === "") {
      alert("빈 댓글은 등록할 수 없습니다.");
      return;
    }
    addList(value);
    setValue("");
  };

  return (
    <div className={styles.comment_form}>
      <form onSubmit={handleSubmit}>
        <div className={styles.ps_box}>
          <input type="text" className={styles.int} placeholder="댓글 달기..." onChange={handleChange} value={value} />
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
