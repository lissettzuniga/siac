import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { obtenerProductos } from "../services/productoService";
import { obtenerTiposCarta } from "../services/tipoCartaService";
import {
  obtenerProductosCarta,
  obtenerProductosCartaInactivos,
  crearProductoCarta,
  actualizarProductoCarta,
  desactivarProductoCarta,
  activarProductoCarta,
} from "../services/productoCartaService";
import { canManageProductoCarta } from "../auth/roleUtils";
import "../styles/productoCartaPage.css";

function ProductoCartaPage() {
  const navigate = useNavigate();

  const [productosCarta, setProductosCarta] = useState([]);
  const [productos, setProductos] = useState([]);
  const [tiposCarta, setTiposCarta] = useState([]);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [seleccionado, setSeleccionado] = useState(null);
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [form, setForm] = useState({
    productoId: "",
    tipoCartaId: "",
    atributo: "",
    ataque: "",
    defensa: "",
    nivel: "",
  });

  useEffect(() => {
    cargarDatosIniciales();
  }, []);

  const cargarDatosIniciales = async () => {
    await cargarActivos();
    await cargarProductos();
    await cargarTiposCarta();
  };

  const cargarActivos = async () => {
    try {
      const data = await obtenerProductosCarta();
      setProductosCarta(data.content || data || []);
      setMostrarInactivos(false);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarInactivos = async () => {
    try {
      const data = await obtenerProductosCartaInactivos();
      setProductosCarta(data.content || data || []);
      setMostrarInactivos(true);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarProductos = async () => {
    try {
      const data = await obtenerProductos();
      setProductos(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const cargarTiposCarta = async () => {
    try {
      const data = await obtenerTiposCarta();
      setTiposCarta(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setSeleccionado(null);
    setForm({
      productoId: "",
      tipoCartaId: "",
      atributo: "",
      ataque: "",
      defensa: "",
      nivel: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (item) => {
    setModoEdicion(true);
    setSeleccionado(item);
    setForm({
      productoId: item.productoId || "",
      tipoCartaId: item.tipoCartaId || "",
      atributo: item.atributo || "",
      ataque: item.ataque || "",
      defensa: item.defensa || "",
      nivel: item.nivel || "",
    });
    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setForm({
      ...form,
      [name]: value,
    });
  };

  const guardar = async (e) => {
    e.preventDefault();

    const request = {
      productoId: Number(form.productoId),
      tipoCartaId: Number(form.tipoCartaId),
      atributo: form.atributo,
      ataque: Number(form.ataque),
      defensa: Number(form.defensa),
      nivel: Number(form.nivel),
    };

    try {
      if (modoEdicion) {
        await actualizarProductoCarta(seleccionado.id, request);
        alert("Producto carta actualizado correctamente");
      } else {
        await crearProductoCarta(request);
        alert("Producto carta creado correctamente");
      }

      setMostrarFormulario(false);
      cargarActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const desactivar = async (id) => {
    const confirmar = window.confirm("¿Seguro que deseas desactivar este producto carta?");

    if (!confirmar) return;

    try {
      await desactivarProductoCarta(id);
      alert("Producto carta desactivado correctamente");
      cargarActivos();
    } catch (error) {
      alert(error.message);
    }
  };

  const activar = async (id) => {
    try {
      await activarProductoCarta(id);
      alert("Producto carta activado correctamente");
      cargarInactivos();
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div className="producto-carta-page">
      <header className="producto-carta-header">
        <div>
          <h1>Productos Carta</h1>
          <p>Relaciona productos con tipos de carta y sus atributos.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {canManageProductoCarta() && (
        <section className="producto-carta-actions">
          <button className="btn-primary" onClick={abrirAgregar}>
            Agregar producto carta
          </button>

          {!mostrarInactivos ? (
            <button className="btn-secondary" onClick={cargarInactivos}>
              Ver inactivos
            </button>
          ) : (
            <button className="btn-secondary" onClick={cargarActivos}>
              Ver activos
            </button>
          )}
        </section>
      )}

      <section className="producto-carta-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Producto</th>
              <th>Tipo carta</th>
              <th>Atributo</th>
              <th>Ataque</th>
              <th>Defensa</th>
              <th>Nivel</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {productosCarta.map((item) => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.productoNombre}</td>
                <td>{item.tipoCartaNombre}</td>
                <td>{item.atributo}</td>
                <td>{item.ataque}</td>
                <td>{item.defensa}</td>
                <td>{item.nivel}</td>
                <td>{item.activo ? "Activo" : "Inactivo"}</td>
                <td>
                  {canManageProductoCarta() ? (
                    item.activo ? (
                      <>
                        <button
                          className="btn-edit"
                          onClick={() => abrirEditar(item)}
                        >
                          Editar
                        </button>

                        <button
                          className="btn-delete"
                          onClick={() => desactivar(item.id)}
                        >
                          Desactivar
                        </button>
                      </>
                    ) : (
                      <button
                        className="btn-edit"
                        onClick={() => activar(item.id)}
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

        {productosCarta.length === 0 && (
          <p className="sin-registros">No hay productos carta registrados.</p>
        )}
      </section>

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar producto carta" : "Agregar producto carta"}</h2>

            <form className="producto-carta-form" onSubmit={guardar}>
              <select
                name="productoId"
                value={form.productoId}
                onChange={manejarCambio}
                required
              >
                <option value="">Selecciona producto</option>
                {productos.map((producto) => (
                  <option key={producto.id} value={producto.id}>
                    {producto.nombre}
                  </option>
                ))}
              </select>

              <select
                name="tipoCartaId"
                value={form.tipoCartaId}
                onChange={manejarCambio}
                required
              >
                <option value="">Selecciona tipo de carta</option>
                {tiposCarta.map((tipo) => (
                  <option key={tipo.id} value={tipo.id}>
                    {tipo.nombre}
                  </option>
                ))}
              </select>

              <input
                type="text"
                name="atributo"
                placeholder="Atributo"
                value={form.atributo}
                onChange={manejarCambio}
                required
              />

              <input
                type="number"
                name="ataque"
                placeholder="Ataque"
                min="0"
                value={form.ataque}
                onChange={manejarCambio}
                required
              />

              <input
                type="number"
                name="defensa"
                placeholder="Defensa"
                min="0"
                value={form.defensa}
                onChange={manejarCambio}
                required
              />

              <input
                type="number"
                name="nivel"
                placeholder="Nivel"
                min="1"
                value={form.nivel}
                onChange={manejarCambio}
                required
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear"}
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

export default ProductoCartaPage;