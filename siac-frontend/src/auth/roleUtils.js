export const getRol = () => {
  return localStorage.getItem("rol");
};

/*
|--------------------------------------------------------------------------
| Roles individuales
|--------------------------------------------------------------------------
*/

export const isAdmin = () => {
  return getRol() === "ROLE_ADMIN";
};

export const isSupervisor = () => {
  return getRol() === "ROLE_SUPERVISOR";
};

export const isEmpleado = () => {
  return getRol() === "ROLE_EMPLEADO";
};

export const isCliente = () => {
  return getRol() === "ROLE_CLIENTE";
};

export const isAuditor = () => {
  return getRol() === "ROLE_AUDITOR";
};

/*
|--------------------------------------------------------------------------
| Permisos agrupados
|--------------------------------------------------------------------------
*/

// CRUD productos
export const canManageProductos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

// Solo lectura productos
export const canViewProductos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isCliente() ||
    isAuditor()
  );
};

// Administración usuarios
export const canManageUsuarios = () => {
  return isAdmin();
};

// Bitácora lectura
export const canViewBitacora = () => {
  return (
    isAdmin() ||
    isAuditor()
  );
};

// Reportes/Auditoría
export const canViewReportes = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isAuditor()
  );
};

// CRUD movimientos inventario
export const canManageMovimientos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

// Solo lectura movimientos
export const canViewMovimientos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isAuditor()
  );
};