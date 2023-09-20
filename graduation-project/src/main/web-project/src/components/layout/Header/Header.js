import { Link } from 'react-router-dom';
import styles from './Header.module.css';

const urlLeft = {
  true: '/Logout',
  false: '/Login',
};

const urlRight = {
  true: '/Mypage',
  false: '/Sign_up',
};

const nameLeft = {
  true: '로그아웃',
  false: '로그인',
};

const nameRight = {
  true: '마이페이지',
  false: '회원가입',
};

function Header(props) {
  const isLogin = sessionStorage.getItem('userId') === null ? false : true;

  return (
    <div className={styles.header}>
      <div className={styles.header_upper}>
        <div className={styles.main_header} role={navigator}>
          <Link to={`/`} className={styles.logo}>
            문화 인 서울
          </Link>
          <div className={styles.login_route}>
            <Link to={urlLeft[isLogin]} className={styles.link} id={styles.login}>
              {nameLeft[isLogin]}
            </Link>
            <Link to={urlRight[isLogin]} className={styles.link} id={styles.signup}>
              {nameRight[isLogin]}
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
                    <Link to={`/Musical`} className={styles.depth2_link}>
                      뮤지컬/오페라
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Concert`} className={styles.depth2_link}>
                      콘서트
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Solo_SingingParty`} className={styles.depth2_link}>
                      독주/독창회
                    </Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link to={`/cultureAndCulture`} className={styles.depth2_link}>
                      문화교양/강좌
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Education`} className={styles.depth2_link}>
                      교육/체험
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Exhibition`} className={styles.depth2_link}>
                      전시/미술
                    </Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link to={`/Classic`} className={styles.depth2_link}>
                      클래식
                    </Link>
                  </li>
                  <li>
                    <Link to={`/KoreanTraditionalMusic`} className={styles.depth2_link}>
                      국악
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Dance`} className={styles.depth2_link}>
                      무용
                    </Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link to={`/Play`} className={styles.depth2_link}>
                      연극
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Movies`} className={styles.depth2_link}>
                      영화
                    </Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link to={`/Festival_culture`} className={styles.depth2_link}>
                      축제-문화/예술
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Festival_traditional`} className={styles.depth2_link}>
                      축제-전통/역사
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Festival_citizen`} className={styles.depth2_link}>
                      축제-시민화합
                    </Link>
                  </li>
                  <li>
                    <Link to={`/Festival_Other`} className={styles.depth2_link}>
                      축제-기타
                    </Link>
                  </li>
                </div>
                <div>
                  <li>
                    <Link to={`/Others`} className={styles.depth2_link}>
                      기타
                    </Link>
                  </li>
                </div>
              </ul>
            </div>
          </li>
          <li className={styles.navigation_tab}>
            <Link to={`/Musical`} className={styles.a}>
              뮤지컬/오페라
            </Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link to={`/cultureAndCulture`} className={styles.a}>
              문화교양/강좌
            </Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link to={`/Classic`} className={styles.a}>
              클래식
            </Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link to={`/Play`} className={styles.a}>
              연극
            </Link>
          </li>
          <li className={styles.navigation_tab}>
            <Link to={`/Festival`} className={styles.a}>
              축제
            </Link>
          </li>
        </ul>
        <ul className={styles.header_nav2}>
          <li>
            <Link to={`/Concert`} className={styles.a}>
              관심문화
            </Link>
          </li>
          <li>
            <Link to={`/Concert`} className={styles.a}>
              문화요청
            </Link>
          </li>
          <li>
            <Link to={`/ReviewBoard`} className={styles.a}>
              리뷰게시판
            </Link>
          </li>
          <li>
            <Link to={`/Concert`} className={styles.a}>
              문화친구
            </Link>
          </li>
        </ul>
      </div>
    </div>
  );
}

export default Header;
