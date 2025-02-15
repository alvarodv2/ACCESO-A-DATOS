import { Paper, Typography, Button } from '@mui/material';
import Grid2 from '@mui/material/Grid2';
import Casilla from './Casilla';
import { useGame } from '../context/useContext';

const Tablero: React.FC = () => {
  const { casillas, jugadorActual, ganador, handleClick, reiniciarJuego } = useGame();

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
      <Grid2 container direction="column" spacing={2} sx={{ width: '100%' }}>
        <Grid2 container justifyContent="space-between" alignItems="center" flexWrap="wrap" spacing={1}>
          <Grid2>
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
          </Grid2>
          <Grid2>
            <Button 
              variant="contained" 
              color="success"
              onClick={reiniciarJuego}
              size="small"
            >
              Nuevo Juego
            </Button>
          </Grid2>
        </Grid2>
        
        <Grid2 
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
            <Grid2 key={index}>
              <Casilla
                valor={valor}
                onCasillaClick={() => handleClick(index)}
                disabled={!!ganador}
              />
            </Grid2>
          ))}
        </Grid2>
      </Grid2>
    </Paper>
  );
};

export default Tablero;