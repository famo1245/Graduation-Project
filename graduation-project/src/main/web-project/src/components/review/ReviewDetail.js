import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams, Link, useNavigate } from "react-router-dom";
import styles from "./ReviewDetail.module.css";

function ReviewDetail() {
  const { id } = useParams();
  const [userName, setUserName] = useState("뚱이");
  const [comment, setComment] = useState("");
  const [feedComments, setFeedComments] = useState([]); // 댓글리스트
  const [isValid, setIsValid] = useState(false); //댓글 게시 가능여부, 유효성 검사
  const [isLogin, setIsLogin] = useState(false);
  const [like, setLike] = useState(false);
  const location = useLocation();
  const reviewInfo = location.state;
  const navigate = useNavigate();
  console.log(reviewInfo);
  const [star, setStar] = useState(3);

  let post = (e) => {
    const copyFeedComments = [...feedComments];
    copyFeedComments.push(comment);
    setFeedComments(copyFeedComments);
    setComment("");
  };

  const starCount = () => {
    let arr = [];
    for (let i = 0; i < star; i++) {
      arr.push(<img className={styles.star_fill} src={`/img/star.png`} />);
    }
    return arr;
  };

  const onClickCultureDetail = () => {
    navigate(`/munhwaRow/${reviewInfo.id}`, {
      replace: false,
      state: reviewInfo,
    });
  };

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  const toggleLike = async () => {
    // const res = await axios.post(
    //   ""
    // ); /* [POST] 사용자가 좋아요를 누름 -> DB 갱신 */
    setLike(!like);
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              리뷰게시판 상세
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              리뷰게시판 제목 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <img
              src={reviewInfo.poster}
              className={styles.poster}
              alt="문화 포스터 이미지"
            />
            <div className={styles.inner_content}>
              <div className={styles.inner_title}>
                [{reviewInfo.codeName}] {reviewInfo.title}
              </div>
              <div className={styles.info}>
                <div>작성자 : 뚱이</div>
              </div>
              <div className={styles.info}>
                <div>작성 날짜 : 2023년 9월 23일 20시 34분 24초</div>
              </div>
              <div className={styles.info}>
                <div className={styles.star_ratings_fill}>
                  <div>추천도</div> {starCount()}
                  {/* <div className={styles.star_ratings_fill}>{starCount()}</div> */}
                </div>
              </div>
              <div className={styles.info_active}>
                {isLogin ? (
                  <div className={styles.zzim1}>
                    <img
                      className={styles.zzim}
                      src={like ? `/img/heart (2).png` : `/img/heart.png`}
                      alt="찜하기"
                      onClick={toggleLike}
                    />
                    <span>좋아요</span>
                  </div>
                ) : (
                  <div></div>
                )}
                <div
                  className={styles.link_site}
                  onClick={onClickCultureDetail}
                >
                  문화상세
                </div>
              </div>
            </div>
          </div>
          <div className={styles.lower_content}>
            <div>
              Speaking slowly, carefully, the prime minister stuck closely to
              his talking points. "We're not looking to provoke or cause
              problems," he said. "We're standing up for the rules-based order."
              But where, several reporters asked, were Canada's allies? "So far
              in time," one journalist said to Mr Trudeau, "you seem to be
              alone". In the public eye at least, Mr Trudeau has appeared to be
              left largely on his own as he goes toe to toe with India, one of
              the world's fastest-growing economies, with a population 35 times
              bigger than Canada's. In the days since the prime minister made
              the explosive announcement, his allies in the Five Eyes
              intelligence alliance provided seemingly boilerplate public
              statements, all stopping far short of full-throated support. UK
              Foreign Secretary James Cleverly said his country took "very
              seriously the things that Canada are saying". Using nearly
              identical language, Australia said it was "deeply concerned" by
              the accusations. But perhaps the most deafening silence came from
              Canada's southern neighbour, the United States. The two countries
              are close allies, but the US did not speak up with outrage on
              Canada's behalf. When President Joe Biden publicly raised India
              this week, while speaking at the UN, it was not to condemn, but to
              praise the country for helping to establish a new economic
              pathway. Mr Biden's National Security Adviser Jake Sullivan later
              denied that there was a "wedge" between the US and its neighbour,
              saying Canada was being closely consulted. But other public
              statements were tepid, more nods to "deep concern", coupled with
              affirmations of India's growing importance to the Western world.
              Speaking slowly, carefully, the prime minister stuck closely to
              his talking points. "We're not looking to provoke or cause
              problems," he said. "We're standing up for the rules-based order."
              But where, several reporters asked, were Canada's allies? "So far
              in time," one journalist said to Mr Trudeau, "you seem to be
              alone". In the public eye at least, Mr Trudeau has appeared to be
              left largely on his own as he goes toe to toe with India, one of
              the world's fastest-growing economies, with a population 35 times
              bigger than Canada's. In the days since the prime minister made
              the explosive announcement, his allies in the Five Eyes
              intelligence alliance provided seemingly boilerplate public
              statements, all stopping far short of full-throated support. UK
              Foreign Secretary James Cleverly said his country took "very
              seriously the things that Canada are saying". Using nearly
              identical language, Australia said it was "deeply concerned" by
              the accusations. But perhaps the most deafening silence came from
              Canada's southern neighbour, the United States. The two countries
              are close allies, but the US did not speak up with outrage on
              Canada's behalf. When President Joe Biden publicly raised India
              this week, while speaking at the UN, it was not to condemn, but to
              praise the country for helping to establish a new economic
              pathway. Mr Biden's National Security Adviser Jake Sullivan later
              denied that there was a "wedge" between the US and its neighbour,
              saying Canada was being closely consulted. But other public
              statements were tepid, more nods to "deep concern", coupled with
              affirmations of India's growing importance to the Western world.
            </div>
            <div>
              <img src="/img/눈의꽃 사진.png" alt="" />
              <img src="/img/사랑했나봐.png" alt="" />
            </div>
          </div>
          <div className={styles.comment_container}>
            <div>댓글</div>
            <input
              type="text"
              placeholder="댓글 달기..."
              onChange={(e) => {
                setComment(e.target.value);
              }}
              onKeyUp={(e) => {
                e.target.value.length > 0
                  ? setIsValid(true)
                  : setIsValid(false);
              }}
              value={comment}
            />
            <button
              type="button"
              className={
                comment.length > 0
                  ? "submitCommentActive"
                  : "submitCommentInactive"
              }
              onClick={post}
              disabled={isValid ? false : true}
            >
              게시
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReviewDetail;
