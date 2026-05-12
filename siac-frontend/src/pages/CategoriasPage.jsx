import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  canManageCategorias
} from "../auth/roleUtils";
import {
  obtenerCategorias,
  obtenerCategoriasInactivas,
  crearCategoria,
  actualizarCategoria,
  desactivarCategoria,
  activarCategoria,
} from "../services/categoriaService";
import "../styles/categoriasPage.css";

function CategoriasPage() {
  const navigate = useNavigate();

  const [categorias, setCategorias] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [mostrarInactivas, setMostrarInactivas] = useState(false);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);

  const [formCategoria, setFormCategoria] = useState({
    nombre: "",
    descripcion: "",
  });

  useEffect(() => {
    cargarCategoriasActivas();
  }, []);

  const cargarCategoriasActivas = async () => {
    try {
      const data = await obtenerCategorias();
      setCategorias(data.content || data || []);
      setMostrarInactivas(false);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarCategoriasInactivas = async () => {
    try {
      const data = await obtenerCategoriasInactivas();
      setCategorias(data.content || data || []);
      setMostrarInactivas(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setCategoriaSeleccionada(null);
    setFormCategoria({
      nombre: "",
      descripcion: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (categoria) => {
    setModoEdicion(true);
    setCategoriaSeleccionada(categoria);
    setFormCategoria({
      nombre: categoria.nombre || "",
      descripcion: categoria.descripcion || "",
    });
    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormCategoria({
      ...formCategoria,
      [name]: value,
    });
  };

  const guardarCategoria = async (e) => {
    e.preventDefault();

    try {
      if (modoEdicion) {
        await actualizarCategoria(categoriaSeleccionada.id, formCategoria);
        alert("Categoría actualizada correctamente");
      } else {
        await crearCategoria(formCategoria);
        alert("Categoría creada correctamente");
      }

      setMostrarFormulario(false);
      cargarCategoriasActivas();
    } catch (error) {
      alert(error.message);
    }
  };

  const eliminarCategoria = async (id) => {
    const confirmar = window.confirm("¿Seguro que deseas desactivar esta categoría?");

    if (!confirmar) return;

    try {
      await desactivarCategoria(id);
      alert("Categoría desactivada correctamente");
      cargarCategoriasActivas();
    } catch (error) {
      alert(error.message);
    }
  };

  const reactivarCategoria = async (id) => {
    try {
      await activarCategoria(id);
      alert("Categoría activada correctamente");
      cargarCategoriasInactivas();
    } catch (error) {
      alert(error.message);
    }
  };

  const categoriasFiltradas = categorias.filter((categoria) =>
    categoria.nombre?.toLowerCase().includes(busqueda.toLowerCase())
  );

  return (
    <div className="categorias-page">
      <header className="categorias-header">
        <div>
          <h1>Gestión de Categorías</h1>
          <p>Administra las categorías de productos coleccionables.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManageCategorias() && (
        <section className="categorias-actions">

          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar categoría
          </button>

          {!mostrarInactivas ? (
            <button className="btn-secondary" onClick={cargarCategoriasInactivas}>
              Ver inactivas
            </button>
          ) : (
            <button className="btn-secondary" onClick={cargarCategoriasActivas}>
              Ver activas
            </button>
          )}

        </section>
      )}

      <section className="categorias-filter">
        <input
          type="text"
          placeholder="Buscar categoría..."
          value={busqueda}
          onChange={(e) => setBusqueda(e.target.value)}
        />
      </section>

      <section className="categorias-table-card">
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
            {categoriasFiltradas.map((categoria) => (
              <tr key={categoria.id}>

                <td>{categoria.id}</td>

                <td>{categoria.nombre}</td>

                <td>{categoria.descripcion}</td>

                <td>{categoria.activo ? "Activa" : "Inactiva"}</td>

                <td>

                  {canManageCategorias() ? (

                    !mostrarInactivas ? (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() => abrirEditar(categoria)}
                        >
                          Editar
                        </button>

                        <button
                          className="btn-delete"
                          onClick={() => eliminarCategoria(categoria.id)}
                        >
                          Desactivar
                        </button>
                      </>
                    ) : (
                      <button
                        className="btn-edit"
                        onClick={() => reactivarCategoria(categoria.id)}
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
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar categoría" : "Agregar categoría"}</h2>

            <form className="categoria-form" onSubmit={guardarCategoria}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre"
                value={formCategoria.nombre}
                onChange={manejarCambio}
                required
              />

              <textarea
                name="descripcion"
                placeholder="Descripción"
                value={formCategoria.descripcion}
                onChange={manejarCambio}
                required
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear categoría"}
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

export default CategoriasPage;