import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import { obtenerProductos } from "../services/productoService";
import { obtenerTiposMovimiento } from "../services/tipoMovimientoService";

import {
  obtenerMovimientos,
  crearMovimiento,
} from "../services/movimientoInventarioService";

import "../styles/movimientosPage.css";

function MovimientosPage() {
  const navigate = useNavigate();

  const [movimientos, setMovimientos] = useState([]);
  const [productos, setProductos] = useState([]);
  const [tiposMovimiento, setTiposMovimiento] = useState([]);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);

  const size = 10;

  const [formMovimiento, setFormMovimiento] = useState({
    productoId: "",
    tipoMovimientoId: "",
    cantidad: "",
  });

  useEffect(() => {
    cargarDatos();
  }, [pagina]);

  const cargarDatos = async () => {
    try {
      const [
        productosData,
        tiposData,
        movimientosData
      ] = await Promise.all([
        obtenerProductos(),
        obtenerTiposMovimiento(),
        obtenerMovimientos(pagina, size),
      ]);

      setProductos(productosData.content || productosData || []);

      setTiposMovimiento(
        tiposData.content || tiposData || []
      );

      setMovimientos(
        movimientosData.content || []
      );

      setTotalPaginas(
        movimientosData.totalPages || 0
      );

    } catch (error) {
      console.error(error);
      alert(error.message || "Error al cargar datos");
    }
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormMovimiento({
      ...formMovimiento,
      [name]: value,
    });
  };

  const guardarMovimiento = async (e) => {
    e.preventDefault();

    try {
      const movimientoRequest = {
        productoId: Number(formMovimiento.productoId),
        tipoMovimientoId: Number(formMovimiento.tipoMovimientoId),
        cantidad: Number(formMovimiento.cantidad),
      };

      await crearMovimiento(movimientoRequest);

      alert("Movimiento registrado correctamente");

      setFormMovimiento({
        productoId: "",
        tipoMovimientoId: "",
        cantidad: "",
      });

      await cargarDatos();

    } catch (error) {
      console.error(error);
      alert(error.message || "Error al registrar movimiento");
    }
  };

  const formatearFecha = (fecha) => {
    if (!fecha) return "Sin fecha";

    return new Date(fecha).toLocaleString("es-MX", {
      dateStyle: "medium",
      timeStyle: "short",
    });
  };

  return (
    <div className="movimientos-page">
      <header className="movimientos-header">
        <div>
          <h1>Movimientos de Inventario</h1>
          <p>Registra entradas y salidas de productos.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="movimientos-form-card">
        <h2>Registrar movimiento</h2>

        <form className="movimiento-form" onSubmit={guardarMovimiento}>
          <select
            name="productoId"
            value={formMovimiento.productoId}
            onChange={manejarCambio}
            required
          >
            <option value="">Selecciona un producto</option>

            {productos.map((producto) => (
              <option key={producto.id} value={producto.id}>
                {producto.nombre} - Stock: {producto.cantidadActual}
              </option>
            ))}
          </select>

          <select
            name="tipoMovimientoId"
            value={formMovimiento.tipoMovimientoId}
            onChange={manejarCambio}
            required
          >
            <option value="">Selecciona tipo de movimiento</option>

            {tiposMovimiento.map((tipo) => (
              <option key={tipo.id} value={tipo.id}>
                {tipo.nombre}
              </option>
            ))}
          </select>

          <input
            type="number"
            name="cantidad"
            placeholder="Cantidad"
            min="1"
            value={formMovimiento.cantidad}
            onChange={manejarCambio}
            required
          />

          <button className="btn-primary" type="submit">
            Registrar movimiento
          </button>
        </form>
      </section>

      <section className="movimientos-table-card">
        <h2>Historial de movimientos</h2>

        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Producto</th>
              <th>Tipo</th>
              <th>Cantidad</th>
              <th>Usuario</th>
              <th>Fecha</th>
            </tr>
          </thead>

          <tbody>
            {movimientos.map((movimiento) => (
              <tr key={movimiento.id}>
                <td>{movimiento.id}</td>

                <td>{movimiento.productoNombre}</td>

                <td>{movimiento.tipoMovimientoNombre}</td>

                <td>{movimiento.cantidad}</td>

                <td>
                  {movimiento.usuarioNombre || "Usuario autenticado"}
                </td>

                <td>
                  {formatearFecha(movimiento.fecha)}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {movimientos.length === 0 && (
          <p className="sin-movimientos">
            No hay movimientos registrados.
          </p>
        )}

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
      </section>
    </div>
  );
}

export default MovimientosPage;