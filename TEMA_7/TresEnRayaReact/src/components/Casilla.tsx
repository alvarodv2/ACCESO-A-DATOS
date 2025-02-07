import { Button } from '@mui/material';
import { Close as CloseIcon } from '@mui/icons-material';
import { RadioButtonUnchecked as RadioButtonUncheckedIcon } from '@mui/icons-material';

interface CasillaProps {
  valor: string | null;
  onCasillaClick: () => void;
  disabled: boolean;
}

const Casilla: React.FC<CasillaProps> = ({ valor, onCasillaClick, disabled }) => {
  return (
    <Button 
      variant="outlined"
      onClick={onCasillaClick}
      disabled={disabled}
      sx={{
        width: { xs: '60px', sm: '100px' },
        height: { xs: '60px', sm: '100px' },
        fontSize: '2rem',
        border: '2px solid #1976d2',
        minWidth: 'unset',
        padding: 0,
        '&:hover': {
          border: '2px solid #1976d2',
          backgroundColor: 'rgba(25, 118, 210, 0.04)'
        }
      }}
    >
      {valor === 'X' && <CloseIcon sx={{ fontSize: { xs: 30, sm: 40 }, color: 'red' }} />}
      {valor === 'O' && <RadioButtonUncheckedIcon sx={{ fontSize: { xs: 25, sm: 35 }, color: '#1565c0' }} />}
    </Button>
  );
};

export default Casilla;