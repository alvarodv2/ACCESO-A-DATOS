import { createContext, useContext, useState, ReactNode } from "react";

// Tipos para TypeScript
interface GameContextType {
  casillas: (string | null) [];
  jugadorActual: "X" | "O";
  ganador: string | null;
  handleClick: (index: number) => void;
  reiniciarJuego: () => void;
}

// Creamos el contexto
const GameContext = createContext<GameContextType | undefined>(undefined);

// Hook para usar el contexto
export const useGame = () => {
  const context = useContext(GameContext);
  if (!context) {
    throw new Error("useGame debe usarse dentro de GameProvider");
  }
  return context;
};

// Provider
export const GameProvider = ({ children }: { children: ReactNode }) => {
  const [casillas, setCasillas] = useState<(string | null)[]>(Array(9).fill(null));
  const [jugadorActual, setJugadorActual] = useState<"X" | "O">("X");
  const [ganador, setGanador] = useState<string | null>(null);

  const combinacionesGanadoras = [
    [0, 1, 2], [3, 4, 5], [6, 7, 8],
    [0, 3, 6], [1, 4, 7], [2, 5, 8],
    [0, 4, 8], [2, 4, 6]
  ];

  const verificarGanador = (tablero: (string | null)[]) => {
    for (const [a, b, c] of combinacionesGanadoras) {
      if (tablero[a] && tablero[a] === tablero[b] && tablero[a] === tablero[c]) {
        return tablero[a];
      }
    }
    return null;
  };

  const handleClick = (index: number) => {
    if (casillas[index] || ganador) return;

    const nuevoTablero = [...casillas];
    nuevoTablero[index] = jugadorActual;
    setCasillas(nuevoTablero);
    setJugadorActual(jugadorActual === "X" ? "O" : "X");

    const posibleGanador = verificarGanador(nuevoTablero);
    if (posibleGanador) {
      setGanador(posibleGanador);
    }
  };

  const reiniciarJuego = () => {
    setCasillas(Array(9).fill(null));
    setJugadorActual("X");
    setGanador(null);
  };

  return (
    <GameContext.Provider value={{ casillas, jugadorActual, ganador, handleClick, reiniciarJuego }}>
      {children}
    </GameContext.Provider>
  );
};
