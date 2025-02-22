import React from 'react';
import { useUserContext } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
import './RegisterForm.css';

const RegisterForm = () => {
    const { setUserData } = useUserContext();
    const navigate = useNavigate();

    const onSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget as HTMLFormElement);
        
        const userData = {
            nombre: formData.get('nombre') as string,
            email: formData.get('email') as string,
            telefono: formData.get('telefono') as string
        };

        setUserData(userData);
        navigate('/perfil'); // Redirige al perfil después del registro
    };

    return (
        <div className="form-container">
            <h2>Registro de Usuario</h2>
            <form onSubmit={onSubmit}>
                <div className="form-group">
                    <label htmlFor="nombre">Nombre:</label>
                    <input
                        type="text"
                        id="nombre"
                        name="nombre"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        name="email"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="telefono">Teléfono:</label>
                    <input
                        type="tel"
                        id="telefono"
                        name="telefono"
                        required
                    />
                </div>

                <button type="submit">Registrar</button>
            </form>
        </div>
    );
};

export default RegisterForm;