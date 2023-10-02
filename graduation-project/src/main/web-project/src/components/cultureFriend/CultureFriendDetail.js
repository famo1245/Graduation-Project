import React, { useState, useEffect } from "react";
import styles from "./CultureFriendDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import CultureDetailComment from "./CultureDetailComment";

function CultureFriendDetail(props) {
  const location = useLocation();
  const reviewInfo = location.state;
  console.log(reviewInfo);
  const [isLogin, setIsLogin] = useState(false);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  const addList = (content, date) => {
    setState({
      list: [...state.list, { userId: "뚱이", content, date }],
    });
  };

  const [state, setState] = useState({
    value: "",
    update: null,
    list: [
      {
        userId: "뚱보",
        content: "게시글 리스트 테스트 1",
        date: "2023/09/29 16 : 37",
      },
      {
        userId: "먹보",
        content: "게시글 리스트 테스트 2",
        date: "2023/09/29 16 : 40",
      },
      {
        userId: "또라에몽",
        content: "게시글 리스트 테스트 3",
        date: "2023/09/29 16 : 41",
      },
    ],
  });

  console.log(state);
  console.log(reviewInfo);

  let renderList = state.list.map((v, k) => {
    return (
      <div>
        {v.userId !== "뚱이" ? (
          <div key={k} className={styles.renderList_container}>
            <div className={styles.comment_row}>
              <div className={styles.comment_id}>{v.userId}</div>
              <div className={styles.comment_date}>{v.date}</div>
            </div>
            <div className={styles.comment_content}>
              <span>
                <div>
                  <span className={styles.comment_content_inner}>
                    {v.content}
                  </span>
                </div>
              </span>
            </div>
          </div>
        ) : (
          <div key={k} className={styles.renderList_container2}>
            <div className={styles.comment_row}>
              <div className={styles.comment_id}>{v.userId}</div>
              <div className={styles.comment_date}>{v.date}</div>
            </div>
            <div className={styles.comment_content}>
              <span>
                <div>
                  <span className={styles.comment_content_inner2}>
                    {v.content}
                  </span>
                </div>
              </span>
            </div>
          </div>
        )}
      </div>
    );
  });

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              문화친구
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {reviewInfo.cultureTitle}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.body_left}>
              <div className={styles.body_left_top}>
                <div className={styles.left_top_img}>
                  <img src={reviewInfo.cultureImg} alt="" />
                </div>
                <div className={styles.left_top_content}>
                  <div className={styles.inner_title}>
                    {reviewInfo.cultureTitle}
                  </div>
                  <div id={styles.inner_title}>
                    <div>{reviewInfo.title}</div>
                  </div>
                  <div className={styles.info}>
                    <div>나이대 | {reviewInfo.availableAgeRange}</div>
                  </div>
                  <div className={styles.info}>
                    <div>성별 | {reviewInfo.gender}</div>
                  </div>
                  <div className={styles.info}>
                    <div>날짜 | {reviewInfo.meetDate}</div>
                  </div>
                </div>
              </div>
              <div className={styles.body_left_bottom}>
                <span>
                  사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                  사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                  사용자가 적은 간단한 내용이 들어올 위치사용자가 적은 간단한
                  내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                </span>
              </div>
            </div>
            <div className={styles.body_right}>
              <div className={styles.talk_box}>{renderList}</div>
              <CultureDetailComment addList={addList} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureFriendDetail;
