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

// const scrollVisibilityApiType = React.ContextType(VisibilityContext);
const url = "/dataList/OA-15486/S/1/datasetView.do";

function Home(props) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setLoading(true);
    const userId =
      sessionStorage.getItem("userId") != null
        ? parseInt(sessionStorage.getItem("userId"))
        : -1;
    setIsLogin(sessionStorage.getItem("userId") === null ? false : true);
    axios
      .get(`/api/home?userId=${userId}`)
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  console.log(data);

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
          {/* <div className={styles.prev}>
            <i className={styles.prev_arrow}>◀</i>
          </div>
          <div className={styles.next}>
            <i className={styles.angle_right}>▶</i>
          </div> */}
          {/* </ScrollMenu> */}
        </div>
      </div>
      <div>
        <h4>
          {isLogin ? data.category + " 문화 생활" : "곧 끝나는 문화 생활"}
        </h4>
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

          {/* <div className={styles.prev}>
            <i className={styles.prev_arrow}>◀</i>
          </div>
          <div className={styles.next}>
            <i className={styles.angle_right}>▶</i>
          </div> */}
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
                        <div className={styles.star_ratings_fill}>
                          {starCount(parseInt(review.reviewGrade))}
                        </div>
                        <div className={styles.star_ratings_base}>
                          {starBase()}
                        </div>
                      </div>
                    </div>
                    <span>작성자: {review.nickname}</span>
                    <br />
                    <span>
                      작성일자:{" "}
                      {new Date(review.reviewDateTime).toLocaleDateString()}
                    </span>
                    <br />
                    <span className={styles.user_short_text}>
                      {review.reviewContents}
                    </span>
                  </div>
                </div>
              );
            })}
            {/* <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img className={styles.review_img} src={`/img/눈의꽃 사진.png`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>{starCount()}</div>
                    <div className={styles.star_ratings_base}>{starBase()}</div>
                  </div>
                </div>
                <span>작성자: 오미크론</span>
                <br />
                <span>작성일자: 2023/06/06</span>
                <br />
                <span className={styles.user_short_text}>정말 멋있어요 강추!!!!합니다.</span>
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img className={styles.review_img} src={`/img/눈의꽃 사진.png`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>{starCount()}</div>
                    <div className={styles.star_ratings_base}>{starBase()}</div>
                  </div>
                </div>
                <span>작성자: 오미크론</span>
                <br />
                <span>작성일자: 2023/06/06</span>
                <br />
                <div className={styles.user_short_text}>
                  정말 멋있어요 강추!!!!합니다. 너무좋아요 사랑해요 레미제라블 너는 나의 하나뿐인 뮤지컬이야. 제발
                  후속작 이만개 내줘요 제작자님!!
                </div>
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img className={styles.review_img} src={`/img/눈의꽃 사진.png`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>{starCount()}</div>
                    <div className={styles.star_ratings_base}>{starBase()}</div>
                  </div>
                </div>
                <span>작성자: 오미크론</span>
                <br />
                <span>작성일자: 2023/06/06</span>
                <br />
                <span className={styles.user_short_text}>정말 멋있어요 강추!!!!합니다.</span>
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img className={styles.review_img} src={`/img/눈의꽃 사진.png`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>{starCount()}</div>
                    <div className={styles.star_ratings_base}>{starBase()}</div>
                  </div>
                </div>
                <span>작성자: 오미크론</span>
                <br />
                <span>작성일자: 2023/06/06</span>
                <br />
                <span className={styles.user_short_text}>정말 멋있어요 강추!!!!합니다.</span>
              </div>
            </div> */}
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
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img
                    className={styles.review_img}
                    src={`/img/사랑했나봐.png`}
                  />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <span>작성자 제목: 우리 같이 즐겨욧</span>
                <br />
                <span>관람 날짜: 2023/06/12</span>
                <br />
                <span>작성자: 오미크론/남/20대</span>
                <br />
                <span>원하는 사람: 여/20대</span>
                <br />
                <span>구하는 인원: 1 / 2</span>
                <br />
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img
                    className={styles.review_img}
                    src={`/img/사랑했나봐.png`}
                  />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <span>작성자 제목: 우리 같이 즐겨욧</span>
                <br />
                <span>관람 날짜: 2023/06/12</span>
                <br />
                <span>작성자: 오미크론/남/20대</span>
                <br />
                <span>원하는 사람: 여/20대</span>
                <br />
                <span>구하는 인원: 1 / 2</span>
                <br />
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img
                    className={styles.review_img}
                    src={`/img/사랑했나봐.png`}
                  />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <span>작성자 제목: 우리 같이 즐겨욧</span>
                <br />
                <span>관람 날짜: 2023/06/12</span>
                <br />
                <span>작성자: 오미크론/남/20대</span>
                <br />
                <span>원하는 사람: 여/20대</span>
                <br />
                <span>구하는 인원: 1 / 2</span>
                <br />
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img
                    className={styles.review_img}
                    src={`/img/사랑했나봐.png`}
                  />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블
                  <hr style={{ border: 0 }} />
                </span>
                <span>작성자 제목: 우리 같이 즐겨욧</span>
                <br />
                <span>관람 날짜: 2023/06/12</span>
                <br />
                <span>작성자: 오미크론/남/20대</span>
                <br />
                <span>원하는 사람: 여/20대</span>
                <br />
                <span>구하는 인원: 1 / 2</span>
                <br />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
