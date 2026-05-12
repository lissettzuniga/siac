import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { canManageProductos } from "../auth/roleUtils";
import { getAccessToken } from "../services/authService";
import { obtenerCategorias } from "../services/categoriaService";

import {
  obtenerProductos as obtenerProductosService,
  obtenerProductosInactivos,
  eliminarProductoPorId,
  activarProductoPorId,
  crearProducto,
  actualizarProducto,
} from "../services/productoService";

import logo from "../assets/logo-siac.png";
import pokemon from "../assets/pokemon.png";
import "../styles/productosPage.css";

function ProductosPage() {
  const navigate = useNavigate();
  const token = getAccessToken();

  const [productos, setProductos] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [categoria, setCategoria] = useState("");
  const [precioMax, setPrecioMax] = useState("");
  const [categoriasDisponibles, setCategoriasDisponibles] = useState([]);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [mostrarDetalle, setMostrarDetalle] = useState(false);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const size = 8;

  const [formProducto, setFormProducto] = useState({
    nombre: "",
    descripcion: "",
    precio: "",
    cantidadActual: "",
    categoriaId: "",
  });

  useEffect(() => {
    cargarCategorias();
  }, []);

  useEffect(() => {
    cargarProductos();
  }, [pagina, mostrarInactivos]);

  const cargarProductos = async () => {
    try {
      const data = mostrarInactivos
        ? await obtenerProductosInactivos(pagina, size)
        : await obtenerProductosService(pagina, size);

      setProductos(data.content || []);
      setTotalPaginas(data.totalPages || 0);
    } catch (error) {
      console.error(error);
      alert(error.message || "Error al cargar productos");
    }
  };

  const cargarCategorias = async () => {
    try {
      const data = await obtenerCategorias();
      setCategoriasDisponibles(data.content || data || []);
    } catch (error) {
      console.error("Error cargando categorías:", error);
    }
  };

  const cambiarVistaProductos = () => {
    setPagina(0);
    setMostrarInactivos(!mostrarInactivos);
  };

  const abrirDetalle = (producto) => {
    setProductoSeleccionado(producto);
    setMostrarDetalle(true);
  };

  const abrirAgregar = () => {
    setProductoSeleccionado(null);
    setModoEdicion(false);
    setFormProducto({
      nombre: "",
      descripcion: "",
      precio: "",
      cantidadActual: "",
      categoriaId: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (producto) => {
    setProductoSeleccionado(producto);
    setModoEdicion(true);
    setFormProducto({
      nombre: producto.nombre || "",
      descripcion: producto.descripcion || "",
      precio: producto.precio || "",
      cantidadActual: producto.cantidadActual || "",
      categoriaId: producto.categoriaId || "",
    });
    setMostrarFormulario(true);
  };

  const manejarCambioFormulario = (e) => {
    const { name, value } = e.target;

    setFormProducto({
      ...formProducto,
      [name]: value,
    });
  };

  const guardarProducto = async (e) => {
    e.preventDefault();

    try {
      const productoRequest = {
        nombre: formProducto.nombre,
        descripcion: formProducto.descripcion,
        precio: Number(formProducto.precio),
        cantidadActual: Number(formProducto.cantidadActual),
        categoriaId: Number(formProducto.categoriaId),
      };

      if (modoEdicion) {
        await actualizarProducto(productoSeleccionado.id, productoRequest);
        alert("Producto actualizado correctamente");
      } else {
        await crearProducto(productoRequest);
        alert("Producto creado correctamente");
      }

      setMostrarFormulario(false);
      setModoEdicion(false);
      setProductoSeleccionado(null);

      await cargarProductos();
    } catch (error) {
      console.error(error);
      alert(error.message || "Error al guardar producto");
    }
  };

  const eliminarProducto = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar este producto?"
    );

    if (!confirmar) return;

    try {
      await eliminarProductoPorId(id);
      alert("Producto desactivado correctamente");
      await cargarProductos();
    } catch (error) {
      console.error(error);
      alert(error.message || "No se pudo desactivar el producto");
    }
  };

  const activarProducto = async (id) => {
    try {
      await activarProductoPorId(id);
      alert("Producto activado correctamente");
      await cargarProductos();
    } catch (error) {
      console.error(error);
      alert(error.message || "No se pudo activar el producto");
    }
  };

  const productosFiltrados = productos.filter((producto) => {
    const coincideNombre = producto.nombre
      ?.toLowerCase()
      .includes(busqueda.toLowerCase());

    const coincideCategoria = categoria
      ? producto.categoriaNombre === categoria
      : true;

    const coincidePrecio = precioMax
      ? Number(producto.precio) <= Number(precioMax)
      : true;

    return coincideNombre && coincideCategoria && coincidePrecio;
  });

  const categorias = [
    ...new Set(
      productos
        .map((producto) => producto.categoriaNombre)
        .filter(Boolean)
    ),
  ];

  return (
    <div className="productos-page">
      <header className="landing-navbar">
        <div className="navbar-brand">
          <img src={logo} alt="Logo SIAC" />
          <span>SIAC</span>
        </div>

        <button
          className="btn-login"
          onClick={() => navigate(token ? "/dashboard" : "/")}
        >
          {token ? "Ir al dashboard" : "Iniciar sesión"}
        </button>
      </header>

      <section className="pokemon-section">
        <div className="pokemon-text">
          <h1>Catálogo de productos</h1>
          <p>
            Explora artículos coleccionables, cartas, accesorios y sets
            disponibles.
          </p>

          <div className="hero-actions">
            <button
              className="btn-primary"
              onClick={() => navigate("/busqueda-inteligente")}
            >
              Buscar producto por imagen
            </button>
          </div>
        </div>

        <img
          className="pokemon-image"
          src={pokemon}
          alt="Coleccionables SIAC"
        />
      </section>

      {token && canManageProductos() && (
        <div className="admin-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar producto
          </button>

          <button className="btn-secondary" onClick={cambiarVistaProductos}>
            {mostrarInactivos ? "Ver activos" : "Ver inactivos"}
          </button>
        </div>
      )}

      <div className="filtros-card">
        <input
          type="text"
          placeholder="Buscar por nombre..."
          value={busqueda}
          onChange={(e) => setBusqueda(e.target.value)}
        />

        <select
          value={categoria}
          onChange={(e) => setCategoria(e.target.value)}
        >
          <option value="">Todas las categorías</option>

          {categorias.map((cat) => (
            <option key={cat} value={cat}>
              {cat}
            </option>
          ))}
        </select>

        <input
          type="number"
          placeholder="Precio máximo"
          value={precioMax}
          onChange={(e) => setPrecioMax(e.target.value)}
        />
      </div>

      <div className="productos-grid">
        {productosFiltrados.length === 0 && (
          <p className="sin-productos">
            No hay productos disponibles o no coinciden con los filtros.
          </p>
        )}

        {productosFiltrados.map((producto) => (
          <div className="producto-card" key={producto.id}>
            <div className="producto-img">
              {producto.imagenUrl ? (
                <img src={producto.imagenUrl} alt={producto.nombre} />
              ) : (
                "Sin imagen"
              )}
            </div>

            <h3>{producto.nombre}</h3>
            <p>{producto.descripcion}</p>

            <div className="producto-info">
              <span>Categoría:</span>
              <strong>{producto.categoriaNombre}</strong>
            </div>

            <div className="producto-info">
              <span>Precio:</span>
              <strong>${producto.precio}</strong>
            </div>

            <div className="producto-info">
              <span>Cantidad:</span>
              <strong>{producto.cantidadActual}</strong>
            </div>

            <div className="acciones">
              <button
                className="btn-secondary"
                onClick={() => abrirDetalle(producto)}
              >
                Ver detalle
              </button>

              {token && canManageProductos() && (
                <>
                  {!mostrarInactivos ? (
                    <>
                      <button
                        className="btn-edit"
                        onClick={() => abrirEditar(producto)}
                      >
                        Editar
                      </button>

                      <button
                        className="btn-delete"
                        onClick={() => eliminarProducto(producto.id)}
                      >
                        Desactivar
                      </button>
                    </>
                  ) : (
                    <button
                      className="btn-edit"
                      onClick={() => activarProducto(producto.id)}
                    >
                      Activar
                    </button>
                  )}
                </>
              )}
            </div>
          </div>
        ))}
      </div>

      <div className="pagination">
        <button
          disabled={pagina === 0}
          onClick={() => setPagina(pagina - 1)}
        >
          Anterior
        </button>

        <span>
          Página {pagina + 1} de {totalPaginas || 1}
        </span>

        <button
          disabled={pagina + 1 >= totalPaginas}
          onClick={() => setPagina(pagina + 1)}
        >
          Siguiente
        </button>
      </div>

      {mostrarDetalle && productoSeleccionado && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{productoSeleccionado.nombre}</h2>
            <p>{productoSeleccionado.descripcion}</p>

            <p>
              <strong>Categoría:</strong>{" "}
              {productoSeleccionado.categoriaNombre}
            </p>

            <p>
              <strong>Precio:</strong> ${productoSeleccionado.precio}
            </p>

            <p>
              <strong>Cantidad:</strong>{" "}
              {productoSeleccionado.cantidadActual}
            </p>

            <button
              className="btn-secondary"
              onClick={() => setMostrarDetalle(false)}
            >
              Cerrar
            </button>
          </div>
        </div>
      )}

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar producto" : "Agregar producto"}</h2>

            <form className="producto-form" onSubmit={guardarProducto}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre del producto"
                value={formProducto.nombre}
                onChange={manejarCambioFormulario}
                required
              />

              <textarea
                name="descripcion"
                placeholder="Descripción"
                value={formProducto.descripcion}
                onChange={manejarCambioFormulario}
                required
              />

              <input
                type="number"
                name="precio"
                placeholder="Precio"
                value={formProducto.precio}
                onChange={manejarCambioFormulario}
                min="1"
                step="0.01"
                required
              />

              <input
                type="number"
                name="cantidadActual"
                placeholder="Cantidad actual"
                value={formProducto.cantidadActual}
                onChange={manejarCambioFormulario}
                min="0"
                required
              />

              <select
                name="categoriaId"
                value={formProducto.categoriaId}
                onChange={manejarCambioFormulario}
                required
              >
                <option value="">Selecciona una categoría</option>

                {categoriasDisponibles.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.nombre}
                  </option>
                ))}
              </select>

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear producto"}
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

export default ProductosPage;