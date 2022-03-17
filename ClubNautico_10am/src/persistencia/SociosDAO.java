
package persistencia;

import entidades.Socio;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class SociosDAO implements ISociosDAO {

    private IConexionBD conexionBD;

    public SociosDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }       
    
    @Override
    public boolean agregar(Socio socio) {
        try{
            // ESTABLECEMOS UNA CONEXION CON MYSQL.. SI NO SE PUEDE LANZA SQLEXCEPTION
            Connection conexion = this.conexionBD.crearConexion();
            
            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();
            
            String codigoSQL = String.format("INSERT INTO socios (nombre, curp) "
                    + "VALUES ('%s', '%s');",
                    socio.getNombre(),
                    socio.getCurp());
            
            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosAfectados = comandoSql.executeUpdate(codigoSQL);
            
            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();
            
            //return numeroRegistrosAfectados == 1;
            
            if(numeroRegistrosAfectados == 1){
                return true;
            }else{
                return false;
            }                        
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }  
    }

    @Override
    public boolean actualizar(Socio socio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(Long idSocio) {
        try{
            // ESTABLECEMOS UNA CONEXION CON MYSQL.. SI NO SE PUEDE LANZA SQLEXCEPTION
            Connection conexion = this.conexionBD.crearConexion();
            
            // DEFINIMOS UN OBJETO STATEMENT PARA ENVIAR COMANDOS SQL
            Statement comandoSql = conexion.createStatement();
            
            String codigoSQL = String.format("DELETE * FROM socios WHERE id_socios = %d;",
                    idSocio);
            
            // ESTE MÉTODO SE UTILIZA PARA HACER OPERACIONES QUE ALTEREN LOS DATOS (INSERT, DELETE, UPDATE)
            int numeroRegistrosBorrados = comandoSql.executeUpdate(codigoSQL);
            
            // SOLICITAR CERRAR EXPLICITAMENTE LA CONEXIÓN HACIA LA BD
            conexion.close();
            
            //return numeroRegistrosAfectados == 1;
            
            if(numeroRegistrosBorrados == 1){
                return true;
            }else{
                return false;
            }                        
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public Socio consultar(Long idSocio) {
        Socio socio = null;
        try{
            Connection conexion = this.conexionBD.crearConexion();
           
           Statement comandoSQL = conexion.createStatement();
           
           String codigoSQL = String.format("SELECT id_socios,nombre,curp FROM socios WHERE id_socios = %d;",idSocio);
    
           ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
           
           
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return;
        }
    }

    @Override
    public List<Socio> consultarTodos() {
        List<Socio>listaSocios = new ArrayList<Socio>();
        try{
            
           Connection conexion = this.conexionBD.crearConexion();
           
           Statement comandoSQL = conexion.createStatement();
           
           String codigoSQL = String.format("SELECT * FROM socios;");
           ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
           
           while(resultado.next()){
               String nombre = resultado.getString("nombre");
               String curp = resultado.getString("curp");
               Long idSocio = resultado.getLong("id_socios");
               Socio socio = new Socio(idSocio,nombre,curp);
               listaSocios.add(socio);
   
           }
           
           conexion.close();
           return listaSocios;
           
           
           
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return listaSocios;
        }
        
        
    }
    
}
