import styles from "./Footer.module.css";
import { Link } from "react-router-dom";

function Footer(props) {
  return (
    <div className={styles.footer}>
      <div className={styles.contents_nav}>
        <div className={styles.left}>
          <Link className={styles.left_click} to={``}>
            ◀
          </Link>
        </div>
        <h2 className={styles.content}>
          <div>
            <span className={styles.content_a}>
              대한민국 근로소득자라면, 누구나 누리고, 연말정산 돌려받자!
            </span>
          </div>
          <div>
            <span className={styles.content_b}>
              문화비 소득공제 바로가기 ⇒⇒⇒
            </span>
          </div>
        </h2>
        <div className={styles.link}>
          <a href="https://www.culture.go.kr/deduction/" target="_blank">
            <div>
              <span className={styles.link_title}>문화비 소득공제</span>
            </div>
            <div>
              <span className={styles.link_content}>
                도서/공연,박물관/미술관입장료/신문구독료
              </span>
            </div>
          </a>
        </div>
        <div className={styles.right}>
          <Link className={styles.right_click} to={`/Input_signup`}>
            ▶
          </Link>
        </div>
      </div>
      <div className={styles.contents_count}>
        <div className={styles.now_culture_title}>현재 즐길 수 있는 문화들</div>
        <table>
          <tr className={styles.table_name}>
            <th>뮤지컬/오페라</th>
            <th>클래식</th>
            <th>축제</th>
            <th></th>
          </tr>
          <tr className={styles.table_content}>
            <th>12</th>
            <th>4</th>
            <th>8</th>
            <th></th>
          </tr>
          <tr className={styles.table_name}>
            <th>문화교양/강좌</th>
            <th>연극</th>
            <th>기타</th>
            <th>어제보다</th>
          </tr>
          <tr className={styles.table_content}>
            <th>23</th>
            <th>11</th>
            <th>20</th>
            <th className={styles.diff_yesterday}>+8</th>
          </tr>
        </table>
      </div>
    </div>
  );
}

export default Footer;
