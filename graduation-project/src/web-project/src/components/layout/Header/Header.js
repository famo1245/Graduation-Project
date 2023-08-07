import { Link } from "react-router-dom";
import styles from "./Header.module.css";

function Header(props) {
  return (
    <div className={styles.header}>
      <div className={styles.header_upper}>
        <div className={styles.main_header} role={navigator}>
          <Link to={`/`} className={styles.logo}>
            문화 인 서울
          </Link>
          <div className={styles.login_route}>
            <Link to={`/Login`} className={styles.link} id={styles.login}>
              로그인
            </Link>
            <Link to={`/Sign_up`} className={styles.link} id={styles.signup}>
              회원가입
            </Link>
          </div>
        </div>
      </div>
      <div className={styles.header_lower}>
        <ul className={styles.header_nav}>
          <li className={styles.navigation_menu}>
            <span className={styles.menu_icon}>≡</span>
            <div className={styles.depth2}>
              <ul>
                <div>
                  <li>
                    <Link to={`/Whyso`} className={styles.depth2_link}>
                      뮤지컬/오페라
                    </Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>콘서트</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>독주/독창회</Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link className={styles.depth2_link}>문화교양/강좌</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>교육/체험</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>전시/미술</Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link className={styles.depth2_link}>클래식</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>국악</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>무용</Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link className={styles.depth2_link}>연극</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>영화</Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link className={styles.depth2_link}>축제-문화/예술</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>축제-전통/역사</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>축제-시민화합</Link>
                  </li>
                  <li>
                    <Link className={styles.depth2_link}>축제-기타</Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link className={styles.depth2_link}>기타</Link>
                  </li>
                </div>
              </ul>
            </div>
          </li>
          <li className={styles.navigation_tab}>
            <Link className={styles.a}>뮤지컬/오페라</Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link className={styles.a}>문화교양/강좌</Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link className={styles.a}>클래식</Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link className={styles.a}>연극</Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link className={styles.a}>축제</Link>
          </li>
        </ul>
        <ul className={styles.header_nav2}>
          <li>
            <Link className={styles.a}>관심문화</Link>
          </li>
          <li>
            <Link className={styles.a}>문화요청</Link>
          </li>
          <li>
            <Link className={styles.a}>리뷰게시판</Link>
          </li>
          <li>
            <Link className={styles.a}>문화친구</Link>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default Header;
