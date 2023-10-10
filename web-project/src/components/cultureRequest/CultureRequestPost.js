import React, { useState, useEffect } from "react";
import styles from "./CultureRequestPost.module.css";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

function CultureRequestPost() {
  const { id } = useParams();
  const { state } = useLocation();
  const navigate = useNavigate();

  const onGoReviewBoard = (e) => {
    e.preventDefault();
    const form = document.querySelector("#cultureRequestForm");
    const formData = new FormData(form);
    const contents = {};
    formData.forEach((value, key) => {
      contents[key] = value;
    });
    const requestContents = document.querySelector("#request-contents");
    contents.contents = requestContents.value;
    axios.post(`/api/culture-request/write?userId=${sessionStorage.getItem("userId")}`, contents).then((res) =>
      navigate(`/CultureRequest`, {
        replace: true,
      })
    );
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              문화 요청 글 작성
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.body_content}>
              <form name="profile" target="" encType="" id="cultureRequestForm">
                <div className={styles.inner_content}>
                  <div className={styles.text}>제목 | </div>
                  <div className={styles.text} id={styles.join_date_div}>
                    <input className={styles.join_date} type="text" name="title" />
                  </div>
                </div>
                <div className={styles.footer_content}>
                  <textarea
                    name=""
                    id="request-contents"
                    cols="30"
                    rows="5"
                    placeholder="글 작성 공간 간단한 내용 작성"
                  ></textarea>
                </div>
              </form>
            </div>
          </div>
          <div className={styles.post_button}>
            <button className={styles.text_link} type="submit" form="cultureRequestForm" onClick={onGoReviewBoard}>
              작성완료
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureRequestPost;
