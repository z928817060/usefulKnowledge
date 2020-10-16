package com.study.usefulknowledge.MysqlTest;

import java.util.List;

import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;

/**
 * 数据层处理类
 *
 * @author AlanLee
 *
 */
public class GoddessDao
{
    /**
     * 查询全部女神
     *
     * @return
     * @throws SQLException
     */
    public List<Goddess> query() throws SQLException
    {
        List<Goddess> goddessList = new ArrayList<Goddess>();

        // 获得数据库连接
        Connection conn = DBUtil.getConnection();

        StringBuilder sb = new StringBuilder();
        sb.append("select id,name,mobie,email,address from goddess");

        // 通过数据库的连接操作数据库，实现增删改查
        PreparedStatement ptmt = conn.prepareStatement(sb.toString());

        ResultSet rs = ptmt.executeQuery();

        Goddess goddess ;

        while (rs.next())
        {
            goddess = new Goddess();
            goddess.setId(rs.getInt("id"));
            goddess.setName(rs.getString("name"));
            goddess.setMobie(rs.getString("mobie"));
            goddess.setEmail(rs.getString("email"));
            goddess.setAddress(rs.getString("address"));

            goddessList.add(goddess);
        }
        return goddessList;
    }

    /**
     * 查询单个女神
     *
     * @return
     * @throws SQLException
     */
    public Goddess queryById(Integer id) throws SQLException
    {
        Goddess g = null;

        Connection conn = DBUtil.getConnection();

        String sql = "" + " select * from imooc_goddess " + " where id=? ";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setInt(1, id);

        ResultSet rs = ptmt.executeQuery();

        while (rs.next())
        {
            g = new Goddess();
            g.setId(rs.getInt("id"));
            g.setName(rs.getString("name"));
            g.setMobie(rs.getString("mobie"));
            g.setEmail(rs.getString("email"));
            g.setAddress(rs.getString("address"));
        }

        return g;
    }

    /**
     * 添加女神
     *
     * @throws SQLException
     */
    public void addGoddess(Goddess goddess) throws SQLException
    {
        // 获得数据库连接
        Connection conn = DBUtil.getConnection();

        String sql = "insert into goddess(name,mobie,email,address) values(?,?,?,?)";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setString(1, goddess.getName());
        ptmt.setString(2, goddess.getMobie());
        ptmt.setString(3, goddess.getEmail());
        ptmt.setString(4, goddess.getAddress());

        ptmt.execute();
    }

    /**
     * 修改女神资料
     *
     * @throws SQLException
     */
    public void updateGoddess(Goddess goddess) throws SQLException
    {
        Connection conn = DBUtil.getConnection();

        String sql = "update goddess set name=?,mobie=?,email=?,address=? where id=?";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setString(1, goddess.getName());
        ptmt.setString(2, goddess.getMobie());
        ptmt.setString(3, goddess.getEmail());
        ptmt.setString(4, goddess.getAddress());
        // TODO: 2018/8/5 还需要id,id的获得见 NMC_CurrentCondition 的line56

        ptmt.execute();
    }

    /**
     * 删除女神
     *
     * @throws SQLException
     */
    public void deleteGoddess(Integer id) throws SQLException
    {
        Connection conn = DBUtil.getConnection();

        String sql = "delete from goddess where id=?";

        PreparedStatement ptmt = conn.prepareStatement(sql);

        ptmt.setInt(1, id);

        ptmt.execute();
    }
}
