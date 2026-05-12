import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { isAdmin } from "../auth/roleUtils";

import {
  obtenerUsuarios,
  obtenerUsuariosInactivos,
  crearUsuario,
  actualizarUsuario,
  desactivarUsuario,
  activarUsuario,
} from "../services/usuarioService";

import "../styles/usuariosPage.css";

function UsuariosPage() {
  const navigate = useNavigate();

  const [usuarios, setUsuarios] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [mostrarInactivos, setMostrarInactivos] = useState(false);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const size = 5;

  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [modoEdicion, setModoEdicion] = useState(false);
  const [usuarioSeleccionado, setUsuarioSeleccionado] = useState(null);

  const [formUsuario, setFormUsuario] = useState({
    nombre: "",
    apPaterno: "",
    apMaterno: "",
    correo: "",
    contrasena: "",
  });

  useEffect(() => {
    cargarUsuarios();
  }, [pagina, mostrarInactivos]);

  const cargarUsuarios = async () => {
    try {
      const data = mostrarInactivos
        ? await obtenerUsuariosInactivos(pagina, size)
        : await obtenerUsuarios(pagina, size);

      setUsuarios(data.content || []);
      setTotalPaginas(data.totalPages || 0);
    } catch (error) {
      alert(error.message);
    }
  };

  const cambiarVista = () => {
    setPagina(0);
    setMostrarInactivos(!mostrarInactivos);
  };

  const abrirAgregar = () => {
    setModoEdicion(false);
    setUsuarioSeleccionado(null);
    setFormUsuario({
      nombre: "",
      apPaterno: "",
      apMaterno: "",
      correo: "",
      contrasena: "",
    });
    setMostrarFormulario(true);
  };

  const abrirEditar = (usuario) => {
    setModoEdicion(true);
    setUsuarioSeleccionado(usuario);

    setFormUsuario({
      nombre: usuario.nombre || "",
      apPaterno: usuario.apPaterno || "",
      apMaterno: usuario.apMaterno || "",
      correo: usuario.correo || usuario.correoElectronico || "",
      contrasena: "",
    });

    setMostrarFormulario(true);
  };

  const manejarCambio = (e) => {
    const { name, value } = e.target;

    setFormUsuario({
      ...formUsuario,
      [name]: value,
    });
  };

  const guardarUsuario = async (e) => {
    e.preventDefault();

    try {
      const usuarioRequest = {
        nombre: formUsuario.nombre,
        apPaterno: formUsuario.apPaterno,
        apMaterno: formUsuario.apMaterno,
        correo: formUsuario.correo,
        contrasena: formUsuario.contrasena,
      };

      if (modoEdicion) {
        if (!usuarioRequest.contrasena) {
          delete usuarioRequest.contrasena;
        }

        await actualizarUsuario(usuarioSeleccionado.id, usuarioRequest);
        alert("Usuario actualizado correctamente");
      } else {
        await crearUsuario(usuarioRequest);
        alert("Usuario creado correctamente");
      }

      setMostrarFormulario(false);
      setModoEdicion(false);
      setUsuarioSeleccionado(null);

      await cargarUsuarios();
    } catch (error) {
      alert(error.message);
    }
  };

  const eliminarUsuario = async (id) => {
    const confirmar = window.confirm(
      "¿Seguro que deseas desactivar este usuario?"
    );

    if (!confirmar) return;

    try {
      await desactivarUsuario(id);
      alert("Usuario desactivado correctamente");
      await cargarUsuarios();
    } catch (error) {
      alert(error.message);
    }
  };

  const reactivarUsuario = async (id) => {
    try {
      await activarUsuario(id);
      alert("Usuario activado correctamente");
      await cargarUsuarios();
    } catch (error) {
      alert(error.message);
    }
  };

  const usuariosFiltrados = usuarios.filter((usuario) => {
    const texto = `${usuario.nombre || ""} ${usuario.apPaterno || ""} ${
      usuario.apMaterno || ""
    } ${usuario.correo || usuario.correoElectronico || ""}`;

    return texto.toLowerCase().includes(busqueda.toLowerCase());
  });

  if (!isAdmin()) {
    return (
      <div className="usuarios-page">
        <header className="usuarios-header">
          <div>
            <h1>Acceso restringido</h1>
            <p>Este módulo solo está disponible para usuarios con rol ADMIN.</p>
          </div>

          <button onClick={() => navigate("/dashboard")}>
            Volver al dashboard
          </button>
        </header>
      </div>
    );
  }

  return (
    <div className="usuarios-page">
      <header className="usuarios-header">
        <div>
          <h1>Gestión de Usuarios</h1>
          <p>Administra los usuarios registrados en SIAC.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="usuarios-actions">
        <button className="btn-primary" onClick={abrirAgregar}>
          Agregar usuario
        </button>

        <button className="btn-secondary" onClick={cambiarVista}>
          {mostrarInactivos ? "Ver activos" : "Ver inactivos"}
        </button>
      </section>

      <section className="usuarios-filter">
        <input
          type="text"
          placeholder="Buscar usuario por nombre o correo..."
          value={busqueda}
          onChange={(e) => setBusqueda(e.target.value)}
        />
      </section>

      <section className="usuarios-table-card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre completo</th>
              <th>Correo</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            {usuariosFiltrados.map((usuario) => (
              <tr key={usuario.id}>
                <td>{usuario.id}</td>

                <td>
                  {usuario.nombre} {usuario.apPaterno} {usuario.apMaterno}
                </td>

                <td>{usuario.correo || usuario.correoElectronico}</td>

                <td>{usuario.activo ? "Activo" : "Inactivo"}</td>

                <td>
                  {!mostrarInactivos ? (
                    <>
                      <button
                        className="btn-edit"
                        onClick={() => abrirEditar(usuario)}
                      >
                        Editar
                      </button>

                      <button
                        className="btn-delete"
                        onClick={() => eliminarUsuario(usuario.id)}
                      >
                        Desactivar
                      </button>
                    </>
                  ) : (
                    <button
                      className="btn-edit"
                      onClick={() => reactivarUsuario(usuario.id)}
                    >
                      Activar
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {usuariosFiltrados.length === 0 && (
          <p className="sin-usuarios">No hay usuarios para mostrar.</p>
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

      {mostrarFormulario && (
        <div className="modal-overlay">
          <div className="modal-card">
            <h2>{modoEdicion ? "Editar usuario" : "Agregar usuario"}</h2>

            <form className="usuario-form" onSubmit={guardarUsuario}>
              <input
                type="text"
                name="nombre"
                placeholder="Nombre"
                value={formUsuario.nombre}
                onChange={manejarCambio}
                required
              />

              <input
                type="text"
                name="apPaterno"
                placeholder="Apellido paterno"
                value={formUsuario.apPaterno}
                onChange={manejarCambio}
                required
              />

              <input
                type="text"
                name="apMaterno"
                placeholder="Apellido materno"
                value={formUsuario.apMaterno}
                onChange={manejarCambio}
              />

              <input
                type="email"
                name="correo"
                placeholder="Correo electrónico"
                value={formUsuario.correo}
                onChange={manejarCambio}
                required
              />

              <input
                type="password"
                name="contrasena"
                placeholder={modoEdicion ? "Nueva contraseña" : "Contraseña"}
                value={formUsuario.contrasena}
                onChange={manejarCambio}
                required={!modoEdicion}
              />

              <div className="acciones">
                <button className="btn-primary" type="submit">
                  {modoEdicion ? "Guardar cambios" : "Crear usuario"}
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

export default UsuariosPage;