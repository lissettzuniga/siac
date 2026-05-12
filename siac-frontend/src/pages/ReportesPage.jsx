import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import * as XLSX from "xlsx-js-style";
import { obtenerProductos } from "../services/productoService";
import "../styles/reportesPage.css";

function ReportesPage() {
  const navigate = useNavigate();
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    cargarProductos();
  }, []);

  const cargarProductos = async () => {
    try {
      const data = await obtenerProductos();
      setProductos(data.content || data || []);
    } catch (error) {
      alert(error.message);
    }
  };

  const calcularValorTotal = (producto) => {
    return Number(producto.precio) * Number(producto.cantidadActual);
  };

  const exportarExcel = () => {

    const workbook = XLSX.utils.book_new();

    const data = [
      ["SIAC - REPORTE DE INVENTARIO"],
      [`Fecha: ${new Date().toLocaleDateString()}`],
      [],
      [
        "Producto",
        "Categoría",
        "Cantidad disponible",
        "Precio unitario",
        "Valor total"
      ],

      ...productos.map((producto) => [
        producto.nombre,
        producto.categoriaNombre,
        producto.cantidadActual,
        producto.precio,
        calcularValorTotal(producto)
      ]),

      [],
      [
        "",
        "",
        "",
        "TOTAL INVENTARIO",
        valorTotalInventario
      ]
    ];

    const worksheet = XLSX.utils.aoa_to_sheet(data);

    // Tamaño columnas
    worksheet["!cols"] = [
      { wch: 30 },
      { wch: 22 },
      { wch: 22 },
      { wch: 18 },
      { wch: 18 }
    ];

    // Combinar título
    worksheet["!merges"] = [
      {
        s: { r: 0, c: 0 },
        e: { r: 0, c: 4 }
      }
    ];

    // ESTILOS

    // Título
    worksheet["A1"].s = {
      font: {
        bold: true,
        sz: 18,
        color: { rgb: "FFFFFF" }
      },
      fill: {
        fgColor: { rgb: "1E3A8A" }
      },
      alignment: {
        horizontal: "center"
      }
    };

    // Fecha
    worksheet["A2"].s = {
      font: {
        italic: true,
        sz: 12
      }
    };

    // Encabezados
    const headers = ["A4", "B4", "C4", "D4", "E4"];

    headers.forEach((cell) => {
      worksheet[cell].s = {
        font: {
          bold: true,
          color: { rgb: "FFFFFF" }
        },
        fill: {
          fgColor: { rgb: "2563EB" }
        },
        alignment: {
          horizontal: "center"
        }
      };
    });

    // Formato moneda
    for (let i = 5; i < 5 + productos.length; i++) {

      worksheet[`D${i}`].s = {
        numFmt: "$#,##0.00"
      };

      worksheet[`E${i}`].s = {
        numFmt: "$#,##0.00"
      };
    }


    const totalRow = 6 + productos.length;

    worksheet[`D${totalRow}`].s = {
      font: {
        bold: true
      },
      fill: {
        fgColor: { rgb: "D1D5DB" }
      }
    };

    worksheet[`E${totalRow}`].s = {
      font: {
        bold: true,
        color: { rgb: "FFFFFF" }
      },
      fill: {
        fgColor: { rgb: "059669" }
      },
      numFmt: "$#,##0.00"
    };

    XLSX.utils.book_append_sheet(
      workbook,
      worksheet,
      "Inventario"
    );

    XLSX.writeFile(
      workbook,
      `reporte_inventario_${new Date().toISOString().split("T")[0]}.xlsx`
    );
  };

  const valorTotalInventario = productos.reduce(
    (total, producto) => total + calcularValorTotal(producto),
    0
  );

  return (
    <div className="reportes-page">
      <header className="reportes-header">
        <div>
          <h1>Reportes de Inventario</h1>
          <p>Consulta el inventario actual y exporta el reporte en Excel.</p>
        </div>

        <button onClick={() => navigate("/dashboard")}>
          Volver al dashboard
        </button>
      </header>

      <section className="reportes-resumen">
        <div className="reporte-card">
          <span>Productos activos</span>
          <strong>{productos.length}</strong>
        </div>

        <div className="reporte-card">
          <span>Valor total del inventario</span>
          <strong>${valorTotalInventario.toFixed(2)}</strong>
        </div>

        <button className="btn-exportar" onClick={exportarExcel}>
          Exportar a Excel
        </button>
      </section>

      <section className="reportes-table-card">
        <h2>Inventario actual</h2>

        <table>
          <thead>
            <tr>
              <th>Producto</th>
              <th>Categoría</th>
              <th>Cantidad disponible</th>
              <th>Precio unitario</th>
              <th>Valor total</th>
            </tr>
          </thead>

          <tbody>
            {productos.map((producto) => (
              <tr key={producto.id}>
                <td>{producto.nombre}</td>
                <td>{producto.categoriaNombre}</td>
                <td>{producto.cantidadActual}</td>
                <td>${producto.precio}</td>
                <td>${calcularValorTotal(producto).toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>
    </div>
  );
}

export default ReportesPage;