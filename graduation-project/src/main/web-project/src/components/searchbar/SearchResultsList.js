import React, { useCallback } from "react";
import styles from "./SearchResultsList.module.css";
// import MunhwaRow from "./MunhwaRow";

function SearchResultsList({ inputD, dataa }) {
  // let row = [];
  // inputD.forEach((m) => {
  //   if (m.title.indexOf(dataa) == -1) {
  //     return;
  //   }
  //   row.push(<MunhwaRow inputD={m} />); //배열에 추가
  // });

  return (
    <div className={"row"}>
      <table className={"table"}>
        <thead>
          <tr className={"danger"}>
            <td>순위</td>
            <td></td>
            <td>노래명</td>
            <td>가수명</td>
          </tr>
        </thead>
        {inputD
          .filter((val) => {
            if (dataa == "") {
              return val;
            } else if (val.album.toLowerCase().includes(dataa.toLowerCase())) {
              return val;
            }
          })
          .map((val, key) => {
            return (
              <tr>
                <td>{val.rank}</td>
                <td>
                  <img src={val.poster} width={"30"} height={"30"} />
                </td>
                <td>{val.title}</td>
                <td>{val.singer}</td>
              </tr>
            );
          })}
      </table>
    </div>
  );
}

function MunhwaRow(props) {
  return (
    <tr>
      <td>{props.inputD.rank}</td>
      <td>
        <img src={props.inputD.poster} width={"30"} height={"30"} />
      </td>
      <td>{props.inputD.title}</td>
      <td>{props.inputD.singer}</td>
    </tr>
  );
}

export default SearchResultsList;

// const html = inputD.map((m) => (
//   <tr>
//     <td>{m.rank}</td>
//     <td>
//       <img src={m.poster} width={"35"} height={"35"} />
//     </td>
//     <td>{m.title}</td>
//     <td>{m.singer}</td>
//   </tr>
// ));
