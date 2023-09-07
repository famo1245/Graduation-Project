import React from "react";
import styles from "./ModifyMyPage.module.css";
import { Link, NavLink } from "react-router-dom";
import { useState } from "react";

function ModifyMyPage() {
  // const [clicked, setClicked] = useState(false);
  // const onClick = () => {
  //   setClicked(!clicked);
  // };

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
              <div className={styles.text}>닉네임 변경하기</div>
              <div className={styles.text}>관심지역 변경하기</div>
              <div className={styles.text}>관심문화 변경하기</div>
              <div id={styles.text}>
                <Link className={styles.text_link} to={`/MyPage`}>
                  <button type="submit" form="profileForm">
                    수정완료
                  </button>
                </Link>
              </div>
            </div>
            <div className={styles.b}>
              <form
                action=""
                method="post"
                name="profile"
                target=""
                encType=""
                id="profileForm"
              >
                <div className={styles.text} id={styles.nickname}>
                  <input type="text" />
                </div>
                <div className={styles.text} id={styles.interest_region}>
                  <select>
                    <option value="dobong">도봉구</option>
                    <option value="nowon">노원구</option>
                    <option value="kangbook">강북구</option>
                    <option value="sungbook">성북구</option>
                    <option value="jungrang">중랑구</option>
                    <option value="dongdaemon">동대문구</option>
                    <option value="gwangjin">광진구</option>
                    <option value="sungdong">성동구</option>
                    <option value="jung">중구</option>
                    <option value="youngsan">용산구</option>
                    <option value="joungro">종로구</option>
                    <option value="seodaemon">서대문구</option>
                    <option value="eunpyeng">은평구</option>
                    <option value="mapo">마포구</option>
                    <option value="gangseo">강서구</option>
                    <option value="yangchun">양천구</option>
                    <option value="guro">구로구</option>
                    <option value="youngdeung">영등포구</option>
                    <option value="geumcheon">금천구</option>
                    <option value="dongjak">동작구</option>
                    <option value="gwanak">관악구</option>
                    <option value="seocho">서초구</option>
                    <option value="gangnam">강남구</option>
                    <option value="songpa">송파구</option>
                    <option value="gangdong">강동구</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.interest_culture}>
                  <div>
                    <input type="checkbox" name="culture1" value="m_o" />
                    뮤지컬/오페라
                  </div>
                  <div>
                    <input type="checkbox" name="culture2" value="c" />
                    콘서트
                  </div>
                  <div>
                    <input type="checkbox" name="culture3" value="d_d" />
                    독주/독창회
                  </div>
                  <div>
                    <input type="checkbox" name="culture4" value="m_k" />
                    문화교양/강좌
                  </div>
                  <div>
                    <input type="checkbox" name="culture5" value="k_c" />
                    교육/체험
                  </div>
                  <div>
                    <input type="checkbox" name="culture6" value="j_m" />
                    전시/미술
                  </div>
                  <div>
                    <input type="checkbox" name="culture7" value="classic" />
                    클래식
                  </div>
                  <div>
                    <input type="checkbox" name="culture8" value="korean" />
                    국악
                  </div>
                  <div>
                    <input type="checkbox" name="culture9" value="dance" />
                    무용
                  </div>
                  <div>
                    <input type="checkbox" name="culture10" value="play" />
                    연극
                  </div>
                  <div>
                    <input type="checkbox" name="culture11" value="movie" />
                    영화
                  </div>
                  <div>
                    <input type="checkbox" name="culture12" value="f" />
                    축제
                  </div>
                  <div>
                    <input type="checkbox" name="culture13" value="others" />
                    기타
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ModifyMyPage;
