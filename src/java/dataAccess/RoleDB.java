package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.persistence.EntityManager;
import models.Role;
import models.User;

/**
 * This class interacts directly with the database, and the roles table
 * @author bdavi
 */
public class RoleDB
{  
    /**
     * Return a single role name based on its ID number
     * @param roleID
     * @return
     * @throws SQLException 
     */
    
    
    public String getRole(int roleID) throws SQLException 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try
        {
            Role theRole = em.find(Role.class, roleID);
            return theRole.getRoleName();
        } finally
        {
            em.close();
        }
        
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        String roleTxt;
        
        String sql = "SELECT role_name FROM role where role_id=?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, roleID);
            rs = ps.executeQuery();
            rs.next();
            roleTxt = rs.getString(1);
            }
         finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
            
        return roleTxt;*/
    }
    
    /**
     * Returns a list of all role names from the roles table
     * @return
     * @throws SQLException 
     */
    public List<Role> getAllRoles() throws SQLException 
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try
        {
            //use the auto generated named query from the User entity to return a list of users
            List<Role> roleObjs = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            List<String> roles = new Vector<>();
            for(int i = 0; i < roleObjs.size(); i++)
            {
                roles.add(roleObjs.get(i).getRoleName());
            }
            return roleObjs;

        } finally
        {
            em.close();
        }
        
        /*ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> roleTxt = new ArrayList<String>();
        
        String sql = "SELECT role_name FROM role";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
                while(rs.next())
                {
                    roleTxt.add(rs.getString(1));
                }
            }
         finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
            
        return roleTxt;*/
    }
}

