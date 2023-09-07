import { func } from "prop-types";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./routes/Home";
import Login from "./routes/Login";
import Sign_up from "./routes/Sign_up";
import { Link } from "react-router-dom";
import Index_home from "./pages/Index_home";
import Layout from "./components/layout/Layout";
import Whyso from "./routes/genres/Whyso";
import Musical from "./components/munhwa/Musical";
import MunhwaDetail from "./components/munhwa/MunhwaDetail";
import ScrollToTop from "./components/scrollToTop/ScrollToTop";
import CultureAndCulture from "./components/munhwa/CultureAndCulture";
import Classic from "./components/munhwa/Classic";
import Concert from "./components/munhwa/Concert";
import Dance from "./components/munhwa/Dance";
import EducationExperience from "./components/munhwa/EducationExperience";
import ExhibitionArt from "./components/munhwa/ExhibitionArt";
import Festival_citizenHarmony from "./components/munhwa/Festival_citizenHarmony";
import Festival_cultureArt from "./components/munhwa/Festival_cultureArt";
import Festival_Other from "./components/munhwa/Festival_Other";
import Festival_traditionsHistory from "./components/munhwa/Festival_traditionsHistory";
import KoreanTraditionalMusic from "./components/munhwa/KoreanTraditionalMusic";
import Movies from "./components/munhwa/Movies";
import Others from "./components/munhwa/Others";
import Play from "./components/munhwa/Play";
import Festival from "./components/munhwa/Festival";
import Solo_SingingParty from "./components/munhwa/Solo_SingingParty";
import MyPage from "./components/myPage/MyPage";
import ModifyMyPage from "./components/myPage/ModifyMyPage";
import Input_signup from "./routes/Input_signup";
import Logout from "./routes/Logout";
import KakaoAuthHandler from "./routes/KakaoAuthHandler";

function App(props) {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <ScrollToTop />
      <Layout>
        <Routes>
          <Route path="/" element={<Index_home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/sign_up" element={<Sign_up />} />
          <Route path="/musical" element={<Musical />} />
          <Route path="/munhwaRow/:id" element={<MunhwaDetail />} />
          <Route path="/cultureAndCulture" element={<CultureAndCulture />} />
          <Route path="/Classic" element={<Classic />} />
          <Route path="/Concert" element={<Concert />} />
          <Route path="/Dance" element={<Dance />} />
          <Route path="/Education" element={<EducationExperience />} />
          <Route path="/Exhibition" element={<ExhibitionArt />} />
          <Route
            path="/Festival_citizen"
            element={<Festival_citizenHarmony />}
          />
          <Route path="/Festival_culture" element={<Festival_cultureArt />} />
          <Route path="/Festival_Other" element={<Festival_Other />} />
          <Route
            path="/Festival_traditional"
            element={<Festival_traditionsHistory />}
          />
          <Route
            path="/KoreanTraditionalMusic"
            element={<KoreanTraditionalMusic />}
          />
          <Route path="/Movies" element={<Movies />} />
          <Route path="/Others" element={<Others />} />
          <Route path="/Play" element={<Play />} />
          <Route path="/Festival" element={<Festival />} />
          <Route path="/Solo_SingingParty" element={<Solo_SingingParty />} />
          <Route path="/MyPage" element={<MyPage />} />
          <Route path="/ModifyMyPage" element={<ModifyMyPage />} />
          <Route path="/Input_signup" element={<Input_signup />} />
          <Route path="/auth/kakao/callback" element={<KakaoAuthHandler />} />
          <Route path="/Logout" element={<Logout />} />
          {/* <Route path="/Layout" element={<Layout />} /> */}
        </Routes>
      </Layout>
    </Router>
  );
}

export default App;
