import React from "react";
import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import styles from "./ReviewDetailComment.module.css";

function ReviewDetailComment({ addList }) {
  const [value, setValue] = useState("");

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    addList(value);
    setValue("");
  };

  return (
    <li className={styles.comment_form}>
      <form onSubmit={handleSubmit}>
        <span className={styles.ps_box}>
          <input
            type="text"
            className={styles.int}
            placeholder="댓글 달기..."
            onChange={handleChange}
            value={value}
          />
        </span>
        <input type="submit" className={styles.comment_btn} value="댓글등록" />
      </form>
    </li>
  );
}

export default ReviewDetailComment;
