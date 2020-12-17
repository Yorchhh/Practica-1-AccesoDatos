package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainJardineria {
	static Connection c;

	public static void main(String[] args) throws SQLException {

		c = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/jardineria?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				"root", "Waterpolo7");

		int opcion,opcionBusqueda;
		do {
			
			opcion = imprimirMenu();
			
			Scanner kb = new Scanner(System.in);
			switch (opcion) {

			case 1:
				crearCliente(c);
				break;

			case 2:
				mostrarById(c);
				break;
			case 3:
				mostrarClientes(c);
				break;
			case 4:
				opcionBusqueda=menuBusquedaCliente();
				switch(opcionBusqueda) {
				case 1: 
					findByNombreCliente(c);
					break;
				case 2: 
					findByNombreContacto(c);
					break;
				case 3:
					findByApellidoContacto(c);
					break;
				
				}
				
				break;
			case 5:
				editarProoducto(c);
				break;
			case 6:
				System.out.println("* HA SALIDO DEL PROGRAMA *");
				break;

			}

		} while (opcion != 6);
	}

	public static int imprimirMenu() {
		int opcion;
		Scanner kb = new Scanner(System.in);
		System.out.println("*************** MENU *******************");
		System.out.println("1.- Añadir un cliente");
		System.out.println("2.- Mostrar un cliente");
		System.out.println("3.- Mostrar todos los clientes");
		System.out.println("4.- Buscar un cliente");
		System.out.println("5.- Editar un producto");
		System.out.println("6.- Salir del programa");
		opcion = kb.nextInt();
		return opcion;

	}

	public static void crearCliente(Connection c) {
		// Preguntamos al usuario los datos a introducir
		Scanner kb = new Scanner(System.in);
		System.out.println("Introduzca el codigo del cliente , por favor");
		int cod = kb.nextInt();
		System.out.println("Introduzca el nombre del cliente, por favor");
		String nombreCli = kb.next();
		System.out.println("Introduce el nombre de contacto, por favor");
		String nombreContacto = kb.next();
		System.out.println("Introduce el apellido del contacto, por favor");
		String apellidoContacto = kb.next();
		System.out.println("Introduce el telefono, porfavor");
		String telefono = kb.next();
		System.out.println("Introduce el fax, por favor");
		String fax = kb.next();
		System.out.println("Introduce la direccion principal , por favor");
		String direccion1 = kb.next();
		System.out.println("Introduce la direccion secundario, porfavor ");
		String direccion2 = kb.next();
		System.out.println("Introduce la ciudad, por favor");
		String ciudad = kb.nextLine();
		System.out.println("Introduce el pais, por favor");
		String pais = kb.nextLine();
		System.out.println("Introduce la region, por favor");
		String region = kb.next();
		System.out.println("Intrduce el codigo postal , por favor");
		int codPostal = kb.nextInt();
		System.out.println("Introduce el codigo del empleado, por favor");
		int codEmple = kb.nextInt();
		System.out.println("Introduce un limite de credito, por favor");
		int limite = kb.nextInt();

		// Vamos a introducirle los datos obtenidos mediante una sentencia sql
		PreparedStatement pst = null;
		String sqlInsertar = "INSERT INTO cliente (codigo_cliente,nombre_cliente,nombre_contacto,apellido_contacto,telefono,fax,linea_direccion1,linea_direccion2,"
				+ "ciudad,pais,region,codigo_postal,codigo_empleado_rep_ventas,limite_credito)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			pst = c.prepareStatement(sqlInsertar);
			pst.setInt(1, cod);
			pst.setString(2, nombreCli);
			pst.setString(3, nombreContacto);
			pst.setString(4, apellidoContacto);
			pst.setString(5, telefono);
			pst.setString(6, fax);
			pst.setString(7, direccion1);
			pst.setString(8, direccion2);
			pst.setString(9, ciudad);
			pst.setString(10, pais);
			pst.setString(11, region);
			pst.setInt(12, codPostal);
			pst.setInt(13, codEmple);
			pst.setInt(14, limite);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		try {
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// El codigo de empleado tiene que existir, sino nos dara error.
	public static void mostrarClientes(Connection c) {
		String sqlClientes = "SELECT * FROM cliente ORDER BY nombre_cliente ASC";
		Statement st = null;
		ResultSet rs = null;
		try {
			st = c.createStatement();
			rs = st.executeQuery(sqlClientes);
			while (rs.next()) {
				System.out.println("Codigo Empleado: " + rs.getString("codigo_cliente") + " Nombre de la Empresa: "
						+ rs.getString("nombre_cliente") + " Nombre: " + rs.getString("nombre_contacto") + " Apellidos "
						+ rs.getString("apellido_contacto") + "\n\tTelefono: " + rs.getString("telefono") + " Fax: "
						+ rs.getString("fax") + " Direccion Principal: " + rs.getString("linea_direccion1")
						+ " Direccion Secundaria: " + rs.getString("linea_direccion2") + "\n\tCiudad :"
						+ rs.getString("ciudad") + " Pais: " + rs.getString("pais") + " Region: "
						+ rs.getString("region") + " Codigo Postal: " + rs.getString("codigo_postal")
						+ " Codigo Empleado: " + rs.getString("codigo_empleado_rep_ventas") + " Limite Credito: "
						+ rs.getString("limite_credito"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void mostrarById(Connection c) {
		Scanner kb = new Scanner(System.in);
		System.out.println("Introduzca el ID del cliente que quiere buscar, por favor");
		int idCliente = kb.nextInt();
		String sqlIdCliente = "SELECT * FROM cliente WHERE codigo_cliente =" + idCliente + ";";
		Statement st = null;
		ResultSet rs = null;

		try {
			st = c.createStatement();
			rs = st.executeQuery(sqlIdCliente);
			while (rs.next()) {
				System.out.println("Codigo Empleado: " + rs.getString("codigo_cliente") + " Nombre de la Empresa: "
						+ rs.getString("nombre_cliente") + " Nombre: " + rs.getString("nombre_contacto") + " Apellidos "
						+ rs.getString("apellido_contacto") + "\n\tTelefono: " + rs.getString("telefono") + " Fax: "
						+ rs.getString("fax") + " Direccion Principal: " + rs.getString("linea_direccion1")
						+ " Direccion Secundaria: " + rs.getString("linea_direccion2") + "\n\tCiudad :"
						+ rs.getString("ciudad") + " Pais: " + rs.getString("pais") + " Region: "
						+ rs.getString("region") + " Codigo Postal: " + rs.getString("codigo_postal")
						+ " Codigo Empleado: " + rs.getString("codigo_empleado_rep_ventas") + " Limite Credito: "
						+ rs.getString("limite_credito"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static int menuBusquedaCliente() {
		int opcion;
		Scanner kb = new Scanner(System.in);
		System.out.println("BUSQUEDA CLIENTE");
		System.out.println("1.- Por Nombre de Empresa");
		System.out.println("2.- Nombre del Cliente");
		System.out.println("3.- Apellidos del Cliente");
		opcion = kb.nextInt();
		return opcion;
	}
	
	public static void findByNombreCliente(Connection c) {
		Scanner kb = new Scanner (System.in);
		System.out.println("Introduzca el Nombre de la Empresa a buscar, por favor");
		String nombreCliente = kb.nextLine();
		String sqlNombreCliente= "SELECT * FROM cliente WHERE nombre_cliente="+"'"+nombreCliente+"'"+";";
		Statement st = null;
		ResultSet rs = null;
		try {
			st= c.createStatement();
			rs= st.executeQuery(sqlNombreCliente);
			while(rs.next()) {
				System.out.println("Codigo Empleado: " + rs.getString("codigo_cliente") + " Nombre de la Empresa: "
						+ rs.getString("nombre_cliente") + " Nombre: " + rs.getString("nombre_contacto") + " Apellidos "
						+ rs.getString("apellido_contacto") + "\n\tTelefono: " + rs.getString("telefono") + " Fax: "
						+ rs.getString("fax") + " Direccion Principal: " + rs.getString("linea_direccion1")
						+ " Direccion Secundaria: " + rs.getString("linea_direccion2") + "\n\tCiudad :"
						+ rs.getString("ciudad") + " Pais: " + rs.getString("pais") + " Region: "
						+ rs.getString("region") + " Codigo Postal: " + rs.getString("codigo_postal")
						+ " Codigo Empleado: " + rs.getString("codigo_empleado_rep_ventas") + " Limite Credito: "
						+ rs.getString("limite_credito"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void findByNombreContacto(Connection c) {
		Scanner kb = new Scanner (System.in);
		System.out.println("Introduzca el Nombre del Contacto a buscar, por favor");
		String nombreContacto = kb.nextLine();
		String sqlNombreContacto= "SELECT * FROM cliente WHERE nombre_contacto='"+nombreContacto+"';";
		Statement st = null;
		ResultSet rs = null;
		try {
			st= c.createStatement();
			rs= st.executeQuery(sqlNombreContacto);
			while(rs.next()) {
				System.out.println("Codigo Empleado: " + rs.getString("codigo_cliente") + " Nombre de la Empresa: "
						+ rs.getString("nombre_cliente") + " Nombre: " + rs.getString("nombre_contacto") + " Apellidos "
						+ rs.getString("apellido_contacto") + "\n\tTelefono: " + rs.getString("telefono") + " Fax: "
						+ rs.getString("fax") + " Direccion Principal: " + rs.getString("linea_direccion1")
						+ " Direccion Secundaria: " + rs.getString("linea_direccion2") + "\n\tCiudad :"
						+ rs.getString("ciudad") + " Pais: " + rs.getString("pais") + " Region: "
						+ rs.getString("region") + " Codigo Postal: " + rs.getString("codigo_postal")
						+ " Codigo Empleado: " + rs.getString("codigo_empleado_rep_ventas") + " Limite Credito: "
						+ rs.getString("limite_credito"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void findByApellidoContacto(Connection c) {
		Scanner kb = new Scanner (System.in);
		System.out.println("Introduzca el Apellido del Contacto a buscar, por favor");
		String apellidoContacto = kb.nextLine();
		String sqlApellidoContacto= "SELECT * FROM cliente WHERE apellido_contacto='"+apellidoContacto+"';";
		Statement st = null;
		ResultSet rs = null;
		try {
			st= c.createStatement();
			rs= st.executeQuery(sqlApellidoContacto);
			while(rs.next()) {
				System.out.println("Codigo Empleado: " + rs.getString("codigo_cliente") + " Nombre de la Empresa: "
						+ rs.getString("nombre_cliente") + " Nombre: " + rs.getString("nombre_contacto") + " Apellidos "
						+ rs.getString("apellido_contacto") + "\n\tTelefono: " + rs.getString("telefono") + " Fax: "
						+ rs.getString("fax") + " Direccion Principal: " + rs.getString("linea_direccion1")
						+ " Direccion Secundaria: " + rs.getString("linea_direccion2") + "\n\tCiudad :"
						+ rs.getString("ciudad") + " Pais: " + rs.getString("pais") + " Region: "
						+ rs.getString("region") + " Codigo Postal: " + rs.getString("codigo_postal")
						+ " Codigo Empleado: " + rs.getString("codigo_empleado_rep_ventas") + " Limite Credito: "
						+ rs.getString("limite_credito"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void editarProoducto(Connection c	) {
		Scanner kb =  new Scanner(System.in);
		System.out.println("Introduce el codigo del producto a editar, por favor");
		int codProducto= kb.nextInt();
		System.out.println("Introduzca un nuevo nombre");
		String nombre=kb.next();
		System.out.println("Introduzca la nueva gama");
		String gama = kb.next();
		System.out.println("Introduzca la nueva dimension");
		String dimension= kb.next();
		System.out.println("Introduzca el nuevo proveedor");
		String proveedor= kb.next();
		System.out.println("Introduzca una nueva descripcion");
		String descripcion= kb.next();
		System.out.println("Introduzca la nueva cantidad de stock");
		short stock= kb.nextShort();
		System.out.println("Introduzca nuevo precio de venta");
		double preVenta= kb.nextDouble();
		System.out.println("Introduzca nuevo precio de proveedor");
		double preProveedor= kb.nextDouble();
		PreparedStatement pst =null;
		String sqlProducto="UPDATE producto SET nombre=?,gama=?,dimensiones=?,proveedor=?,descripcion=?,cantidad_en_stock=?,"
				+ "precio_venta=?,precio_proveedor=? WHERE codigo_producto="+codProducto+";";
		
		try {
			pst = c.prepareStatement(sqlProducto);
			pst.setString(2, nombre);
			pst.setString(3,gama);
			pst.setString(4, dimension);
			pst.setString(5, proveedor);
			pst.setString(6, descripcion);
			pst.setShort(7, stock);
			pst.setDouble(8, preVenta);
			pst.setDouble(9, preProveedor);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
