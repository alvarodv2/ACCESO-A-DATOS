import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import RegisterForm from './components/RegisterForm';
import Profile from './components/Profile';
import { UserProvider } from './context/UserContext';
import './App.css';

// Componentes adicionales
const Home = () => <div className="page-container"><h1>Bienvenido a la practica de useContext</h1></div>;
const About = () => <div className="page-container"><h1>Ejemplo</h1></div>;

function App() {
    return (
        <UserProvider>
            <Router>
                <div className="app">
                    <NavigationBar />
                    <main>
                        <Routes>
                            <Route path="/home" element={<Home />} />
                            <Route path="/registro" element={<RegisterForm />} />
                            <Route path="/perfil" element={<Profile />} />
                            <Route path="/about" element={<About />} />
                            <Route path="/" element={<Home />} />
                        </Routes>
                    </main>
                </div>
            </Router>
        </UserProvider>
    );
}

export default App;