import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/authService";
import logo from "../assets/logo-siac.png";

function Login() {

  const [correo, setCorreo] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();

    if (!correo.trim() || !contrasena.trim()) {
      setError("Ingresa correo y contraseña para continuar.");
      return;
    }

    try {
      setError("");

      const respuesta = await login(correo, contrasena);

      console.log("Respuesta del backend:", respuesta);

      localStorage.setItem("isAuthenticated", "true");
      localStorage.setItem("correo", correo);

      navigate("/dashboard");

    } catch (error) {

      console.error(error);

      if (error.message === "Failed to fetch") {
        setError("No se pudo conectar con el servidor.");
      } else {
        setError(error.message);
      }
    }
  };

  return (
    <div className="login-container">

      <div className="login-box">

        <img
          src={logo}
          alt="Logo SIAC"
          className="login-logo"
        />

        <h1>Acceso a SIAC</h1>

        <p>Inicia sesión para continuar</p>

        {error && (
          <p className="error-message">
            {error}
          </p>
        )}

        <form onSubmit={handleLogin}>

          <div className="form-group">

            <label>Correo electrónico</label>

            <input
              type="email"
              placeholder="Ingresa tu correo"
              value={correo}
              onChange={(e) => setCorreo(e.target.value)}
            />

          </div>

          <div className="form-group">

            <label>Contraseña</label>

            <input
              type="password"
              placeholder="Ingresa tu contraseña"
              value={contrasena}
              onChange={(e) => setContrasena(e.target.value)}
            />

          </div>

          <button
            className="login-button"
            type="submit"
          >
            Iniciar sesión
          </button>

        </form>

      </div>

    </div>
  );
}

export default Login;