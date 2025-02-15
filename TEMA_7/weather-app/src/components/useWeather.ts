import { useState, useEffect } from "react";

interface Weather {
  city: string;
  country: string;
  temp: number;
  condition: string;
  icon: string;
  conditionText: string;
}

interface WeatherError {
  error: boolean;
  message: string;
}

export const useWeather = (searchCity: string, shouldSearch: boolean) => {
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<WeatherError>({ error: false, message: "" });
  const [weather, setWeather] = useState<Weather>({
    city: "",
    country: "",
    temp: 0,
    condition: "",
    icon: "",
    conditionText: "",
  });

  useEffect(() => {
    const fetchWeather = async () => {
      if (!searchCity.trim() || !shouldSearch) return;
      
      setLoading(true);
      setError({ error: false, message: "" });

      try {
        const API_WEATHER = `https://api.weatherapi.com/v1/current.json?key=${
          import.meta.env.VITE_API_KEY
        }&q=${searchCity}`;

        const response = await fetch(API_WEATHER);
        const data = await response.json();

        if (data.error) throw { message: data.error.message };

        setWeather({
          city: data.location.name,
          country: data.location.country,
          temp: data.current.temp_c,
          condition: data.current.condition.code,
          icon: data.current.condition.icon,
          conditionText: data.current.condition.text,
        });
      } catch (ex: any) {
        setError({ error: true, message: ex.message });
      } finally {
        setLoading(false);
      }
    };

    fetchWeather();
    // Se ejecuta cuando cambia "searchCity" "shouldSearch"
  }, [searchCity, shouldSearch]); 

  return { loading, error, weather };
};