package webService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	public MySQL() {
		
	}
	
	public static Connection connect(){
		try {
	        Class.forName("com.mysql.jdbc");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tccanhembi", "tccanhembi", "tccanhembi");
	        return con;
	    }
	    catch (ClassNotFoundException e) {
	        System.out.println("Driver not found");
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	public static boolean valida(String imei, String simid, String mac, String cert){
		try {
			Class.forName("com.mysql.jdbc.Driver") ;
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tccanhembi", "tccanhembi", "tccanhembi");
			Statement stmt = con.createStatement() ;
			ResultSet rs = stmt.executeQuery(queryMontada(imei, simid, mac, cert)) ;
			
			//verifica se há algum resultado na query
			return (rs.isBeforeFirst()) ? true : false;
	    }
	    catch (ClassNotFoundException e) {
	        System.out.println("Driver not found");
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
		return false;
	}
	
	public static String queryMontada(String imei, String simid, String mac, String cert){
		return "SELECT TB_DISPOSITIVOS.MAC_ADRESS, TB_SMARTPHONE.IMEI, TB_SMARTPHONE.ICCID, TB_USUARIOS.CERTIFICADO FROM TB_DISPOSITIVOS INNER JOIN TB_DISPOSITIVOS_HAS_TB_GRUPOS ON TB_DISPOSITIVOS_HAS_TB_GRUPOS.TB_DISPOSITIVOS_ID_DISPOSITIVO = TB_DISPOSITIVOS.ID_DISPOSITIVO  INNER JOIN  TB_GRUPOS ON TB_DISPOSITIVOS_HAS_TB_GRUPOS.TB_GRUPOS_ID_GRUPO = TB_GRUPOS.ID_GRUPO  INNER JOIN TB_USUARIOS_HAS_TB_GRUPOS ON TB_USUARIOS_HAS_TB_GRUPOS.TB_GRUPOS_ID_GRUPO = TB_GRUPOS.ID_GRUPO INNER JOIN TB_USUARIOS ON TB_USUARIOS_HAS_TB_GRUPOS.TB_USUARIOS_ID_USUARIO = TB_USUARIOS.ID_USUARIO INNER JOIN TB_SMARTPHONE ON TB_SMARTPHONE.TB_USUARIOS_ID_USUARIO = TB_USUARIOS.ID_USUARIO WHERE TB_DISPOSITIVOS.MAC_ADRESS = '" + mac + "' AND TB_SMARTPHONE.IMEI = '" + imei + "' AND TB_SMARTPHONE.ICCID = '" + simid + "' AND TB_USUARIOS.CERTIFICADO = '" + cert + "';";
	}
	
	

}
