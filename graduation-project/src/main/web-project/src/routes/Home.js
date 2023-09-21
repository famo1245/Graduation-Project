import React, { Component, useEffect, useState } from 'react';
import ReactDOM from 'react-dom';
import { Link } from 'react-router-dom';
import styles from './Home.module.css';
import PropTypes from 'prop-types';
import { ScrollMenu, VisibilityContext } from 'react-horizontal-scrolling-menu';
import { LeftArrow, RightArrow } from '../components/horizonScroll/Arrow';
import usePreventBodyScroll from '../components/horizonScroll/UsePreventBodyScroll';
import { type } from '@testing-library/user-event/dist/type';
import axios from 'axios';

// const scrollVisibilityApiType = React.ContextType(VisibilityContext);
const url = '/dataList/OA-15486/S/1/datasetView.do';

function Home(props) {
  // const { disableScroll, enableScroll } = usePreventBodyScroll();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [userId, setUserId] = useState(-1);

  useEffect(() => {
    setLoading(true);
    if (sessionStorage.getItem('userId') != null) {
      setUserId(parseInt(sessionStorage.getItem('userId')));
    }
    axios
      .get(
        `/api/home?userId=${sessionStorage.getItem('userId') != null ? parseInt(sessionStorage.getItem('userId')) : -1}`
      )
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  return (
    <div className={styles.home}>
      <div>
        <h4>신규 문화 생활 추천</h4>
      </div>
      <div className={styles.home_upper}>
        <div className={styles.home_upper_content}>
          {data.recentCultures.map((culture) => {
            const url = `/munhwaRow/${culture.id}`;
            return (
              <div key={culture.id}>
                <Link to={url}>
                  <img src={culture.main_img} alt="상세페이지" />
                </Link>
              </div>
            );
          })}

          <div className={styles.prev}>
            <i className={styles.prev_arrow}>◀</i>
          </div>
          <div className={styles.next}>
            <i className={styles.angle_right}>▶</i>
          </div>
          {/* </ScrollMenu> */}
        </div>
      </div>
      <div>
        <h4>곧 끝나는 문화 생활</h4>
      </div>
      <div className={styles.home_upper}>
        <div className={styles.home_upper_content}>
          {data.recentCultures.map((culture) => {
            const url = `/munhwaRow/${culture.id}`;
            return (
              <div key={culture.id}>
                <Link to={url}>
                  <img src={culture.main_img} alt="상세페이지" />
                </Link>
              </div>
            );
          })}

          <div className={styles.prev}>
            <i className={styles.prev_arrow}>◀</i>
          </div>
          <div className={styles.next}>
            <i className={styles.angle_right}>▶</i>
          </div>
        </div>
      </div>
      <div className={styles.home_lower}>
        <div className={styles.home_lower_content}>
          <div className={styles.lower_content_left}>
            <div className={styles.review_notice_board}>
              <div className={styles.a}>리뷰게시판</div>
              <div className={styles.b}>
                <Link className={styles.see_more}>+</Link>
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img src={`img/boat.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
                    <div className={styles.star_ratings_base}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
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
                  <img src={`img/beautifulroad.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
                    <div className={styles.star_ratings_base}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
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
                  <img src={`img/sakura.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
                    <div className={styles.star_ratings_base}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
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
                  <img src={`img/green.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
                  <hr style={{ border: 0 }} />
                </span>
                <div className={styles.container_star_ratings}>
                  <span className={styles.star_ratings_title}>평점:</span>
                  <div className={styles.star_ratings}>
                    <div className={styles.star_ratings_fill}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
                    <div className={styles.star_ratings_base}>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                      <span>★</span>
                    </div>
                  </div>
                </div>
                <span>작성자: 오미크론</span>
                <br />
                <span>작성일자: 2023/06/06</span>
                <br />
                <span className={styles.user_short_text}>정말 멋있어요 강추!!!!합니다.</span>
              </div>
            </div>
          </div>
          <div className={styles.lower_content_right}>
            <div className={styles.culture_friend}>
              <div className={styles.a}>문화친구</div>
              <div className={styles.b}>
                <Link className={styles.see_more}>+</Link>
              </div>
            </div>
            <div className={styles.content_both_inner}>
              <div>
                <Link>
                  <img src={`img/whitehorse.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
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
                  <img src={`img/window.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
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
                  <img src={`img/window2.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
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
                  <img src={`img/waterandhouse.jpg`} />
                </Link>
              </div>
              <div className={styles.inner_content}>
                <span className={styles.inner_content_title}>
                  [뮤지컬/오페라]레미제라블 <br />
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

// function onWheel(apiObj, ev) {
//   const isThouchpad = Math.abs(ev.deltaX) !== 0 || Math.abs(ev.deltaY) < 15;

//   if (isThouchpad) {
//     ev.stopPropagation();
//     return;
//   }

//   if (ev.deltaY < 0) {
//     apiObj.scrollNext();
//   } else if (ev.deltaY > 0) {
//     apiObj.scrollPrev();
//   }
// }
