package bloodbank.blood4life;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBFunctions {
    public Connection connect_to_db(String dbname, String user, String pass){
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname,user,pass);
            if(con != null){
                System.out.println("Connected to database");
            }else{
                System.out.println("Failed to connect to database");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
    public void createTable(Connection con, String table_name){
        Statement stmt;
        try{
            String query = "create table " + table_name + " (donorid Serial, name varchar(200), address varchar(200), primary key(donorid));";
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Table created");
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public void insert_row(Connection con,String table_name, String name,String address){
        Statement stmt;
        try {
            String query = String.format("insert into %s(name,address) values('%s','%s');", table_name, name, address);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Row inserted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void read_data(Connection con, String table_name){
        Statement stmt;
        try{
            String query = String.format("select * from %s", table_name);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("donorid")+" "+rs.getString("name")+" "+rs.getString("address"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void update_name(Connection con, String table_name,String old_name, String new_name){
        Statement stmt;
        try{
            String query =String.format("update %s set name='%s' where name='%s'",table_name,new_name,old_name);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Name updated");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void search_by_name(Connection con, String table_name, String name){
        Statement stmt;
        try {
            String query = String.format("select * from %s where name = '%s'", table_name,name);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("donorid")+" "+rs.getString("name")+" "+rs.getString("address"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void delete_row_by_name(Connection con, String table_name, String name){
        Statement stmt;
        try{
            String query = String.format("delete from %s where name = '%s'", table_name,name);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Row deleted");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
