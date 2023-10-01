import React, { useState, useEffect } from "react";
import styles from "./CultureFriendDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import CultureDetailComment from "./CultureDetailComment";

function CultureFriendDetail() {
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
              [{reviewInfo.codeName}]{reviewInfo.title}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.body_left}>
              <div className={styles.body_left_top}>
                <div className={styles.left_top_img}>
                  <img src="/img/사랑했나봐.png" alt="" />
                </div>
                <div className={styles.left_top_content}>
                  <div className={styles.inner_title}>
                    [{reviewInfo.codeName}] {reviewInfo.title}
                  </div>
                  <div id={styles.inner_title}>
                    <div>사용자가 작성한 문화친구 제목 들어올 위치</div>
                  </div>
                  <div className={styles.info}>
                    <div>나이대 | 20대~20대</div>
                  </div>
                  <div className={styles.info}>
                    <div>성별 | 상관없음</div>
                  </div>
                  <div className={styles.info}>
                    <div>날짜 | 11월 17일</div>
                  </div>
                </div>
              </div>
              <div className={styles.body_left_bottom}>
                사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                사용자가 적은 간단한 내용이 들어올 위치사용자가 적은 간단한
                내용이 들어올 위치 사용자가 적은 간단한 내용이 들어올 위치
                사용자가 적은 간단한 내용이 들어올 위치 사용자가 적은 간단한
                내용이 들어올 위치
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
