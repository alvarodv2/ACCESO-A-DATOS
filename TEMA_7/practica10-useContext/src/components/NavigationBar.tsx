import { Link } from 'react-router-dom';
import './NavigationBar.css';

const NavigationBar = () => {
    return (
        <nav className="navbar">
            <ul>
                <li><Link to="/home">Inicio</Link></li>
                <li><Link to="/registro">Registro</Link></li>
                <li><Link to="/perfil">Perfil</Link></li>
                <li><Link to="/about">Sobre Nosotros</Link></li>
            </ul>
        </nav>
    );
};

export default NavigationBar;