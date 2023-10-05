import React, { useState, useEffect } from "react";
import styles from "./CultureFriendDetail.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import CultureDetailComment from "./CultureDetailComment";
import axios from "axios";

function CultureFriendDetail(props) {
  const location = useLocation();
  const cultureFriend = location.state;
  const [isLogin, setIsLogin] = useState(false);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  const addList = (content) => {
    axios
      .post(`/api/chats/new-message/${cultureFriend.roomId}?userId=${sessionStorage.getItem("userId")}`, {
        text: content,
      })
      .then((res) => {
        setState({
          list: [...state.list, res.data.chat],
        });
      });
  };

  const [state, setState] = useState({
    value: "",
    update: null,
    list: cultureFriend.chats,
  });

  const dateConverter = (data) => {
    const date = new Date(data);
    let result = "";
    result += date.toLocaleDateString().slice(0, -1).replace(/ /g, "");
    result += " ";
    result += date.toTimeString().slice(0, 5);
    return result;
  };

  let renderList = state.list.map((v, k) => {
    return (
      <div>
        {v.author != sessionStorage.getItem("userId") ? (
          <div key={k} className={styles.renderList_container}>
            <div className={styles.comment_row}>
              <div className={styles.comment_id}>{v.nickName}</div>
              <div className={styles.comment_date}>{dateConverter(v.created_at)}</div>
            </div>
            <div className={styles.comment_content}>
              <span>
                <div>
                  <span className={styles.comment_content_inner}>{v.text}</span>
                </div>
              </span>
            </div>
          </div>
        ) : (
          <div key={k} className={styles.renderList_container2}>
            <div className={styles.comment_row}>
              <div className={styles.comment_id}>{v.nickName}</div>
              <div className={styles.comment_date}>{dateConverter(v.created_at)}</div>
            </div>
            <div className={styles.comment_content}>
              <span>
                <div>
                  <span className={styles.comment_content_inner2}>{v.text}</span>
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
              {cultureFriend.cultureTitle}
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.body_left}>
              <div className={styles.body_left_top}>
                <div className={styles.left_top_img}>
                  <img src={cultureFriend.cultureImg} alt="" />
                </div>
                <div className={styles.left_top_content}>
                  <div className={styles.inner_title}>{cultureFriend.cultureTitle}</div>
                  <div id={styles.inner_title}>
                    <div>{cultureFriend.title}</div>
                  </div>
                  <div className={styles.info}>
                    <div>
                      나이대 |{" "}
                      {cultureFriend.availableAgeRange === "any" ? "상관없음" : cultureFriend.availableAgeRange}
                    </div>
                  </div>
                  <div className={styles.info}>
                    <div>
                      성별 | {cultureFriend.gender === "any" ? "아무나" : cultureFriend.gender === "male" ? "남" : "여"}
                    </div>
                  </div>
                  <div className={styles.info}>
                    <div>날짜 | {new Date(cultureFriend.meetDate).toLocaleDateString()}</div>
                  </div>
                </div>
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
