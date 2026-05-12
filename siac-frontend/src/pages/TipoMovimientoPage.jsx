import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  obtenerTiposMovimiento,
  crearTipoMovimiento,
  actualizarTipoMovimiento,
} from "../services/tipoMovimientoService";
import { canManageTiposMovimiento } from "../auth/roleUtils";
import "../styles/tipoMovimientoPage.css";

function TipoMovimientoPage() {
  const navigate = useNavigate();

  const [tipos, setTipos] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [tipoSeleccionado, setTipoSeleccionado] = useState(null);

  const [formTipo, setFormTipo] = useState({
    nombre: "",
    clave: "",
    descripcion: "",
  });

  useEffect(() => {
    cargarTipos();
  }, []);

  const cargarTipos = async () => {
    try {
      const data = await obtenerTiposMovimiento();
      setTipos(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setTipoSeleccionado(null);
    setFormTipo({
      nombre: "",
      clave: "",
      descripcion: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (tipo) => {
    setModoEdicion(true);
    setTipoSeleccionado(tipo);
    setFormTipo({
      nombre: tipo.nombre || "",
      clave: tipo.clave || "",
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
        await actualizarTipoMovimiento(tipoSeleccionado.id, formTipo);
        alert("Tipo de movimiento actualizado correctamente");
      } else {
        await crearTipoMovimiento(formTipo);
        alert("Tipo de movimiento creado correctamente");
      }

      setMostrarFormulario(false);
      cargarTipos();
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div className="tipo-movimiento-page">
      <header className="tipo-movimiento-header">
        <div>
          <h1>Tipos de Movimiento</h1>
          <p>Administra los tipos usados para entradas y salidas de inventario.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManageTiposMovimiento() && (
        <section className="tipo-movimiento-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar tipo
          </button>
        </section>
      )}

      <section className="tipo-movimiento-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Clave</th>
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
                <td>{tipo.clave}</td>
                <td>{tipo.descripcion}</td>
                <td>{tipo.activo ? "Activo" : "Inactivo"}</td>
                <td>
                  {canManageTiposMovimiento() ? (
                    <button
                      className="btn-edit"
                      onClick={() => abrirEditar(tipo)}
                    >
                      Editar
                    </button>
                  ) : (
                    <span>Solo lectura</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {tipos.length === 0 && (
          <p className="sin-registros">No hay tipos de movimiento registrados.</p>
        )}
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar tipo" : "Agregar tipo"}</h2>

            <form className="tipo-movimiento-form" onSubmit={guardarTipo}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre visible, ejemplo: Entrada"
                value={formTipo.nombre}
                onChange={manejarCambio}
                required
              />

              <input
                type="text"
                name="clave"
                placeholder="Clave del sistema, ejemplo: ENTRADA"
                value={formTipo.clave}
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

export default TipoMovimientoPage;