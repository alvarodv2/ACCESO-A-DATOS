// useDog.ts
export async function fetchDogImage(breed: string): Promise<string> {
    const API_DOG = `https://dog.ceo/api/breed/${encodeURIComponent(breed)}/images/random`;
    try {
      const response = await fetch(API_DOG);
      if (!response.ok) {
        throw new Error("No se pudo conectar con la API. Intenta de nuevo más tarde.");
      }
      const data = await response.json();
      if (data.status === "error") {
        throw new Error("Raza no encontrada. Intenta con otra raza.");
      }
      return data.message;
    } catch (error) {
      throw error instanceof Error ? error : new Error("Ocurrió un error inesperado");
    }
  }