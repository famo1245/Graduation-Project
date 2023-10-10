import React, { useState, useEffect } from "react";
import styles from "./MyPage.module.css";
import { Link, NavLink } from "react-router-dom";
import MyPickCulture from "./MyPickCulture";
import MyReview from "./MyReview";
import MyCultureFriend from "./MyCultureFriend";
import axios from "axios";

function MyPage() {
  const activeStyle = {
    color: "dodgerblue",
  };

  const [viewPoint, setViewPoint] = useState(0);
  const [buttonColor1, setButtonColor1] = useState(true);
  const [buttonColor2, setButtonColor2] = useState(true);
  const [buttonColor3, setButtonColor3] = useState(true);
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const [dummyData, setDummyData] = useState(null);
  const [myReviews, setMyReviews] = useState([]);
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    setLoading(true);
    const userId = sessionStorage.getItem("userId") != null ? parseInt(sessionStorage.getItem("userId")) : -1;
    setIsLogin(sessionStorage.getItem("userId") === null ? false : true);
    axios
      .get(`/api/home?userId=${userId}`)
      .then((response) => {
        setDummyData(response.data);
      })
      .catch((err) => setError(err));
  }, []);

  useEffect(() => {
    setLoading(true);
    const userId = sessionStorage.getItem("userId");
    axios
      .post("/api/members/info", { userId: userId })
      .then((response) => {
        setData(response.data.myInfo);
        setMyReviews(response.data.myReviews);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  const switchComponent = () => {
    switch (viewPoint) {
      case 1:
        return <MyPickCulture dummyData={dummyData} />;
      case 2:
        return <MyReview reviews={myReviews} />;
      case 3:
        return <MyCultureFriend />;
      default:
        return <></>;
    }
  };

  const getTier = () => {
    let tierName;
    switch (data.tiers) {
      case "BRONZE":
        tierName = "일류";
        break;
      case "SILVER":
        tierName = "절정";
        break;
      case "GOLD":
        tierName = "화경";
        break;
      default:
        tierName = "현경";
        break;
    }

    return tierName;
  };

  const getNeedTierScore = () => {
    let upperScore = 0;
    switch (data.tiers) {
      case "BRONZE":
        upperScore = 300;
        break;
      case "SILVER":
        upperScore = 500;
        break;
      case "GOLD":
        upperScore = 1000;
        break;
      default:
        upperScore = -1;
        break;
    }

    if (upperScore === -1) {
      return "최고 등급 문화친구에요";
    }

    return `다음 등급까지 ${upperScore - data.tierScore} 포인트 필요해요`;
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              마이페이지 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.a}>
              <div className={styles.text}>닉네임 : {data.nickName}</div>
              <div className={styles.text}>
                나이/성별 : {data.age_range}/{data.gender === "male" ? "남" : "여"}
              </div>
              <div className={styles.text}>관심지역 : {data.district}</div>
              <div className={styles.text}>
                문화친구 등급 : {getTier()}
                <small>(현재 등급)</small>
              </div>
              <div className={styles.rank}>
                <img src={`/img/${data.tiers}.png`} />
                <div className={styles.rank_text}>
                  <div className={styles.rank_text_field} id={styles.rank_text_field}>
                    {getNeedTierScore()}
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.line}></div>
            <div className={styles.b}>
              <div className={styles.body_text}>
                <button
                  className={buttonColor1 ? styles.classicButton : styles.activeButton}
                  onClick={() => {
                    if (viewPoint === 1) {
                      setViewPoint(0);
                      setButtonColor1(true);
                      setButtonColor2(true);
                      setButtonColor3(true);
                    } else {
                      setViewPoint(1);
                      setButtonColor1(false);
                      setButtonColor2(true);
                      setButtonColor3(true);
                    }
                  }}
                >
                  내가 찜한 문화
                </button>
              </div>
              <div className={styles.body_text}>
                <button
                  className={buttonColor2 ? styles.classicButton : styles.activeButton}
                  onClick={() => {
                    if (viewPoint === 2) {
                      setViewPoint(0);
                      setButtonColor1(true);
                      setButtonColor2(true);
                      setButtonColor3(true);
                    } else {
                      setViewPoint(2);
                      setButtonColor2(false);
                      setButtonColor1(true);
                      setButtonColor3(true);
                    }
                  }}
                >
                  내가 작성한 리뷰 글
                </button>
              </div>
              <div className={styles.body_text}>
                <button
                  className={buttonColor3 ? styles.classicButton : styles.activeButton}
                  onClick={() => {
                    if (viewPoint === 3) {
                      setViewPoint(0);
                      setButtonColor1(true);
                      setButtonColor2(true);
                      setButtonColor3(true);
                    } else {
                      setViewPoint(3);
                      setButtonColor3(false);
                      setButtonColor1(true);
                      setButtonColor2(true);
                    }
                  }}
                >
                  내가 작성한 문화친구 글
                </button>
              </div>
              <div id={styles.body_text}>
                <Link className={styles.body_text_link} to={`/ModifyMyPage`} state={{ myinfo: data }}>
                  내 정보 수정하기
                </Link>
              </div>
            </div>
          </div>
          <div className={styles.body_inner_bottom}>{switchComponent()}</div>
        </div>
      </div>
    </div>
  );
}

export default MyPage;
