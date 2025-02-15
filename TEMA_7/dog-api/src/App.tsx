import React, { useState, useEffect } from "react";
import {Container, Typography, Box, TextField, Alert, Card, CardMedia, CardContent } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { fetchDogImage } from "./components/useDog";


function App() {
  const [breed, setBreed] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState({ error: false, message: "" });
  const [dogImage, setDogImage] = useState(""); 
  const [shouldFetch, setShouldFetch] = useState(false); 

  // Función para manejar el cambio en el campo de texto
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setBreed(e.target.value); 
    setDogImage(""); 
    setError({ error: false, message: "" }); 
  };

  // Función para manejar el clic en el botón "Buscar"
  const handleSearch = () => {
    if (!breed.trim()) {
      setError({ error: true, message: "El campo raza es obligatorio" });
      return;
    }
    setShouldFetch(true); 
  };

  // useEffect para realizar la llamada a la API
  useEffect(() => {
    if (!shouldFetch || !breed.trim()) return;

    setLoading(true); 
    setError({ error: false, message: "" }); 

    const fetchImage = async () => {
      try {
        const image = await fetchDogImage(breed); 
        setDogImage(image); 
      } catch (ex) {
        setError({ error: true, message: ex.message }); 
      } finally {
        setLoading(false);
        setShouldFetch(false); 
      }
    };

    fetchImage();
  }, [shouldFetch, breed]); 

  return (
    <Container maxWidth="xs" sx={{ mt: 2 }}>
      <Box className="card" sx={{ p: 3, borderRadius: 2 }}>
        <Typography variant="h3" component="h1" align="center" gutterBottom>
          DOG API
        </Typography>
        <Box
          sx={{ display: "grid", gap: 2 }}
          component="form"
          autoComplete="off"
        >
          <Typography variant="body1" sx={{ mb: 1 }}>
            Raza del perro a devolver
          </Typography>
          <TextField
            id="breed"
            variant="outlined"
            fullWidth
            size="small"
            value={breed}
             // Actualizamos el estado de breed
            onChange={handleInputChange}
            placeholder="Ingresa una raza de perro"
            sx={{ backgroundColor: "white", borderRadius: 1 }}
          />
          <LoadingButton
            type="button"
            variant="contained"
            loading={loading}
            loadingIndicator="Cargando..."
            // Llamamos a la function de busqueda al hacer click
            onClick={handleSearch} 
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