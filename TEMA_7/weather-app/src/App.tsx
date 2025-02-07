import React, { useState } from "react";
import {
  Container,
  Typography,
  Box,
  TextField,
  Alert,
  Card,
  CardMedia,
  CardContent,
} from "@mui/material";
import { LoadingButton } from "@mui/lab";

function App() {
  const [breed, setBreed] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState({ error: false, message: "" });
  const [dogImage, setDogImage] = useState("");

  const API_DOG = `https://dog.ceo/api/breed/${encodeURIComponent(breed)}/images/random`;

  const onSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError({ error: false, message: "" });

    try {
      if (!breed.trim()) throw new Error("El campo raza es obligatorio");

      const response = await fetch(API_DOG);

      // Verificacion si es (200 OK)
      if (!response.ok) {
        throw new Error("No se pudo conectar con la API. Intenta de nuevo más tarde.");
      }

      const data = await response.json();

      if (data.status === "error") {
        throw new Error("Raza no encontrada. Intenta con otra raza.");
      }

      setDogImage(data.message);
    } catch (ex) {
      // Asegurarnos de que `ex` sea un objeto Error
      const errorMessage = ex instanceof Error ? ex.message : "Ocurrió un error inesperado";
      setError({ error: true, message: errorMessage });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="xs" sx={{ mt: 2 }}>
      {/* Contenedor principal con la clase .card */}
      <Box className="card" sx={{ p: 3, borderRadius: 2 }}>
        {/* Título */}
        <Typography variant="h3" component="h1" align="center" gutterBottom>
          DOG API
        </Typography>

        {/* Formulario */}
        <Box
          sx={{ display: "grid", gap: 2 }}
          component="form"
          autoComplete="off"
          onSubmit={onSubmit}
        >
          {/* Texto "Raza*" */}
          <Typography variant="body1" sx={{ mb: 1 }}>
            Raza del perro a devolver
          </Typography>

          {/* Campo de entrada para la raza */}
          <TextField
            id="breed"
            variant="outlined"
            required
            fullWidth
            size="small"
            value={breed}
            onChange={(e) => setBreed(e.target.value)}
            placeholder="Ingresa una raza de perro"
            sx={{ backgroundColor: "white", borderRadius: 1 }} // Fondo blanco para el input
          />

          {/* Botón de búsqueda */}
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

        {/* Mostrar errores */}
        {error.error && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {error.message}
          </Alert>
        )}

        {/* Mostrar la imagen del perro */}
        {dogImage && (
          <Box sx={{ display: "flex", justifyContent: "center", mt: 2 }}>
            <Box sx={{ width: "100%", maxWidth: 400 }}>
              <Card>
                <CardMedia
                  component="img"
                  image={dogImage}
                  alt="Imagen de perro"
                  sx={{ height: "100%", width: "100%", objectFit: "cover" }}
                />
                <CardContent>
                  <Typography variant="h5" component="h2" align="center">
                    {breed}
                  </Typography>
                </CardContent>
              </Card>
            </Box>
          </Box>
        )}
      </Box>
    </Container>
  );
}

export default App;