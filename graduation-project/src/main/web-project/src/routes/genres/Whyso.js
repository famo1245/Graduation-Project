{
  /* <div className={styles.comment_container}>
  <div className={styles.comment_title}>댓글</div>
  <li className={styles.comment_form}>
    <form onSubmit={handleSubmit}>
      <span className={styles.ps_box}>
        <input
          type="text"
          className={styles.int}
          placeholder="댓글 달기..."
          onChange={handleChange}
          value={value}
        />
      </span>
      <input type="submit" className={styles.comment_btn} value="댓글등록" />
    </form>
  </li>
  <li>
    <ul className={styles.comment_row} key={k}>
      <li className={styles.comment_id}>{v.userId}</li>
      <li className={styles.comment_content}>
        <span>
          {state.update === k ? (
            <input
              type="text"
              value={state.value}
              onChange={handleChange}
              className={styles.comment_update_input}
              onKeyDown={updateKeyDown(k)}
            />
          ) : (
            <div>
              <span onClick={handleClick(k)}>{v.content}</span>
              <button
                className={styles.comment_delete_btn}
                onClick={() => {
                  deleteList(k);
                }}
              >
                댓글삭제
              </button>
            </div>
          )}
        </span>
      </li>
      <li className={styles.comment_date}>{v.date}</li>
    </ul>
  </li>
</div>; */
}

// import React, { Component, useEffect, useState } from "react";
// import { Link } from "react-router-dom";
// import ReactDOM from "react-dom";
// import PropTypes from "prop-types";
// import { ScrollMenu, VisibilityContext } from "react-horizontal-scrolling-menu";
// import styles from "./Whyso.module.css";

// let list = [
//   { name: "item1" },
//   { name: "item2" },
//   { name: "item3" },
//   { name: "item4" },
//   { name: "item5" },
//   { name: "item6" },
//   { name: "item7" },
//   { name: "item8" },
//   { name: "item9" },
//   { name: "item10" },
//   { name: "item11" },
//   { name: "item12" },
//   { name: "item13" },
//   { name: "item14" },
//   { name: "item15" },
//   { name: "item16" },
//   { name: "item17" },
//   { name: "item18" },
//   { name: "item19" },
//   { name: "item20" },
//   { name: "item21" },
//   { name: "item22" },
//   { name: "item23" },
//   { name: "item24" },
//   { name: "item25" },
// ];

// const MenuItem = ({ text, selected }) => {
//   return <div className={`menu-item ${selected ? "active" : ""}`}>{text}</div>;
// };

// export const Menu = (list, selected) =>
//   list.map((el) => {
//     const { name } = el;

//     return <MenuItem text={name} key={name} selected={selected} />;
//   });

// const Arrow = ({ text, className }) => {
//   return <div className={className}>{text}</div>;
// };
// Arrow.propTypes = {
//   text: PropTypes.string,
//   className: PropTypes.string,
// };

// export const ArrowLeft = Arrow({ text: "<", className: "arrow-prev" });
// export const ArrowRight = Arrow({ text: ">", className: "arrow-next" });

// class Whyso extends Component {
//   state = {
//     alignCenter: true,
//     clickWhenDrag: false,
//     dragging: true,
//     hideArrows: true,
//     hideSingleArrow: true,
//     itemsCount: list.length,
//     scrollToSelected: false,
//     selected: "item1",
//     translate: 0,
//     transition: 0.3,
//     wheel: true,
//   };

//   constructor(props) {
//     super(props);
//     this.menu = null;
//     this.menuItems = Menu(list.slice(0, list.length), this.state.selected);
//   }

//   onFirstItemVisible = () => {
//     console.log("first item is visible");
//   };

//   onLastItemVisible = () => {
//     console.log("last item is visible");

//     const newItems = Array(5)
//       .fill(1)
//       .map((el, ind) => ({ name: `item${list.length + ind + 1}` }));
//     list = list.concat(newItems);
//     this.menuItems = Menu(list, list.slice(-1)[0].name);
//     this.setState({
//       itemsCount: list.length,
//       selected: this.state.selected,
//     });
//   };

//   onUpdate = ({ translate }) => {
//     console.log(`onUpdate: translate: ${translate}`);
//     this.setState({ translate });
//   };

//   onSelect = (key) => {
//     console.log(`onSelect: ${key}`);
//     this.setState({ selected: key });
//   };

//   componentDidUpdate(prevProps, prevState) {
//     const { alignCenter } = prevState;
//     const { alignCenter: alignCenterNew } = this.state;
//     if (alignCenter !== alignCenterNew) {
//       this.menu.setInitial();
//     }
//   }

//   setItemsCount = (ev) => {
//     const { itemsCount = list.length, selected } = this.state;
//     const val = +ev.target.value;
//     const itemsCountNew =
//       !isNaN(val) && val <= list.length && val >= 0
//         ? +ev.target.value
//         : list.length;
//     const itemsCountChanged = itemsCount !== itemsCountNew;

