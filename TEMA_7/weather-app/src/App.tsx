import React, { useState } from "react";
import { Container, Typography, Box, TextField, Alert } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { useWeather } from "./components/useWeather";

function App() {
  const [city, setCity] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [shouldSearch, setShouldSearch] = useState(false);
  const { loading, error, weather } = useWeather(searchTerm, shouldSearch);

  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setSearchTerm(city);
    setShouldSearch(true);
  };

  return (
    <Container maxWidth="xs" sx={{ mt: 2 }}>
      <Box className="card" sx={{ p: 3, borderRadius: 2 }}>
        <Typography variant="h3" component="h1" align="center" gutterBottom>
          WEATHER APP
        </Typography>

        <Box
          sx={{ display: "grid", gap: 2 }}
          component="form"
          autoComplete="off"
          onSubmit={onSubmit}
        >
          <Typography variant="body1" sx={{ mb: 1 }}>
            CIUDAD
          </Typography>

          <TextField
            id="city"
            variant="outlined"
            required
            fullWidth
            size="small"
            value={city}
            onChange={(e) => setCity(e.target.value)}
            placeholder="Ingresa una ciudad"
            sx={{ backgroundColor: "white", borderRadius: 1 }}
          />

          <LoadingButton
            type="submit"
            variant="contained"
            loading={loading}
            loadingIndicator="Cargando..."
            sx={{ mt: 2 }}
          >
            Buscar
          </LoadingButton>
        </Box>

        {error.error && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {error.message}
          </Alert>
        )}

        {weather.city && (
          <Box sx={{ mt: 2, display: "grid", gap: 2, textAlign: "center" }}>
            <Typography variant="h4" component="h2">
              {weather.city}, {weather.country}
            </Typography>
            <Box
              component="img"
              alt={weather.conditionText}
              src={weather.icon}
              sx={{ margin: "0 auto" }}
            />
            <Typography variant="h2" component="h2">
              {weather.temp}ºC
            </Typography>
            <Typography variant="h5" component="h5">
              {weather.conditionText}
            </Typography>
          </Box>
        )}
      </Box>
    </Container>
  );
}

export default App;