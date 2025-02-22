import React, { createContext, useState, useContext } from 'react';

interface UserData {
    nombre: string;
    email: string;
    telefono: string;
}

interface UserContextType {
    userData: UserData;
    setUserData: (data: UserData) => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const UserProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [userData, setUserData] = useState<UserData>({
        nombre: '',
        email: '',
        telefono: ''
    });

    return (
        <UserContext.Provider value={{ userData, setUserData }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error('useUserContext debe ser usado dentro de un UserProvider');
    }
    return context;
};