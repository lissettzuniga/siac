import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { obtenerBitacora } from "../services/bitacoraService";
import "../styles/bitacoraPage.css";

function BitacoraPage() {
  const navigate = useNavigate();

  const [bitacora, setBitacora] = useState([]);
  const [entidadFiltro, setEntidadFiltro] = useState("");
  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [error, setError] = useState("");

  const size = 10;

  useEffect(() => {
    cargarBitacora();
  }, [pagina]);

  const cargarBitacora = async () => {
    try {
      setError("");

      const data = await obtenerBitacora(pagina, size);

      setBitacora(data.content || []);
      setTotalPaginas(data.totalPages || 0);
    } catch (error) {
      console.error(error);
      setError(error.message || "Error al cargar la bitácora");
    }
  };

  const formatearFecha = (fecha) => {
    if (!fecha) return "Sin fecha";

    return new Date(fecha).toLocaleString("es-MX", {
      dateStyle: "medium",
      timeStyle: "short",
    });
  };

  const entidades = [
    ...new Set(bitacora.map((registro) => registro.entidad).filter(Boolean)),
  ];

  const bitacoraFiltrada = entidadFiltro
    ? bitacora.filter((registro) => registro.entidad === entidadFiltro)
    : bitacora;

  return (
    <div className="bitacora-page">
      <header className="bitacora-header">
        <div>
          <h1>Bitácora del sistema</h1>
          <p>Consulta las acciones realizadas dentro de SIAC.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      {error && <div className="alert-error">{error}</div>}

      <section className="bitacora-filtros">
        <select
          value={entidadFiltro}
          onChange={(e) => setEntidadFiltro(e.target.value)}
        >
          <option value="">Todas las entidades</option>

          {entidades.map((entidad) => (
            <option key={entidad} value={entidad}>
              {entidad}
            </option>
          ))}
        </select>

        <button className="btn-secondary" onClick={cargarBitacora}>
          Actualizar
        </button>
      </section>

      <section className="bitacora-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Usuario</th>
              <th>Entidad</th>
              <th>Acción</th>
              <th>Descripción</th>
              <th>Fecha</th>
            </tr>
          </thead>

          <tbody>
            {bitacoraFiltrada.map((registro) => (
              <tr key={registro.id}>
                <td>{registro.id}</td>
                <td>{registro.usuarioNombre || "Usuario"}</td>
                <td>{registro.entidad}</td>
                <td>
                  <span
                    className={`accion-badge ${
                      registro.accion
                        ? registro.accion.toLowerCase()
                        : ""
                    }`}
                  >
                    {registro.accion || "Sin acción"}
                  </span>
                </td>
                <td>{registro.descripcion || "Sin descripción"}</td>
                <td>{formatearFecha(registro.fecha)}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {bitacoraFiltrada.length === 0 && (
          <p className="sin-registros">
            No hay registros de bitácora para mostrar.
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

export default BitacoraPage;