package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;

/**
 * This class interacts directly with the database in order to create, delete, 
 * and manipulate user data
 * @author bdavi
 */
public class UserDB
{
    /**
     * Create a list of User objects from every user in the database
     * @return list of User objects
     * @throws SQLException 
     */
    public List<User> getAllUsers() throws SQLException
    {
        //List<User> users = new ArrayList<>();
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try
        {
            //use the auto generated named query from the User entity to return a list of users
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;

        } finally
        {
            em.close();
        }
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM user";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String email      = rs.getString(1);
                boolean isActive   = rs.getBoolean(2);
                String firstName  = rs.getString(3);
                String lastName   = rs.getString(4);
                String password   = rs.getString(5);
                String role       = rs.getString(6);
                
                User currentUser = new User(email, isActive, firstName, lastName, password, role);
                users.add(currentUser);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }*/
    }
    /**
     * Retrieve a single user from the database based on the email primary key
     * @param email the primary key to search for
     * @return the User object matching the email passed to it
     * @throws SQLException 
     */
    public User getUser(String email) throws SQLException
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try
        {
            User theUser = em.find(User.class, email);
            return theUser;
        } finally
        {
            em.close();
        }
        /*User theUser = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                //String email      = rs.getString(1);
                boolean isActive   = rs.getBoolean(2);
                String firstName  = rs.getString(3);
                String lastName   = rs.getString(4);
                String password   = rs.getString(5);
                String role       = rs.getString(6);
                
                theUser = new User(email, isActive, firstName, lastName, password, role);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }*/
    }
    /**
     * Add a User object to the database as a new row in the Users table
     * @param user the User object to add
     * @throws SQLException 
     */
    public void addUser(User user) throws SQLException
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try
        {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex)
        {
            trans.rollback();
        } finally
        {
            em.close();
        }
        
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO user (email, active, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setBoolean(2, user.isIsActive());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getPassword());
            int role;
            
            if(user.getRole().equals("system admin"))
            {
                role = 1;
            }
            else if(user.getRole().equals("regular user"))
            {
                role = 2;
            }
            else if(user.getRole().equals("company admin"))
            {
                role = 3;
            }
            else
            {
                role = -1;
            }
            
            ps.setInt(6, role);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }*/
    }
    /**
     * Change data for a user in the users table
     * @param user the User object to edit
     * @throws SQLException 
     */
    public void updateUser(User user) throws SQLException
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try
        {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex)
        {
            trans.rollback();
        } finally
        {
            em.close();
        }
        
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE user SET active=?, first_name=?, last_name=?, password=?, role=? where email=?";
        //this may or may not work. I'm not entirely sure how the order of ?'s will be read
        try {
            ps = con.prepareStatement(sql);
            ps.setString(6, user.getEmail());
            ps.setBoolean(1, user.isIsActive());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getPassword());
            int role;
            
            if(user.getRole().equals("system admin"))
            {
                role = 1;
            }
            else if(user.getRole().equals("regular user"))
            {
                role = 2;
            }
            else if(user.getRole().equals("company admin"))
            {
                role = 3;
            }
            else
            {
                role = -1;
            }
            
            ps.setInt(5, role);
            
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }*/
    }
    /**
     * Remove a row (a user) from the database users table
     * @param email the primary key of the User to remove
     * @throws SQLException 
     */
    public void deleteUser(String email) throws SQLException
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try
        {
            trans.begin();
            //get a User based on email, merge it, then remove it
            em.remove(em.merge(em.find(User.class, email)));
            trans.commit();
        } catch (Exception ex)
        {
            trans.rollback();
        } finally
        {
            em.close();
        }
        
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM user WHERE email=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }*/
    }
}
