import './App.css'
import Tablero from './components/Tablero'
import { Container, Typography } from '@mui/material'
import { GameProvider } from './context/useContext'

function App() {
  return (
    <GameProvider>
      <Container 
        maxWidth="sm" 
        sx={{ 
          py: 2,
          px: { xs: 1, sm: 2 }
        }}
      >
        <Typography 
          variant="h3" 
          component="h1" 
          align="center" 
          gutterBottom
          sx={{
            fontSize: { xs: '2rem', sm: '3rem' }
          }}
        >
          TRES EN RAYA
        </Typography>
        <Tablero />
      </Container>
    </GameProvider>
  )
}

export default App