import { useUserContext } from '../context/UserContext';
import './Profile.css';

const Profile = () => {
    const { userData } = useUserContext();

    return (
        <div className="profile-container">
            <h2>Perfil de Usuario</h2>
            <div className="profile-data">
                <div className="data-item">
                    <h3>Nombre</h3>
                    <p>{userData.nombre || 'No disponible'}</p>
                </div>
                <div className="data-item">
                    <h3>Email</h3>
                    <p>{userData.email || 'No disponible'}</p>
                </div>
                <div className="data-item">
                    <h3>Teléfono</h3>
                    <p>{userData.telefono || 'No disponible'}</p>
                </div>
            </div>
        </div>
    );
};

export default Profile;