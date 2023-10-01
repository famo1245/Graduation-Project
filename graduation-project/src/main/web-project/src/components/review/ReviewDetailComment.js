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
    if (value === "") {
      e.preventDefault();
      addList(
        ";;;;;;;;;;;;;;;;;;;;;; 빈 댓글을 제출하였습니다. 클릭 후 삭제 요망"
      );
      setValue("");
    } else {
      e.preventDefault();
      addList(value);
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
            placeholder="댓글 달기..."
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
