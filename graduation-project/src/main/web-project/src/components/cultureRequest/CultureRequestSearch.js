import React, { useState, useEffect } from "react";
import { FaSearch } from "react-icons/fa";
import styles from "./CultureRequestSearch.module.css";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import CultureRequestContent from "./CultureRequestContent";
import ReactPaginate from "react-paginate";

function CultureRequestSearch({ inputD, checkId, state }) {
  const [data, setData] = useState("");
  const [pageNumber, setPageNumber] = useState(0);
  // const [searchData, setSearchData] = useState([]);
  // const [pageCount, setPageCount] = useState();
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const culturePerPage = 15;
  let pagesVisited = pageNumber * culturePerPage;
  let pageCount = Math.ceil(inputD.length / culturePerPage);

  useEffect(() => {
    if (state) {
      setData(state.title);
    }
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  // useEffect(() => {
  //   setPageCount(Math.ceil(inputD.length / culturePerPage));
  // }, []);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  const handleChange = (e) => {
    setData(e.target.value);
  };

  const handleOnKeyPress = (e) => {
    if (e.key === "Enter") {
      pageCount = 1;
    }
  };

  const displayCultures = inputD
    .filter((val) => {
      if (data == "") {
        return val;
      } else if (val.cultureTitle.toLowerCase().includes(data.toLowerCase())) {
        return val;
      }
    })
    .slice(pagesVisited, pagesVisited + culturePerPage)
    .map((val, key) => {
      return (
        <CultureRequestContent
          key={key}
          cultureImg={val.cultureImg}
          title={val.title}
          meetDate={val.meetDate}
          roomId={val.roomId}
          cultureTitle={val.cultureTitle}
          availableAgeRange={val.availableAgeRange}
          gender={val.gender}
        />
      );
    });

  const onClick = () => {
    navigate(`/CultureRequestPost`, {
      replace: false,
    });
  };

  return (
    <div className={styles.container}>
      <div className={styles.wrapper}>
        <div className={styles.for_the_review_button}>
          <div className={styles.inputWrapper}>
            <div className={styles.searchLogo}>검색 |</div>
            <input
              type="text"
              value={data}
              onChange={handleChange}
              onKeyDown={handleOnKeyPress}
            />
            <FaSearch id={styles.searchIcon} />
          </div>
          {isLogin ? (
            <div className={styles.review_input_button} onClick={onClick}>
              <span>+</span> 문화 요청하기
            </div>
          ) : (
            <div></div>
          )}
        </div>
        <div className={styles.line}></div>
        <div className={styles.row}>
          {displayCultures}
          <ReactPaginate
            previousLabel={"Previous"}
            nextLabel={"Next"}
            pageCount={pageCount}
            onPageChange={changePage}
            containerClassName={styles.paginationBttns}
            previousLinkClassName={styles.previousBttn}
            nextLinkClassName={styles.nextBttn}
            disabledClassName={styles.paginationDisabled}
            activeClassName={styles.paginationActive}
          />
        </div>
      </div>
    </div>
  );
}

export default CultureRequestSearch;
