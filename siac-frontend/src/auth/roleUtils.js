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

export const canManageProductos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

export const canManageUsuarios = () => {
  return isAdmin();
};

export const canViewBitacora = () => {
  return (
    isAdmin() ||
    isAuditor()
  );
};

export const canViewReportes = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isAuditor()
  );
};

export const canManageMovimientos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

export const canViewMovimientos = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isAuditor()
  );
};

export const canManageCategorias = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

export const canViewProductos = () => {
  return true;
};

export const canViewCategorias = () => {
  return true;
};

export const canManageTiposMovimiento = () => {
  return isAdmin();
};

export const canManageTiposCarta = () => {
  return isAdmin();
};

export const canViewTiposCarta = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isCliente() ||
    isAuditor()
  );
};

export const canManageProductoCarta = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

export const canViewProductoCarta = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isCliente() ||
    isAuditor()
  );
};

export const canManageImagenesProducto = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado()
  );
};

export const canViewImagenesProducto = () => {
  return (
    isAdmin() ||
    isSupervisor() ||
    isEmpleado() ||
    isCliente() ||
    isAuditor()
  );
};

export const canManageUsuarioRoles = () => {
  return isAdmin();
};

export const canViewUsuarioRoles = () => {
  return isAdmin();
};

export const canManageRoles = () => {
  return isAdmin();
};

export const canViewRoles = () => {
  return isAdmin();
};

export const canManagePermisos = () => {
  return isAdmin();
};

export const canViewPermisos = () => {
  return (
    isAdmin() ||
    isAuditor()
  );
};

export const canManageRolPermisos = () => {
  return isAdmin();
};

export const canViewRolPermisos = () => {
  return (
    isAdmin() ||
    isAuditor()
  );
};