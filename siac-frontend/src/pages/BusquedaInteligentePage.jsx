import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { buscarPorImagen } from "../services/busquedaInteligenteService";
import "../styles/busquedaInteligentePage.css";

function BusquedaInteligentePage() {
  const navigate = useNavigate();

  const [imagen, setImagen] = useState(null);
  const [preview, setPreview] = useState(null);
  const [resultados, setResultados] = useState([]);
  const [cargando, setCargando] = useState(false);
  const [mensaje, setMensaje] = useState("");

  const manejarImagen = (e) => {
    const archivo = e.target.files[0];

    if (!archivo) return;

    setImagen(archivo);
    setPreview(URL.createObjectURL(archivo));
    setResultados([]);
    setMensaje("");
  };

  const buscar = async () => {
    if (!imagen) {
      setMensaje("Selecciona una imagen para realizar la búsqueda.");
      return;
    }

    try {
      setCargando(true);
      setMensaje("");

      const data = await buscarPorImagen(imagen);

      console.log("Respuesta búsqueda imagen:", data);

      const productosEncontrados = data.productos || [];

      setResultados(productosEncontrados);

      if (!data.encontrado || productosEncontrados.length === 0) {

        setMensaje(
          `Texto detectado: ${
            data.textoDetectado || "No se detectó texto"
          }. No se encontraron productos relacionados.`
        );

      } else {

        setMensaje(
          `Texto detectado: ${data.textoDetectado}`
        );
      }
    } catch (error) {
      setMensaje(error.message);
    } finally {
      setCargando(false);
    }
  };

  const limpiar = () => {
    setImagen(null);
    setPreview(null);
    setResultados([]);
    setMensaje("");
  };

  return (
    <div className="busqueda-page">
      <header className="busqueda-header">
        <div>
          <h1>Búsqueda Inteligente</h1>
          <p>Sube una imagen y SIAC intentará encontrar productos relacionados.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="busqueda-card">
        <div className="upload-area">
          <h2>Buscar producto por imagen</h2>
          <p>
            Selecciona una imagen de una carta, figura, accesorio o producto coleccionable.
          </p>

          <input
            type="file"
            accept="image/*"
            onChange={manejarImagen}
          />

          <div className="busqueda-actions">
            <button
              className="btn-primary"
              onClick={buscar}
              disabled={cargando}
            >
              {cargando ? "Buscando..." : "Buscar coincidencias"}
            </button>

            <button
              className="btn-secondary"
              onClick={limpiar}
              type="button"
            >
              Limpiar
            </button>
          </div>

          {mensaje && (
            <p className="busqueda-mensaje">{mensaje}</p>
          )}
        </div>

        <div className="preview-area">
          {preview ? (
            <img src={preview} alt="Vista previa" />
          ) : (
            <div className="preview-placeholder">
              Vista previa de la imagen
            </div>
          )}
        </div>
      </section>

      <section className="resultados-card">
        <h2>Resultados encontrados</h2>

        {resultados.length === 0 ? (
          <p className="sin-resultados">
            Aún no hay resultados para mostrar.
          </p>
        ) : (
          <div className="resultados-grid">
            {resultados.map((producto) => (
              <div className="resultado-producto" key={producto.id}>
                <div className="resultado-img">
                  {producto.imagenUrl ? (
                    <img src={producto.imagenUrl} alt={producto.nombre} />
                  ) : (
                    "Sin imagen"
                  )}
                </div>

                <h3>{producto.nombre}</h3>
                <p>{producto.descripcion}</p>

                <div className="resultado-info">
                  <span>Categoría:</span>
                  <strong>{producto.categoriaNombre}</strong>
                </div>

                <div className="resultado-info">
                  <span>Precio:</span>
                  <strong>${producto.precio}</strong>
                </div>

                <div className="resultado-info">
                  <span>Stock:</span>
                  <strong>{producto.cantidadActual}</strong>
                </div>
              </div>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}

export default BusquedaInteligentePage;