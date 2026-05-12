import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  obtenerImagenesProductoActivas,
  obtenerImagenesProductoInactivas,
  crearImagenProducto,
  actualizarImagenProducto,
  desactivarImagenProducto,
  activarImagenProducto,
} from "../services/imagenesProductoService";
import {
  canManageImagenesProducto,
  canViewImagenesProducto,
} from "../auth/roleUtils";
import "../styles/imagenesProductoPage.css";

const initialForm = {
  productoId: "",
  ruta: "",
  nombreArchivo: "",
};

function ImagenesProductoPage() {
  const navigate = useNavigate();

  const [imagenes, setImagenes] = useState([]);
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);
  const [mostrarInactivas, setMostrarInactivas] = useState(false);
  const [mensaje, setMensaje] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    cargarImagenesActivas();
  }, []);

  const cargarImagenesActivas = async () => {
    try {
      setError("");
      const data = await obtenerImagenesProductoActivas();
      setImagenes(data.content || data || []);
      setMostrarInactivas(false);
    } catch (err) {
      setError(err.message);
    }
  };

  const cargarImagenesInactivas = async () => {
    try {
      setError("");
      const data = await obtenerImagenesProductoInactivas();
      setImagenes(data.content || data || []);
      setMostrarInactivas(true);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const limpiarFormulario = () => {
    setForm(initialForm);
    setEditingId(null);
  };

  const handleEdit = (imagen) => {
    setEditingId(imagen.id);

    setForm({
      productoId: imagen.productoId || "",
      ruta: imagen.ruta || "",
      nombreArchivo: imagen.nombreArchivo || "",
    });

    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setMensaje("");
      setError("");

      const request = {
        productoId: Number(form.productoId),
        ruta: form.ruta,
        nombreArchivo: form.nombreArchivo,
      };

      if (editingId) {
        await actualizarImagenProducto(editingId, request);
        setMensaje("Imagen actualizada correctamente");
      } else {
        await crearImagenProducto(request);
        setMensaje("Imagen registrada correctamente");
      }

      limpiarFormulario();

      if (mostrarInactivas) {
        cargarImagenesInactivas();
      } else {
        cargarImagenesActivas();
      }
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeactivate = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar esta imagen?"
    );

    if (!confirmar) return;

    try {
      await desactivarImagenProducto(id);

      setMensaje("Imagen desactivada correctamente");
      setError("");

      cargarImagenesActivas();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleActivate = async (id) => {
    try {
      await activarImagenProducto(id);

      setMensaje("Imagen activada correctamente");
      setError("");

      cargarImagenesInactivas();
    } catch (err) {
      setError(err.message);
    }
  };

  if (!canViewImagenesProducto()) {
    return (
      <div className="imagenes-page">
        <section className="imagenes-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>No tienes permiso para consultar imágenes de producto.</p>
          </div>

          <button
            className="btn-secondary"
            onClick={() => navigate("/dashboard")}
          >
            Volver al dashboard
          </button>
        </section>
      </div>
    );
  }

  return (
    <div className="imagenes-page">
      <section className="imagenes-header">
        <div>
          <h1>Imágenes de producto</h1>
          <p>Administra las imágenes asociadas a los productos de SIAC.</p>
        </div>

        <button
          className="btn-secondary"
          onClick={() => navigate("/dashboard")}
        >
          Volver al dashboard
        </button>
      </section>

      {mensaje && <div className="alert success">{mensaje}</div>}
      {error && <div className="alert error">{error}</div>}

      {canManageImagenesProducto() && (
        <>
          <section className="imagenes-actions">
            <button
              className="btn-primary"
              onClick={limpiarFormulario}
            >
              Agregar imagen
            </button>

            {!mostrarInactivas ? (
              <button
                className="btn-secondary"
                onClick={cargarImagenesInactivas}
              >
                Ver inactivas
              </button>
            ) : (
              <button
                className="btn-secondary"
                onClick={cargarImagenesActivas}
              >
                Ver activas
              </button>
            )}
          </section>

          <section className="form-card">
            <h2>{editingId ? "Editar imagen" : "Agregar imagen"}</h2>

            <form onSubmit={handleSubmit} className="imagenes-form">
              <input
                type="number"
                name="productoId"
                placeholder="ID del producto"
                value={form.productoId}
                onChange={handleChange}
                required
              />

              <input
                type="text"
                name="ruta"
                placeholder="Ruta. Ejemplo: /productos/pikachu-v.jpg"
                value={form.ruta}
                onChange={handleChange}
                required
              />

              <input
                type="text"
                name="nombreArchivo"
                placeholder="Nombre del archivo"
                value={form.nombreArchivo}
                onChange={handleChange}
                required
              />

              <div className="form-actions">
                <button type="submit" className="btn-primary">
                  {editingId ? "Actualizar" : "Guardar"}
                </button>

                {editingId && (
                  <button
                    type="button"
                    className="btn-cancel"
                    onClick={limpiarFormulario}
                  >
                    Cancelar
                  </button>
                )}
              </div>
            </form>
          </section>
        </>
      )}

      <section className="table-card">
        <table className="imagenes-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Preview</th>
              <th>Producto</th>
              <th>Ruta</th>
              <th>Archivo</th>
              <th>Fecha registro</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {imagenes.length === 0 ? (
              <tr>
                <td colSpan="8" className="empty">
                  No hay imágenes registradas.
                </td>
              </tr>
            ) : (
              imagenes.map((imagen) => (
                <tr key={imagen.id}>
                  <td>{imagen.id}</td>

                  <td>
                    <img
                      src={imagen.ruta}
                      alt={imagen.nombreArchivo}
                      className="imagen-preview"
                    />
                  </td>

                  <td>
                    <strong>{imagen.productoNombre}</strong>
                    <br />
                    <span>ID: {imagen.productoId}</span>
                  </td>

                  <td className="ruta-cell">{imagen.ruta}</td>
                  <td>{imagen.nombreArchivo}</td>

                  <td>
                    {imagen.fechaRegistro
                      ? new Date(imagen.fechaRegistro).toLocaleString()
                      : "Sin fecha"}
                  </td>

                  <td>
                    <span
                      className={
                        imagen.activo ? "badge active" : "badge inactive"
                      }
                    >
                      {imagen.activo ? "Activa" : "Inactiva"}
                    </span>
                  </td>

                  <td>
                    <div className="actions">
                      <a
                        href={imagen.ruta}
                        target="_blank"
                        rel="noreferrer"
                        className="btn-view"
                      >
                        Ver
                      </a>

                      {canManageImagenesProducto() && imagen.activo && (
                        <>
                          <button
                            className="btn-edit"
                            onClick={() => handleEdit(imagen)}
                          >
                            Editar
                          </button>

                          <button
                            className="btn-delete"
                            onClick={() => handleDeactivate(imagen.id)}
                          >
                            Desactivar
                          </button>
                        </>
                      )}

                      {canManageImagenesProducto() && !imagen.activo && (
                        <button
                          className="btn-activate"
                          onClick={() => handleActivate(imagen.id)}
                        >
                          Activar
                        </button>
                      )}
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </section>
    </div>
  );
}

export default ImagenesProductoPage;