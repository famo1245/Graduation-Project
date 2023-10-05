import { func } from "prop-types";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./routes/Login";
import Sign_up from "./routes/Sign_up";
import Index_home from "./pages/Index_home";
import Layout from "./components/layout/Layout";
import Musical from "./components/munhwa/Musical";
import MunhwaDetail from "./components/munhwa/MunhwaDetail";
import ScrollToTop from "./components/scrollToTop/ScrollToTop";
import Classic from "./components/munhwa/Classic";
import MyPage from "./components/myPage/MyPage";
import ModifyMyPage from "./components/myPage/ModifyMyPage";
import Input_signup from "./routes/Input_signup";
import KakaoAuthHandler from "./routes/KakaoAuthHandler";
import Logout from "./routes/Logout";
import ReviewBoard from "./components/review/ReviewBoard";
import CreatePost from "./components/review/CreatePost";
import ReviewDetail from "./components/review/ReviewDetail";
import CreatePostSelf from "./components/review/CreatePostSelf";
import CultureFriend from "./components/cultureFriend/CultureFriend";
import CultureFriendDetail from "./components/cultureFriend/CultureFriendDetail";
import CultureFriendPost from "./components/cultureFriend/CultureFriendPost";
import CultureRequest from "./components/cultureRequest/CultureRequest";
import CultureRequestDetail from "./components/cultureRequest/CultureRequestDetail";
import CultureRequestPost from "./components/cultureRequest/CultureRequestPost";

function App(props) {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <ScrollToTop />
      <Layout>
        <Routes>
          <Route path="/" element={<Index_home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/sign_up" element={<Sign_up />} />
          <Route path="/munhwaRow/:id" element={<MunhwaDetail />} />
          <Route path="/culture/favorite" element={<Classic />} />
          <Route path="/culture/:categoryName" element={<Musical />} />
          <Route path="/MyPage" element={<MyPage />} />
          <Route path="/ModifyMyPage" element={<ModifyMyPage />} />
          <Route path="/Input_signup" element={<Input_signup />} />
          <Route path="/auth/kakao/callback" element={<KakaoAuthHandler />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/ReviewBoard" element={<ReviewBoard />} />
          <Route path="/CreatePost" element={<CreatePost />} />
          <Route path="/CreatePostSelf" element={<CreatePostSelf />} />
          <Route path="/ReviewDetail/:id" element={<ReviewDetail />} />
          <Route path="/CultureFriend" element={<CultureFriend />} />
          <Route
            path="/CultureFriendDetail/:id"
            element={<CultureFriendDetail />}
          />
          <Route path="/CultureFriendPost" element={<CultureFriendPost />} />
          <Route path="/CultureFriend/:id" element={<CultureFriend />} />
          <Route path="/CultureRequest" element={<CultureRequest />} />
          <Route
            path="/CultureRequestDetail/:id"
            element={<CultureRequestDetail />}
          />
          <Route path="/CultureRequestPost" element={<CultureRequestPost />} />
          <Route path="/CultureRequest/:id" element={<CultureRequest />} />
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;