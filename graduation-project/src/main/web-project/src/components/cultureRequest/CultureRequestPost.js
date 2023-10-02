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
    const form = document.querySelector("#cultureFriendForm");
    const formData = new FormData(form);
    const contents = {};
    formData.forEach((value, key) => {
      contents[key] = value;
    });
    const friendContent = document.querySelector("#cultureFriendContent");
    contents.friendContents = friendContent.value;
    contents.max = 4;
    axios
      .post(
        `/api/chats/create/${state.id}?userId=${sessionStorage.getItem(
          "userId"
        )}`,
        contents
      )
      .then((res) =>
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
            {/* <h1 className={styles.title2}>
              여기는 "문화요청"을 작성하는 페이지야
              <hr style={{ border: 0 }} />
            </h1> */}
          </div>
          <div className={styles.body}>
            <div className={styles.body_content}>
              <form name="profile" target="" encType="" id="cultureRequestForm">
                <div className={styles.inner_content}>
                  <div className={styles.text}>문화 이름 | </div>
                  <div className={styles.text} id={styles.join_date_div}>
                    <input
                      className={styles.join_date}
                      type="text"
                      name="meetDate"
                    />
                  </div>
                </div>
                <div className={styles.footer_content}>
                  <textarea
                    name=""
                    id=""
                    cols="30"
                    rows="5"
                    placeholder="글 작성 공간 간단한 내용 작성"
                  ></textarea>
                </div>
              </form>
            </div>
          </div>
          <div className={styles.post_button}>
            <button
              className={styles.text_link}
              type="submit"
              form="cultureRequestForm"
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

export default CultureRequestPost;
