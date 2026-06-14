package BackendSuperNova.ms_inventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BackendSuperNova.ms_inventario.model.*;
import BackendSuperNova.ms_inventario.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(
            CategoriaRepository categoriaRepo,
            ProveedorRepository proveedorRepo,
            ProductoRepository productoRepo,
            StockRepository stockRepo,
            MovimientoStockRepository movimientoRepo,
            PedidoRepository pedidoRepo,
            DetallePedidoRepository detallePedidoRepo,
            VentaRepository ventaRepo) {

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
                    {"77.123.456-3", "AquaPura S.A.",         "+56 9 8877 6655", "pedidos@aquapura.cl"},
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

            // ─── 3. Productos ─────────────────────────────────────────
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

            // ─── 4. Stock (MODIFICADO CON IDs NUMÉRICOS) ──────────────────
            if (stockRepo.count() == 0) {
                System.out.println("Cargando stock...");

                List<Producto> prods = productoRepo.findAll();
                
                // En vez de buscar objetos Bodega, usamos directamente sus IDs esperados (1L y 2L)
                Long centralId = 1L;
                Long norteId   = 2L;

                Object[][] stocks = {
                    {prods.get(0),  centralId,  80, 20, "2026-05-01", "2026-08-01"},
                    {prods.get(1),  centralId,  45, 10, "2026-05-01", "2026-07-15"},
                    {prods.get(2),  centralId,  60, 15, "2026-05-01", "2026-06-30"},
                    {prods.get(3),  centralId, 120, 30, "2026-05-01", null},
                    {prods.get(4),  centralId,  55, 15, "2026-05-01", "2026-09-01"},
                    {prods.get(5),  centralId,  90, 20, "2026-05-01", "2026-10-01"},
                    {prods.get(6),  centralId,   8, 10, "2026-05-20", "2026-05-27"},
                    {prods.get(7),  centralId,  30, 15, "2026-05-20", "2026-05-25"},
                    {prods.get(8),  centralId,  25,  8, "2026-05-20", "2026-05-26"},
                    {prods.get(9),  centralId,  40,  8, "2026-05-10", "2026-08-10"},
                    {prods.get(10), centralId,  35, 10, "2026-05-01", null},
                    {prods.get(11), centralId,  22,  8, "2026-05-01", null},
                    {prods.get(12), centralId,  50, 12, "2026-05-01", "2026-12-31"},
                    {prods.get(13), centralId,  48, 10, "2026-05-01", "2027-01-01"},
                    {prods.get(14), centralId,   5,  6, "2026-05-01", "2026-07-01"},
                    {prods.get(0),  norteId,    40, 20, "2026-05-05", "2026-08-01"},
                    {prods.get(3),  norteId,    60, 30, "2026-05-05", null},
                    {prods.get(6),  norteId,     5, 10, "2026-05-20", "2026-05-27"},
                    {prods.get(10), norteId,    18, 10, "2026-05-05", null},
                };

                for (Object[] s : stocks) {
                    Stock stock = new Stock();
                    stock.setProducto((Producto) s[0]);
                    stock.setBodegaId((Long) s[1]); // Cambiado a setBodegaId
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
                        + " en Bodega ID: " + guardado.getBodegaId()); // Cambiado para evitar getBodega()
                    movimientoRepo.save(mov);
                }
            }

            // ─── 5. Pedidos ───────────────────────────────────────────
            if (pedidoRepo.count() == 0) {
                System.out.println("Cargando pedidos...");

                List<Proveedor> provs = proveedorRepo.findAll();
                List<Producto>  prods = productoRepo.findAll();

                // Pedido 1
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

                // Pedido 2
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

                // Pedido 3
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

                // Pedido 4
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

            // ─── 6. Ventas ──────────────────────────────────────────────────
            if (ventaRepo.count() == 0) {
                System.out.println("Cargando historial de ventas de prueba...");

                List<Producto> prods = productoRepo.findAll();

                Long centralId = 1L;
                Long norteId   = 2L;

                // VENTA DÍA 1: 2026-05-28
                Venta v1 = new Venta();
                v1.setFechaVenta(LocalDateTime.of(2026, 4, 28, 11, 30));
                DetalleVenta dv1_1 = new DetalleVenta(null, v1, prods.get(0), centralId, 10, prods.get(0).getPrecioVenta());
                DetalleVenta dv1_2 = new DetalleVenta(null, v1, prods.get(3), centralId, 15, prods.get(3).getPrecioVenta());
                v1.setDetalles(List.of(dv1_1, dv1_2));
                v1.setTotal((10 * prods.get(0).getPrecioVenta()) + (15 * prods.get(3).getPrecioVenta()));
                ventaRepo.save(v1);

                // VENTA DÍA 2: 2026-05-29
                Venta v2 = new Venta();
                v2.setFechaVenta(LocalDateTime.of(2026, 4, 29, 16, 45));
                DetalleVenta dv2_1 = new DetalleVenta(null, v2, prods.get(1), norteId, 8, prods.get(1).getPrecioVenta());
                DetalleVenta dv2_2 = new DetalleVenta(null, v2, prods.get(8), norteId, 5, prods.get(8).getPrecioVenta());
                v2.setDetalles(List.of(dv2_1, dv2_2));
                v2.setTotal((8 * prods.get(1).getPrecioVenta()) + (5 * prods.get(8).getPrecioVenta()));
                ventaRepo.save(v2);

                // VENTA DÍA 3: 2026-05-30
                Venta v3 = new Venta();
                v3.setFechaVenta(LocalDateTime.of(2026, 4, 30, 10, 15));
                DetalleVenta dv3_1 = new DetalleVenta(null, v3, prods.get(5), centralId, 12, prods.get(5).getPrecioVenta());
                v3.setDetalles(List.of(dv3_1));
                v3.setTotal(12 * prods.get(5).getPrecioVenta());
                ventaRepo.save(v3);

                // VENTA DÍA 4: 2026-06-01
                Venta v4 = new Venta();
                v4.setFechaVenta(LocalDateTime.of(2026, 5, 1, 14, 20));
                DetalleVenta dv4_1 = new DetalleVenta(null, v4, prods.get(11), centralId, 6, prods.get(11).getPrecioVenta());
                DetalleVenta dv4_2 = new DetalleVenta(null, v4, prods.get(9),  centralId, 4, prods.get(9).getPrecioVenta());
                v4.setDetalles(List.of(dv4_1, dv4_2));
                v4.setTotal((6 * prods.get(11).getPrecioVenta()) + (4 * prods.get(9).getPrecioVenta()));
                ventaRepo.save(v4);

                // VENTA DÍA 5: 2026-06-02
                Venta v5 = new Venta();
                v5.setFechaVenta(LocalDateTime.of(2026, 5, 2, 19, 0));
                DetalleVenta dv5_1 = new DetalleVenta(null, v5, prods.get(8),  norteId, 7,  prods.get(8).getPrecioVenta());
                DetalleVenta dv5_2 = new DetalleVenta(null, v5, prods.get(12), norteId, 20, prods.get(12).getPrecioVenta());
                v5.setDetalles(List.of(dv5_1, dv5_2));
                v5.setTotal((7 * prods.get(8).getPrecioVenta()) + (20 * prods.get(12).getPrecioVenta()));
                ventaRepo.save(v5);

                // VENTA DÍA 6: 2026-06-03
                Venta v6 = new Venta();
                v6.setFechaVenta(LocalDateTime.of(2026, 5, 3, 12, 0));
                DetalleVenta dv6_1 = new DetalleVenta(null, v6, prods.get(6), centralId, 5, prods.get(6).getPrecioVenta());
                v6.setDetalles(List.of(dv6_1));
                v6.setTotal(5 * prods.get(6).getPrecioVenta());
                ventaRepo.save(v6);

                // ─── JULIO 2026 ───────────────────────────────────────────────

                // VENTA DÍA 7: 2026-07-02
                Venta v7 = new Venta();
                v7.setFechaVenta(LocalDateTime.of(2026, 6, 2, 9, 15));
                DetalleVenta dv7_1 = new DetalleVenta(null, v7, prods.get(0),  centralId, 20, prods.get(0).getPrecioVenta());
                DetalleVenta dv7_2 = new DetalleVenta(null, v7, prods.get(4),  centralId, 12, prods.get(4).getPrecioVenta());
                v7.setDetalles(List.of(dv7_1, dv7_2));
                v7.setTotal((20 * prods.get(0).getPrecioVenta()) + (12 * prods.get(4).getPrecioVenta()));
                ventaRepo.save(v7);

                // VENTA DÍA 8: 2026-07-05
                Venta v8 = new Venta();
                v8.setFechaVenta(LocalDateTime.of(2026, 6, 5, 14, 0));
                DetalleVenta dv8_1 = new DetalleVenta(null, v8, prods.get(2),  norteId, 15, prods.get(2).getPrecioVenta());
                DetalleVenta dv8_2 = new DetalleVenta(null, v8, prods.get(13), norteId, 10, prods.get(13).getPrecioVenta());
                v8.setDetalles(List.of(dv8_1, dv8_2));
                v8.setTotal((15 * prods.get(2).getPrecioVenta()) + (10 * prods.get(13).getPrecioVenta()));
                ventaRepo.save(v8);

                // VENTA DÍA 9: 2026-07-10
                Venta v9 = new Venta();
                v9.setFechaVenta(LocalDateTime.of(2026, 6, 10, 11, 30));
                DetalleVenta dv9_1 = new DetalleVenta(null, v9, prods.get(7),  centralId, 18, prods.get(7).getPrecioVenta());
                DetalleVenta dv9_2 = new DetalleVenta(null, v9, prods.get(10), centralId,  8, prods.get(10).getPrecioVenta());
                v9.setDetalles(List.of(dv9_1, dv9_2));
                v9.setTotal((18 * prods.get(7).getPrecioVenta()) + (8 * prods.get(10).getPrecioVenta()));
                ventaRepo.save(v9);

                // VENTA DÍA 10: 2026-07-15
                Venta v10 = new Venta();
                v10.setFechaVenta(LocalDateTime.of(2026, 6, 15, 17, 45));
                DetalleVenta dv10_1 = new DetalleVenta(null, v10, prods.get(5),  norteId, 25, prods.get(5).getPrecioVenta());
                DetalleVenta dv10_2 = new DetalleVenta(null, v10, prods.get(12), norteId, 15, prods.get(12).getPrecioVenta());
                v10.setDetalles(List.of(dv10_1, dv10_2));
                v10.setTotal((25 * prods.get(5).getPrecioVenta()) + (15 * prods.get(12).getPrecioVenta()));
                ventaRepo.save(v10);

                // VENTA DÍA 11: 2026-07-20
                Venta v11 = new Venta();
                v11.setFechaVenta(LocalDateTime.of(2026, 6, 20, 10, 0));
                DetalleVenta dv11_1 = new DetalleVenta(null, v11, prods.get(9),  centralId, 6, prods.get(9).getPrecioVenta());
                DetalleVenta dv11_2 = new DetalleVenta(null, v11, prods.get(11), centralId, 9, prods.get(11).getPrecioVenta());
                DetalleVenta dv11_3 = new DetalleVenta(null, v11, prods.get(3),  centralId, 30, prods.get(3).getPrecioVenta());
                v11.setDetalles(List.of(dv11_1, dv11_2, dv11_3));
                v11.setTotal((6 * prods.get(9).getPrecioVenta()) + (9 * prods.get(11).getPrecioVenta()) + (30 * prods.get(3).getPrecioVenta()));
                ventaRepo.save(v11);

                // VENTA DÍA 12: 2026-07-25
                Venta v12 = new Venta();
                v12.setFechaVenta(LocalDateTime.of(2026, 6, 25, 16, 20));
                DetalleVenta dv12_1 = new DetalleVenta(null, v12, prods.get(1), norteId, 10, prods.get(1).getPrecioVenta());
                DetalleVenta dv12_2 = new DetalleVenta(null, v12, prods.get(6), norteId,  8, prods.get(6).getPrecioVenta());
                v12.setDetalles(List.of(dv12_1, dv12_2));
                v12.setTotal((10 * prods.get(1).getPrecioVenta()) + (8 * prods.get(6).getPrecioVenta()));
                ventaRepo.save(v12);
            

                System.out.println("✓ Base de datos de Inventario lista con datos de prueba.");
            }
        };
    }    
}