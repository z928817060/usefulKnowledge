package com.study.usefulknowledge.MysqlTest;
import java.sql.SQLException;
import java.util.List;

/**
 * 控制层，直接在这里构建数据，界面的数据则通过请求传递接收即可，亦是同理
 *
 * @author AlanLee
 *
 */
public class GoddessAction
{
    /**
     * 新增女神
     *
     * @param goddess
     * @throws Exception
     */
    public void add(Goddess goddess) throws Exception
    {
        GoddessDao dao = new GoddessDao();
        goddess.setName("苍井空");
        goddess.setMobie("52220000");
        goddess.setEmail("52220000@qq.com");
        goddess.setAddress("北京红灯区");
        dao.addGoddess(goddess);
    }

    /**
     * 查询单个女神
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Goddess get(Integer id) throws SQLException
    {
        GoddessDao dao = new GoddessDao();
        return dao.queryById(id);
    }

    /**
     * 修改女神
     *
     * @param goddess
     * @throws Exception
     */
    public void edit(Goddess goddess) throws Exception
    {
        GoddessDao dao = new GoddessDao();
        dao.updateGoddess(goddess);
    }

    /**
     * 删除女神
     *
     * @param id
     * @throws SQLException
     */
    public void del(Integer id) throws SQLException
    {
        GoddessDao dao = new GoddessDao();
        dao.deleteGoddess(id);
    }

    /**
     * 查询全部女神
     *
     * @return
     * @throws Exception
     */
    public List<Goddess> query() throws Exception
    {
        GoddessDao dao = new GoddessDao();
        return dao.query();
    }

    /**
     * 测试是否成功
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException
    {
        GoddessDao goddessDao = new GoddessDao();

        List<Goddess> goddessList = goddessDao.query();

        for (Goddess goddess : goddessList)
        {
            System.out.println(goddess.getName() + "," + goddess.getMobie() + "," + goddess.getEmail());
        }
    }
}
