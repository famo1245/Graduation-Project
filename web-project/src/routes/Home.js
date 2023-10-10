import React, { Component, useEffect, useState } from "react";
import ReactDOM from "react-dom";
import { Link, useNavigate } from "react-router-dom";
import styles from "./Home.module.css";
import PropTypes from "prop-types";
import { ScrollMenu, VisibilityContext } from "react-horizontal-scrolling-menu";
import { LeftArrow, RightArrow } from "../components/horizonScroll/Arrow";
import usePreventBodyScroll from "../components/horizonScroll/UsePreventBodyScroll";
import { type } from "@testing-library/user-event/dist/type";
import axios from "axios";
import { HiPlus } from "react-icons/hi";

const url = "/dataList/OA-15486/S/1/datasetView.do";

function Home(props) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    const userId = sessionStorage.getItem("userId") != null ? parseInt(sessionStorage.getItem("userId")) : -1;
    setIsLogin(sessionStorage.getItem("userId") === null ? false : true);
    axios
      .get(`/api/home?userId=${userId}`)
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  const starCount = (count) => {
    let arr = [];
    for (let i = 0; i < count; i++) {
      arr.push(<img className={styles.star_fill} src="/img/star.png" />);
    }

    return arr;
  };
  const starBase = () => {
    let arr = [];
    for (let i = 0; i < 5; i++) {
      arr.push(<img className={styles.star_empty} src="/img/star (1).png" />);
    }
    return arr;
  };

  const getAgeRange = (ageRange) => {
    switch (ageRange) {
      case "10~19":
        return "10대";
      case "20~29":
        return "20대";
      case "30~39":
        return "30대";
      case "40~49":
        return "40대";
      case "50~59":
        return "50대";
      case "60~69":
        return "60대";
      case "70~79":
        return "70대";
      case "80~89":
        return "80대";
      case "90~99":
        return "90대";
      case "100~109":
        return "100대";
      default:
        return "상관없음";
    }
  };

  const onClickCultureFriendItem = (e, friend) => {
    if (!isLogin) {
      alert("로그인을 해야 이용 가능한 서비스입니다");
      return;
    }
    axios.get(`/api/chats/${friend.roomId}?userId=${sessionStorage.getItem("userId")}`).then((res) => {
      if (res.data.accessible) {
        alert("채팅방에 입장합니다");
        navigate(`/CultureFriendDetail/${friend.roomId}`, {
          replace: false,
          state: { ...friend, chats: res.data.chats },
        });
        return;
      }

      alert(res.data.message);
    });
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  return (
    <div className={styles.home}>
      <div>
        <h4>{isLogin ? data.district : "신규 문화 추천"}</h4>
      </div>
      <div className={styles.home_upper}>
        <div className={styles.home_upper_content}>
          {isLogin
            ? data.guList.map((culture) => {
                const url = `/munhwaRow/${culture.id}`;
                return (
                  <div key={culture.id}>
                    <Link to={url}>
                      <img src={culture.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                );
              })
            : data.recentCultures.map((culture) => {
                const url = `/munhwaRow/${culture.id}`;
                return (
                  <div key={culture.id}>
                    <Link to={url}>
                      <img src={culture.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                );
              })}
        </div>
      </div>
      <div>
        <h4>{isLogin ? data.category + " 문화 생활" : "곧 끝나는 문화 생활"}</h4>
      </div>
      <div className={styles.home_upper}>
        <div className={styles.home_upper_content}>
          {isLogin
            ? data.recommendList.map((culture) => {
                const url = `/munhwaRow/${culture.id}`;
                return (
                  <div key={culture.id}>
                    <Link to={url}>
                      <img src={culture.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                );
              })
            : data.soonEnd.map((culture) => {
                const url = `/munhwaRow/${culture.id}`;
                return (
                  <div key={culture.id}>
                    <Link to={url}>
                      <img src={culture.main_img} alt="상세페이지" />
                    </Link>
                  </div>
                );
              })}
        </div>
      </div>
      <div className={styles.home_lower}>
        <div className={styles.home_lower_content}>
          <div className={styles.lower_content_left}>
            <div className={styles.review_notice_board}>
              <div className={styles.a}>리뷰게시판</div>
              <div className={styles.b}>
                <Link className={styles.see_more} to={`/ReviewBoard`}>
                  <HiPlus />
                </Link>
              </div>
            </div>
            {data.reviews.map((review) => {
              return (
                <div className={styles.content_both_inner}>
                  <div>
                    <img
                      className={styles.review_img}
                      src={review.main_img}
                      onClick={() => {
                        navigate(`/reviewDetail/${review.id}`, {
                          replace: false,
                          state: review,
                        });
                      }}
                    />
                  </div>
                  <div className={styles.inner_content}>
                    <span className={styles.inner_content_title}>
                      {review.reviewTitle}
                      <hr style={{ border: 0 }} />
                    </span>
                    <div className={styles.container_star_ratings}>
                      <span className={styles.star_ratings_title}>평점:</span>
                      <div className={styles.star_ratings}>
                        <div className={styles.star_ratings_fill}>{starCount(parseInt(review.reviewGrade))}</div>
                        <div className={styles.star_ratings_base}>{starBase()}</div>
                      </div>
                    </div>
                    <span>작성자: {review.nickname}</span>
                    <br />
                    <span>작성일자: {new Date(review.reviewDateTime).toLocaleDateString()}</span>
                    <br />
                    <span className={styles.user_short_text}>{review.reviewContents}</span>
                  </div>
                </div>
              );
            })}
          </div>
          <div className={styles.lower_content_right}>
            <div className={styles.culture_friend}>
              <div className={styles.a}>문화친구</div>
              <div className={styles.b}>
                <Link to={`/CultureFriend`} className={styles.see_more}>
                  <HiPlus />
                </Link>
              </div>
            </div>
            {data.friends.map((friend) => {
              return (
                <div className={styles.content_both_inner}>
                  <div>
                    <img
                      className={styles.review_img}
                      src={friend.cultureImg}
                      onClick={(e) => {
                        onClickCultureFriendItem(e, friend);
                      }}
                    />
                  </div>
                  <div
                    className={styles.inner_content}
                    onClick={(e) => {
                      onClickCultureFriendItem(e, friend);
                    }}
                  >
                    <span className={styles.inner_content_title}>
                      {friend.cultureTitle}
                      <hr style={{ border: 0 }} />
                    </span>
                    <span>제목: {friend.title}</span>
                    <br />
                    <span>관람 날짜: {new Date(friend.meetDate).toLocaleDateString()}</span>
                    <br />
                    <span>작성자: {friend.authorNickname}</span>
                    <br />
                    <span>
                      원하는 사람: {friend.gender === "any" ? "아무나" : friend.gender === "male" ? "남" : "여"} /{" "}
                      {getAgeRange(friend.availableAgeRange)}
                    </span>
                    <br />
                    <span>
                      구하는 인원: {Object.keys(friend.participants).length} / {friend.max}
                    </span>
                    <br />
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
