package dao;

import bd.BDConnection;
import model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listarCursos(){
        List<Curso> cursoList = new ArrayList<>();
        BDConnection connectionBD = new BDConnection();
        Connection connection = connectionBD.EstablecerConexion();
        try{
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM curso ORDER BY idcurso DESC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                cursoList.add(new Curso(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3)));
            }
            rs.close();
            st.close();
            connectionBD.DesconectarBD();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return cursoList;
    }

    public boolean registrarCurso(Curso objCurso){
        BDConnection bdConnection = new BDConnection();
        Connection connection = bdConnection.EstablecerConexion();
        boolean rpta = false;
        try {
            Statement st = connection.createStatement();
            String sql = "INSERT INTO curso (idcurso, nomcurso, credito) " +
                    " VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, objCurso.getIdcurso());
            preparedStatement.setString(2, objCurso.getNomcurso());
            preparedStatement.setInt(3, objCurso.getCredito());
            rpta = preparedStatement.executeUpdate() > 0 ? true : false;
            st.close();
            preparedStatement.close();
            bdConnection.DesconectarBD();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return rpta;
    }
}
