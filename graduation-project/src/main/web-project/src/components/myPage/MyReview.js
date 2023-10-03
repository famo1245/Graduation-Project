import axios from "axios";
import { Link, useNavigate, useLocation } from "react-router-dom";
import React, { Component, useEffect, useState } from "react";
import styles from "./MyReview.module.css";

function MyReview(props) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const info = location.state;

  return (
    <div className={styles.body_inner_bottom}>
      <div>
        <div>
          <h4>내가 작성한 리뷰 글</h4>
        </div>
        <div className={styles.home_upper}>
          <div className={styles.home_upper_content}>
            {props.reviews.map((review) => {
              const url = `/ReviewDetail/${review.id}`;
              return (
                <div className={styles.box}>
                  <div key={review.id}>
                    <Link to={url}>
                      <img src={review.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                  <div className={styles.title}>{review.reviewTitle}</div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyReview;
