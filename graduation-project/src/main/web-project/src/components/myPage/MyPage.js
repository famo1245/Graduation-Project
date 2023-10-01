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

  useEffect(() => {
    setLoading(true);
    const userId = sessionStorage.getItem("userId");
    axios
      .post("/api/members/info", { userId: userId })
      .then((response) => {
        setData(response.data.myInfo);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  console.log(data);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  const switchComponent = () => {
    switch (viewPoint) {
      case 1:
        return <MyPickCulture />;
      case 2:
        return <MyReview />;
      case 3:
        return <MyCultureFriend />;
      default:
        return <></>;
    }
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
                나이/성별 : {data.age_range}/
                {data.gender === "male" ? "남" : "여"}
              </div>
              <div className={styles.text}>관심지역 : {data.district}</div>
              <div className={styles.text}>
                문화친구 티어 : {data.tiers}(현재티어)
              </div>
              <div className={styles.rank}>
                <img src={`/img/챌린저.png`} />
                <div className={styles.rank_text}>
                  <div className={styles.rank_text_field}>
                    상위 99% 문화친구에요!
                  </div>
                  <div
                    className={styles.rank_text_field}
                    id={styles.rank_text_field}
                  >
                    다음 티어까지 100MC 필요
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.line}></div>
            <div className={styles.b}>
              <div className={styles.body_text}>
                <button
                  className={
                    buttonColor1 ? styles.classicButton : styles.activeButton
                  }
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
                  className={
                    buttonColor2 ? styles.classicButton : styles.activeButton
                  }
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
                  className={
                    buttonColor3 ? styles.classicButton : styles.activeButton
                  }
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
                <Link
                  className={styles.body_text_link}
                  to={`/ModifyMyPage`}
                  state={{ myinfo: data }}
                >
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

// {viewPoint === 1 ? (
//   <MyPickCulture />
// ) : viewPoint === 2 ? (
//   <MyReview />
// ) : (
//   <MyCultureFriend />
// )}
