import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  obtenerTiposCarta,
  obtenerTiposCartaInactivos,
  crearTipoCarta,
  actualizarTipoCarta,
  desactivarTipoCarta,
  activarTipoCarta,
} from "../services/tipoCartaService";
import { canManageTiposCarta } from "../auth/roleUtils";
import "../styles/tipoCartaPage.css";

function TipoCartaPage() {
  const navigate = useNavigate();

  const [tipos, setTipos] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [tipoSeleccionado, setTipoSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [formTipo, setFormTipo] = useState({
    nombre: "",
    descripcion: "",
  });

  useEffect(() => {
    cargarTiposActivos();
  }, []);

  const cargarTiposActivos = async () => {
    try {
      const data = await obtenerTiposCarta();
      setTipos(data.content || data || []);
      setMostrarInactivos(false);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarTiposInactivos = async () => {
    try {
      const data = await obtenerTiposCartaInactivos();
      setTipos(data.content || data || []);
      setMostrarInactivos(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setTipoSeleccionado(null);
    setFormTipo({
      nombre: "",
      descripcion: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (tipo) => {
    setModoEdicion(true);
    setTipoSeleccionado(tipo);
    setFormTipo({
      nombre: tipo.nombre || "",
      descripcion: tipo.descripcion || "",
    });
    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormTipo({
      ...formTipo,
      [name]: value,
    });
  };

  const guardarTipo = async (e) => {
    e.preventDefault();

    try {
      if (modoEdicion) {
        await actualizarTipoCarta(tipoSeleccionado.id, formTipo);
        alert("Tipo de carta actualizado correctamente");
      } else {
        await crearTipoCarta(formTipo);
        alert("Tipo de carta creado correctamente");
      }

      setMostrarFormulario(false);
      cargarTiposActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const eliminarTipo = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar este tipo de carta?"
    );

    if (!confirmar) return;

    try {
      await desactivarTipoCarta(id);
      alert("Tipo de carta desactivado correctamente");
      cargarTiposActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const activarTipo = async (id) => {
    try {
      await activarTipoCarta(id);
      alert("Tipo de carta activado correctamente");
      cargarTiposInactivos();
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div className="tipo-carta-page">
      <header className="tipo-carta-header">
        <div>
          <h1>Tipos de Carta</h1>
          <p>Administra los tipos de cartas coleccionables del sistema.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManageTiposCarta() && (
        <section className="tipo-carta-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar tipo de carta
          </button>

          {!mostrarInactivos ? (
            <button
              className="btn-secondary"
              onClick={cargarTiposInactivos}
            >
              Ver inactivos
            </button>
          ) : (
            <button
              className="btn-secondary"
              onClick={cargarTiposActivos}
            >
              Ver activos
            </button>
          )}
        </section>
      )}

      <section className="tipo-carta-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Descripción</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {tipos.map((tipo) => (
              <tr key={tipo.id}>
                <td>{tipo.id}</td>
                <td>{tipo.nombre}</td>
                <td>{tipo.descripcion}</td>
                <td>{tipo.activo ? "Activo" : "Inactivo"}</td>
                <td>
                  {canManageTiposCarta() ? (
                    tipo.activo ? (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() => abrirEditar(tipo)}
                        >
                          Editar
                        </button>

                        <button
                          className="btn-delete"
                          onClick={() => eliminarTipo(tipo.id)}
                        >
                          Desactivar
                        </button>
                      </>
                    ) : (
                      <button
                        className="btn-edit"
                        onClick={() => activarTipo(tipo.id)}
                      >
                        Activar
                      </button>
                    )
                  ) : (
                    <span>Solo lectura</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {tipos.length === 0 && (
          <p className="sin-registros">
            No hay tipos de carta registrados.
          </p>
        )}
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>
              {modoEdicion
                ? "Editar tipo de carta"
                : "Agregar tipo de carta"}
            </h2>

            <form className="tipo-carta-form" onSubmit={guardarTipo}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre, ejemplo: Pokémon"
                value={formTipo.nombre}
                onChange={manejarCambio}
                required
              />

              <textarea
                name="descripcion"
                placeholder="Descripción"
                value={formTipo.descripcion}
                onChange={manejarCambio}
                required
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear tipo"}
                </button>

                <button
                  className="btn-secondary"
                  type="button"
                  onClick={() => setMostrarFormulario(false)}
                >
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default TipoCartaPage;