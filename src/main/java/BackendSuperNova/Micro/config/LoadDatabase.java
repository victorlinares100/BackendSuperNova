package BackendSuperNova.Micro.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BackendSuperNova.Micro.model.*;
import BackendSuperNova.Micro.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            CategoriaRepository categoriaRepo,
            BodegaRepository bodegaRepo,
            ProveedorRepository proveedorRepo,
            ProductoRepository productoRepo,
            StockRepository stockRepo,
            MovimientoStockRepository movimientoRepo,
            PedidoRepository pedidoRepo,
            DetallePedidoRepository detallePedidoRepo,
            VentaRepository ventaRepo) { // ← Agregamos VentaRepository aquí

        return args -> {

            // ─── 1. Categorías ────────────────────────────────────────
            if (categoriaRepo.count() == 0) {
                System.out.println("Cargando categorías...");
                String[] cats = {
                    "Lácteos", "Bebidas", "Panadería", "Carnes y Embutidos",
                    "Frutas y Verduras", "Aseo del Hogar", "Higiene Personal",
                    "Congelados", "Snacks y Dulces", "Condimentos y Salsas"
                };
                for (String c : cats) categoriaRepo.save(new Categoria(null, c));
            }

            // ─── 2. Proveedores ───────────────────────────────────────
            if (proveedorRepo.count() == 0) {
                System.out.println("Cargando proveedores...");
                Object[][] provs = {
                    {"76.543.210-K", "Lácteos El Sol",        "+56 2 2233 4455", "ventas@elsol.cl"},
                    {"77.123.456-3", "AquaPura S.A.",          "+56 9 8877 6655", "pedidos@aquapura.cl"},
                    {"78.999.001-5", "Bimbo Chile",            "+56 2 2999 0011", "chile@bimbo.com"},
                    {"76.300.200-8", "Procter Chile",          "+56 2 2100 2200", "ventas@procter.cl"},
                    {"79.456.789-2", "Distribuidor Sur Ltda.", "+56 9 7766 5544", "contacto@distrsur.cl"},
                    {"76.888.999-1", "Frutos del Campo S.A.",  "+56 9 6655 4433", "ventas@frutoscampo.cl"},
                };
                for (Object[] p : provs) {
                    Proveedor prov = new Proveedor();
                    prov.setRutEmpresa((String) p[0]);
                    prov.setDescripcion((String) p[1]);
                    prov.setTelefono((String) p[2]);
                    prov.setEmail((String) p[3]);
                    proveedorRepo.save(prov);
                }
            }

            // ─── 3. Bodegas ───────────────────────────────────────────
            if (bodegaRepo.count() == 0) {
                System.out.println("Cargando bodegas...");
                bodegaRepo.save(new Bodega(null, "Bodega Central", "Av. Principal 123, Santiago"));
                bodegaRepo.save(new Bodega(null, "Bodega Norte",   "Av. Los Leones 456, Providencia"));
                bodegaRepo.save(new Bodega(null, "Bodega Sur",     "Calle Gran Avenida 789, San Miguel"));
            }

            // ─── 4. Productos ─────────────────────────────────────────
            if (productoRepo.count() == 0) {
                System.out.println("Cargando productos...");

                List<Categoria> cats  = categoriaRepo.findAll();
                List<Proveedor> provs = proveedorRepo.findAll();

                Categoria lacteos     = cats.get(0);
                Categoria bebidas     = cats.get(1);
                Categoria panaderia   = cats.get(2);
                Categoria carnes      = cats.get(3);
                Categoria aseo        = cats.get(5);
                Categoria higiene     = cats.get(6);
                Categoria congelados  = cats.get(7);
                Categoria snacks      = cats.get(8);
                Categoria condimentos = cats.get(9);

                Proveedor prov1 = provs.get(0);
                Proveedor prov2 = provs.get(1);
                Proveedor prov3 = provs.get(2);
                Proveedor prov4 = provs.get(3);
                Proveedor prov5 = provs.get(4);
                Proveedor prov6 = provs.get(5);

                Object[][] prods = {
                    {"7802000100001", "Leche Entera 1L",       "Leche entera larga vida 1 litro",       lacteos,     990.0, 20, prov1},
                    {"7802000100002", "Queso Gauda 250g",      "Queso gauda laminado 250 gramos",       lacteos,    3490.0, 10, prov1},
                    {"7802000100003", "Yogurt Natural 180g",   "Yogurt natural sin azúcar 180g",        lacteos,     890.0, 15, prov1},
                    {"7802000100004", "Agua Mineral 1.5L",     "Agua mineral sin gas 1.5 litros",       bebidas,     790.0, 30, prov2},
                    {"7802000100005", "Jugo Naranja 1L",       "Jugo de naranja 100% natural 1 litro",  bebidas,    1290.0, 15, prov2},
                    {"7802000100006", "Bebida Cola 1.5L",      "Bebida cola regular 1.5 litros",        bebidas,    1490.0, 20, prov2},
                    {"7802000100007", "Pan de Molde 500g",     "Pan de molde blanco 500 gramos",        panaderia,  2190.0, 10, prov3},
                    {"7802000100008", "Marraqueta x6",         "Pack 6 marraquetas frescas",            panaderia,   990.0, 15, prov3},
                    {"7802000100009", "Pechuga de Pollo 1kg",  "Pechuga de pollo fresca 1 kilo",        carnes,     4990.0,  8, prov5},
                    {"7802000100010", "Longaniza 500g",        "Longaniza ahumada 500 gramos",          carnes,     3290.0,  8, prov5},
                    {"7802000100011", "Detergente Líquido 1L", "Detergente líquido ropa delicada 1L",   aseo,       3990.0, 10, prov4},
                    {"7802000100012", "Shampoo 400ml",         "Shampoo cabello normal 400ml",          higiene,    4490.0,  8, prov4},
                    {"7802000100013", "Papas Fritas 200g",     "Papas fritas clásicas 200 gramos",      snacks,     1890.0, 12, prov5},
                    {"7802000100014", "Salsa de Tomate 500g",  "Salsa de tomate natural 500 gramos",    condimentos, 990.0, 10, prov6},
                    {"7802000100015", "Helado Vainilla 1L",    "Helado de vainilla familiar 1 litro",   congelados, 3290.0,  6, prov5},
                };

                for (Object[] p : prods) {
                    Producto prod = new Producto();
                    prod.setCodigoDeBarras((String) p[0]);
                    prod.setNombre((String) p[1]);
                    prod.setDescripcion((String) p[2]);
                    prod.setCategoria((Categoria) p[3]);
                    prod.setPrecioVenta((Double) p[4]);
                    prod.setStockMinimo((Integer) p[5]);
                    prod.setProveedor((Proveedor) p[6]);
                    productoRepo.save(prod);
                }
            }

            // ─── 5. Stock ─────────────────────────────────────────────
            if (stockRepo.count() == 0) {
                System.out.println("Cargando stock...");

                List<Producto> prods = productoRepo.findAll();
                List<Bodega>   bods  = bodegaRepo.findAll();
                Bodega central = bods.get(0);
                Bodega norte   = bods.get(1);

                Object[][] stocks = {
                    {prods.get(0),  central,  80, 20, "2026-05-01", "2026-08-01"},
                    {prods.get(1),  central,  45, 10, "2026-05-01", "2026-07-15"},
                    {prods.get(2),  central,  60, 15, "2026-05-01", "2026-06-30"},
                    {prods.get(3),  central, 120, 30, "2026-05-01", null},
                    {prods.get(4),  central,  55, 15, "2026-05-01", "2026-09-01"},
                    {prods.get(5),  central,  90, 20, "2026-05-01", "2026-10-01"},
                    {prods.get(6),  central,   8, 10, "2026-05-20", "2026-05-27"},
                    {prods.get(7),  central,  30, 15, "2026-05-20", "2026-05-25"},
                    {prods.get(8),  central,  25,  8, "2026-05-20", "2026-05-26"},
                    {prods.get(9),  central,  40,  8, "2026-05-10", "2026-08-10"},
                    {prods.get(10), central,  35, 10, "2026-05-01", null},
                    {prods.get(11), central,  22,  8, "2026-05-01", null},
                    {prods.get(12), central,  50, 12, "2026-05-01", "2026-12-31"},
                    {prods.get(13), central,  48, 10, "2026-05-01", "2027-01-01"},
                    {prods.get(14), central,   5,  6, "2026-05-01", "2026-07-01"},
                    {prods.get(0),  norte,    40, 20, "2026-05-05", "2026-08-01"},
                    {prods.get(3),  norte,    60, 30, "2026-05-05", null},
                    {prods.get(6),  norte,     5, 10, "2026-05-20", "2026-05-27"},
                    {prods.get(10), norte,    18, 10, "2026-05-05", null},
                };

                for (Object[] s : stocks) {
                    Stock stock = new Stock();
                    stock.setProducto((Producto) s[0]);
                    stock.setBodega((Bodega) s[1]);
                    stock.setCantidadDisponible((Integer) s[2]);
                    stock.setStockMinimo((Integer) s[3]);
                    stock.setFechaIngreso(LocalDate.parse((String) s[4]));
                    stock.setFechaVencimiento(s[5] != null ? LocalDate.parse((String) s[5]) : null);
                    Stock guardado = stockRepo.save(stock);

                    MovimientoStock mov = new MovimientoStock();
                    mov.setStock(guardado);
                    mov.setCantidad(guardado.getCantidadDisponible());
                    mov.setFechaMovimiento(LocalDate.parse((String) s[4]));
                    mov.setTipoMovimiento("ENTRADA");
                    mov.setDescripcion("Ingreso inicial de " + guardado.getCantidadDisponible()
                        + " unidad(es) de " + ((Producto) s[0]).getNombre()
                        + " en " + ((Bodega) s[1]).getSucursal());
                    movimientoRepo.save(mov);
                }
            }

            // ─── 6. Pedidos ───────────────────────────────────────────
            if (pedidoRepo.count() == 0) {
                System.out.println("Cargando pedidos...");

                List<Proveedor> provs = proveedorRepo.findAll();
                List<Producto>  prods = productoRepo.findAll();

                // Pedido 1 — RECIBIDO
                Pedido p1 = new Pedido();
                p1.setProveedor(provs.get(0));
                p1.setFechaEmision(LocalDate.parse("2026-05-01"));
                p1.setEstado("RECIBIDO");
                p1.setTotal(new BigDecimal("49500.00"));
                Pedido p1g = pedidoRepo.save(p1);
                DetallePedido dp1a = new DetallePedido();
                dp1a.setPedido(p1g); dp1a.setProducto(prods.get(0));
                dp1a.setCantidadSolicitada(50); dp1a.setPrecioUnitario(new BigDecimal("990.00"));
                detallePedidoRepo.save(dp1a);
                DetallePedido dp1b = new DetallePedido();
                dp1b.setPedido(p1g); dp1b.setProducto(prods.get(1));
                dp1b.setCantidadSolicitada(15); dp1b.setPrecioUnitario(new BigDecimal("3490.00"));
                detallePedidoRepo.save(dp1b);

                // Pedido 2 — RECIBIDO
                Pedido p2 = new Pedido();
                p2.setProveedor(provs.get(1));
                p2.setFechaEmision(LocalDate.parse("2026-05-05"));
                p2.setEstado("RECIBIDO");
                p2.setTotal(new BigDecimal("94800.00"));
                Pedido p2g = pedidoRepo.save(p2);
                DetallePedido dp2a = new DetallePedido();
                dp2a.setPedido(p2g); dp2a.setProducto(prods.get(3));
                dp2a.setCantidadSolicitada(120); dp2a.setPrecioUnitario(new BigDecimal("790.00"));
                detallePedidoRepo.save(dp2a);

                // Pedido 3 — EN CAMINO
                Pedido p3 = new Pedido();
                p3.setProveedor(provs.get(2));
                p3.setFechaEmision(LocalDate.parse("2026-05-18"));
                p3.setEstado("EN CAMINO");
                p3.setTotal(new BigDecimal("43800.00"));
                Pedido p3g = pedidoRepo.save(p3);
                DetallePedido dp3a = new DetallePedido();
                dp3a.setPedido(p3g); dp3a.setProducto(prods.get(6));
                dp3a.setCantidadSolicitada(20); dp3a.setPrecioUnitario(new BigDecimal("2190.00"));
                detallePedidoRepo.save(dp3a);

                // Pedido 4 — PENDIENTE
                Pedido p4 = new Pedido();
                p4.setProveedor(provs.get(3));
                p4.setFechaEmision(LocalDate.parse("2026-05-22"));
                p4.setEstado("PENDIENTE");
                p4.setTotal(new BigDecimal("47880.00"));
                Pedido p4g = pedidoRepo.save(p4);
                DetallePedido dp4a = new DetallePedido();
                dp4a.setPedido(p4g); dp4a.setProducto(prods.get(10));
                dp4a.setCantidadSolicitada(12); dp4a.setPrecioUnitario(new BigDecimal("3990.00"));
                detallePedidoRepo.save(dp4a);
            }

            // ─── 7. Ventas e Historial Diario (NUEVO BLOQUE) ───────────
            if (ventaRepo.count() == 0) {
                System.out.println("Cargando historial de ventas de prueba...");

                List<Bodega> bods = bodegaRepo.findAll();
                List<Producto> prods = productoRepo.findAll();

                Bodega central = bods.get(0);
                Bodega norte = bods.get(1);

                // VENTA DÍA 1: 2026-05-28
                Venta v1 = new Venta();
                v1.setBodega(central);
                v1.setFechaVenta(LocalDateTime.of(2026, 5, 28, 11, 30));
                DetalleVenta dv1_1 = new DetalleVenta(null, v1, prods.get(0), 10, prods.get(0).getPrecioVenta()); // 10 Leches
                DetalleVenta dv1_2 = new DetalleVenta(null, v1, prods.get(3), 15, prods.get(3).getPrecioVenta()); // 15 Aguas
                v1.setDetalles(List.of(dv1_1, dv1_2));
                v1.setTotal((10 * prods.get(0).getPrecioVenta()) + (15 * prods.get(3).getPrecioVenta()));
                ventaRepo.save(v1);

                // VENTA DÍA 2: 2026-05-29 (Suben las ventas)
                Venta v2 = new Venta();
                v2.setBodega(norte);
                v2.setFechaVenta(LocalDateTime.of(2026, 5, 29, 16, 45));
                DetalleVenta dv2_1 = new DetalleVenta(null, v2, prods.get(1), 8, prods.get(1).getPrecioVenta());  // 8 Quesos
                DetalleVenta dv2_2 = new DetalleVenta(null, v2, prods.get(8), 5, prods.get(8).getPrecioVenta());  // 5 Pechugas Pollo
                v2.setDetalles(List.of(dv2_1, dv2_2));
                v2.setTotal((8 * prods.get(1).getPrecioVenta()) + (5 * prods.get(8).getPrecioVenta()));
                ventaRepo.save(v2);

                // VENTA DÍA 3: 2026-05-30 (Bajan un poco)
                Venta v3 = new Venta();
                v3.setBodega(central);
                v3.setFechaVenta(LocalDateTime.of(2026, 5, 30, 10, 15));
                DetalleVenta dv3_1 = new DetalleVenta(null, v3, prods.get(5), 12, prods.get(5).getPrecioVenta()); // 12 Bebidas Cola
                v3.setDetalles(List.of(dv3_1));
                v3.setTotal(12 * prods.get(5).getPrecioVenta());
                ventaRepo.save(v3);

                // VENTA DÍA 4: 2026-06-01 (Inicio de mes fuerte)
                Venta v4 = new Venta();
                v4.setBodega(central);
                v4.setFechaVenta(LocalDateTime.of(2026, 6, 1, 14, 20));
                DetalleVenta dv4_1 = new DetalleVenta(null, v4, prods.get(11), 6, prods.get(11).getPrecioVenta()); // 6 Shampoos
                DetalleVenta dv4_2 = new DetalleVenta(null, v4, prods.get(9), 4, prods.get(9).getPrecioVenta());   // 4 Longanizas
                v4.setDetalles(List.of(dv4_1, dv4_2));
                v4.setTotal((6 * prods.get(11).getPrecioVenta()) + (4 * prods.get(9).getPrecioVenta()));
                ventaRepo.save(v4);

                // VENTA DÍA 5: 2026-06-02 (Pico de ventas)
                Venta v5 = new Venta();
                v5.setBodega(norte);
                v5.setFechaVenta(LocalDateTime.of(2026, 6, 2, 19, 0));
                DetalleVenta dv5_1 = new DetalleVenta(null, v5, prods.get(8), 7, prods.get(8).getPrecioVenta());   // 7 Pechugas
                DetalleVenta dv5_2 = new DetalleVenta(null, v5, prods.get(12), 20, prods.get(12).getPrecioVenta()); // 20 Papas Fritas
                v5.setDetalles(List.of(dv5_1, dv5_2));
                v5.setTotal((7 * prods.get(8).getPrecioVenta()) + (20 * prods.get(12).getPrecioVenta()));
                ventaRepo.save(v5);
                
                // VENTA DÍA 6: 2026-06-03 (Hoy)
                Venta v6 = new Venta();
                v6.setBodega(central);
                v6.setFechaVenta(LocalDateTime.of(2026, 6, 3, 12, 0));
                DetalleVenta dv6_1 = new DetalleVenta(null, v6, prods.get(6), 5, prods.get(6).getPrecioVenta());   // 5 Panes de molde
                v6.setDetalles(List.of(dv6_1));
                v6.setTotal(5 * prods.get(6).getPrecioVenta());
                ventaRepo.save(v6);
            }

            System.out.println("✓ Base de datos lista con datos de prueba.");
        };
    }
}