//     if (itemsCountChanged) {
//       this.menuItems = Menu(list.slice(0, itemsCountNew), selected);
//       this.setState({
//         itemsCount: itemsCountNew,
//       });
//     }
//   };

//   setSelected = (ev) => {
//     const { value } = ev.target;
//     this.setState({ selected: String(value) });
//   };

//   render() {
//     const {
//       alignCenter,
//       clickWhenDrag,
//       hideArrows,
//       dragging,
//       hideSingleArrow,
//       itemsCount,
//       scrollToSelected,
//       selected,
//       translate,
//       transition,
//       wheel,
//     } = this.state;

//     const menu = this.menuItems;

//     const checkboxStyle = {
//       margin: "5px 10px",
//     };
//     const valueStyle = {
//       margin: "5px 10px",
//       display: "inline-block",
//     };

//     return (
//       <div className={styles.App}>
//         <header className={styles.App_header}>
//           <h1 className={styles.App_title}>React horizontal scrolling menu</h1>
//         </header>
//         <p className={styles.App_intro}>
//           Horizontal scrolling menu example. Click arrow or drag items.
//         </p>

//         <ScrollMenu
//           alignCenter={alignCenter}
//           arrowLeft={ArrowLeft}
//           arrowRight={ArrowRight}
//           clickWhenDrag={clickWhenDrag}
//           data={menu}
//           dragging={dragging}
//           hideArrows={hideArrows}
//           hideSingleArrow={hideSingleArrow}
//           onFirstItemVisible={this.onFirstItemVisible}
//           onLastItemVisible={this.onLastItemVisible}
//           onSelect={this.onSelect}
//           onUpdate={this.onUpdate}
//           ref={(el) => (this.menu = el)}
//           scrollToSelected={scrollToSelected}
//           selected={selected}
//           transition={+transition}
//           translate={translate}
//           wheel={wheel}
//         />

//         <form className={styles.properties}>
//           <label style={checkboxStyle}>
//             Align center
//             <input
//               name="alignCenter"
//               type="checkbox"
//               checked={alignCenter}
//               onChange={() => this.setState({ alignCenter: !alignCenter })}
//             />
//           </label>
//           <label style={checkboxStyle}>
//             Dragging
//             <input
//               name="dragging"
//               type="checkbox"
//               checked={dragging}
//               onChange={() => this.setState({ dragging: !dragging })}
//             />
//           </label>
//           <label style={checkboxStyle}>
//             Click when drag end
//             <input
//               name="clickWhenDrag"
//               type="checkbox"
//               checked={clickWhenDrag}
//               onChange={() => this.setState({ clickWhenDrag: !clickWhenDrag })}
//             />
//           </label>
//           <label style={checkboxStyle}>
//             Use mouse wheel
//             <input
//               name="wheel"
//               type="checkbox"
//               checked={wheel}
//               onChange={() => this.setState({ wheel: !wheel })}
//             />
//           </label>
//           <label style={checkboxStyle}>
//             Hide arrows if items width less than menu width
//             <input
//               name="hideArrows"
//               type="checkbox"
//               checked={hideArrows}
//               onChange={() => this.setState({ hideArrows: !hideArrows })}
//             />
//           </label>
//           <label style={checkboxStyle}>
//             Hide left/right arrows if first/last item visible
//             <input
//               name="hideSingleArrow"
//               type="checkbox"
//               checked={hideSingleArrow}
//               onChange={() =>
//                 this.setState({ hideSingleArrow: !hideSingleArrow })
//               }
//             />
//           </label>
//           <br />
//           <div style={valueStyle}>Translate: {translate.toFixed(2)}</div>
//           <label style={valueStyle}>
//             Selected:
//             <input
//               style={{ margin: "0 5px" }}
//               name="selected"
//               type="text"
//               value={selected}
//               onChange={this.setSelected}
//             />
//           </label>
//           <label style={valueStyle}>
//             Transition duration:
//             <input
//               style={{ margin: "0 5px" }}
//               name="transition"
//               type="number"
//               value={transition || 0}
//               min={0}
//               max={10}
//               onChange={(ev) =>
//                 this.setState({
//                   transition: !isNaN(ev.target.value) ? +ev.target.value : 0,
//                 })
//               }
//             />
//           </label>
//           <label style={valueStyle}>
//             Items count:
//             <input
//               style={{ margin: "0 5px" }}
//               name="itemsCount"
//               type="number"
//               value={itemsCount}
//               min={0}
//               max={list.length}
//               onChange={this.setItemsCount}
//             />
//           </label>
//         </form>
//         <hr />
//       </div>
//     );
//   }
// }

// export default Whyso;
