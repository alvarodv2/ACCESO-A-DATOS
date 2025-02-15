import { useState, useEffect, useCallback } from 'react';
import { Paper, Typography, Button } from '@mui/material';
import Grid from '@mui/material/Grid';
import Casilla from './Casilla';

const Tablero: React.FC = () => {
  const [casillas, setCasillas] = useState<(string | null)[]>(Array(9).fill(null));
  const [jugadorActual, setJugadorActual] = useState<'X' | 'O'>('X');
  const [ganador, setGanador] = useState<string | null>(null);

  const combinacionesGanadoras = [
    [0, 1, 2], [3, 4, 5], [6, 7, 8],
    [0, 3, 6], [1, 4, 7], [2, 5, 8],
    [0, 4, 8], [2, 4, 6]
  ];

  const verificarGanador = useCallback((tablero: (string | null)[]) => {
    for (const [a, b, c] of combinacionesGanadoras) {
      if (tablero[a] && tablero[a] === tablero[b] && tablero[a] === tablero[c]) {
        return tablero[a];
      }
    }
    return null;
  }, []);

  const handleClick = (index: number) => {
    if (casillas[index] || ganador) return;

    const nuevoTablero = [...casillas];
    nuevoTablero[index] = jugadorActual;
    setCasillas(nuevoTablero);
    setJugadorActual(jugadorActual === 'X' ? 'O' : 'X'); // Corregido
  };

  useEffect(() => {
    const posibleGanador = verificarGanador(casillas);
    if (posibleGanador) {
      setGanador(posibleGanador);
    }
  }, [casillas, verificarGanador]);

  const reiniciarJuego = () => {
    setCasillas(Array(9).fill(null));
    setJugadorActual('X');
    setGanador(null);
  };

  return (
    <Paper 
      elevation={3} 
      sx={{ 
        p: 2,
        width: '100%',
        maxWidth: '400px',
        margin: '0 auto'
      }}
    >
      <Grid container direction="column" spacing={2} sx={{ width: '100%' }}>
        <Grid container justifyContent="space-between" alignItems="center" flexWrap="wrap" spacing={1}>
          <Grid item>
            <Typography 
              variant="h6" 
              sx={{ 
                fontSize: { xs: '1rem', sm: '1.25rem' }
              }}
            >
              {ganador 
                ? `🏆 ¡Jugador ${ganador} gana!` 
                : `Siguiente: Jugador ${jugadorActual}`
              }
            </Typography>
          </Grid>
          <Grid item>
            <Button 
              variant="contained" 
              color="success"
              onClick={reiniciarJuego}
              size="small"
            >
              Nuevo Juego
            </Button>
          </Grid>
        </Grid>
        
        <Grid 
          container 
          spacing={1} 
          sx={{ 
            display: 'grid',
            gridTemplateColumns: 'repeat(3, 1fr)',
            gap: 1,
            width: '100%',
            justifyItems: 'center'
          }}
        >
          {casillas.map((valor, index) => (
            <Grid item key={index}>
              <Casilla
                valor={valor}
                onCasillaClick={() => handleClick(index)}
                disabled={!!ganador}
              />
            </Grid>
          ))}
        </Grid>
      </Grid>
    </Paper>
  );
};

export default Tablero;
