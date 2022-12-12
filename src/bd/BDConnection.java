package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConnection {

    private final String bd = "bdnotas";
    private final String url = "jdbc:mysql://localhost:3306/" + bd;
    private final String user = "root";
    private final String password = "admin";
    Connection connection = null;

    public Connection EstablecerConexion(){
        try{
            connection = DriverManager.getConnection(url, user, password);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
    public void DesconectarBD(){
        try{
            connection.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}
