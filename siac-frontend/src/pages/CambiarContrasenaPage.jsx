import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { cambiarContrasena } from "../services/usuarioService";
import "../styles/usuariosPage.css";

function CambiarContrasenaPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    contrasenaActual: "",
    nuevaContrasena: "",
    confirmarContrasena: "",
  });

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setForm({
      ...form,
      [name]: value,
    });
  };

  const guardar = async (e) => {
    e.preventDefault();

    try {
      await cambiarContrasena(form);
      alert("Contraseña actualizada correctamente");
      navigate("/dashboard");
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div className="usuarios-page">
      <header className="usuarios-header">
        <div>
          <h1>Cambiar contraseña</h1>
          <p>Actualiza la contraseña de tu cuenta.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <div className="modal-card" style={{ margin: "0 auto" }}>
        <form className="usuario-form" onSubmit={guardar}>
          <input
            type="password"
            name="contrasenaActual"
            placeholder="Contraseña actual"
            value={form.contrasenaActual}
            onChange={manejarCambio}
            required
          />

          <input
            type="password"
            name="nuevaContrasena"
            placeholder="Nueva contraseña"
            value={form.nuevaContrasena}
            onChange={manejarCambio}
            required
          />

          <input
            type="password"
            name="confirmarContrasena"
            placeholder="Confirmar nueva contraseña"
            value={form.confirmarContrasena}
            onChange={manejarCambio}
            required
          />

          <div className="acciones">
            <button className="btn-primary" type="submit">
              Cambiar contraseña
            </button>

            <button
              className="btn-secondary"
              type="button"
              onClick={() => navigate("/dashboard")}
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CambiarContrasenaPage;