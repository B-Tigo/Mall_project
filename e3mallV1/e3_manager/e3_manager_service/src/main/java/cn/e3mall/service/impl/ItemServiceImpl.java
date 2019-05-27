package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.common.pojo.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Override
    public TbItem getItemById(long itemId) {
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andIdEqualTo(itemId);
        //执行查询
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //去分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    /**
     * 新增商品
     */
    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建一个商品描述表对应的pojo对象。
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //返回成功
        return E3Result.ok();
    }

    /**
     * 加载商品描述
     */
    @Override
    public TbItemDesc getItemDesc(long itemId) {
        TbItemDesc ItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        return ItemDesc;
    }

    /**
     * 更新商品信息
     */
    @Override
    public E3Result updateItem(TbItem item, String desc) {
            // 1.根据商品id更新商品表
            Long id = item.getId();
            // 创建查询条件，根据id更新商品表
            TbItemExample tbItemExample = new TbItemExample();
            TbItemExample.Criteria criteria = tbItemExample.createCriteria();
            criteria.andIdEqualTo(id);
            itemMapper.updateByExampleSelective(item, tbItemExample);

            // 2.根据商品id更新商品描述表
            TbItemDesc itemDesc = new TbItemDesc();
            itemDesc.setItemDesc(desc);
            TbItemDescExample tbItemDescExample = new TbItemDescExample();
            TbItemDescExample.Criteria createCriteria = tbItemDescExample.createCriteria();
            createCriteria.andItemIdEqualTo(id);
            itemDescMapper.updateByExampleSelective(itemDesc, tbItemDescExample);

            return E3Result.ok();
    }

    /**
     * 删除商品
     */
    @Override
    public E3Result delete(String ids) {
        //判断选择不为空
        if(!StringUtils.isEmpty(ids)){
            //将所选id分割
            String[] spilts = ids.split(",");
            System.out.println(ids);
            //将所有id的商品进行遍历删除
            for (String id:spilts) {
//                TbItem item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
//                System.out.println(item);
//                //进行下架操作，修改status值
//                ///1-正常，2-下架，3-删除
//                item.setStatus((byte)3);
//                itemMapper.updateByPrimaryKey(item);
                itemMapper.deleteByPrimaryKey(Long.valueOf(id));
                itemDescMapper.deleteByPrimaryKey(Long.valueOf(id));
            }
        }
        return E3Result.ok();
    }

    /**
     * 下架商品
     */
    @Override
    public E3Result instock(String ids) {
        //判断所选不为空
        if(!StringUtils.isEmpty(ids)){
            //将所选id分割
            String[] spilts = ids.split(",");
            //将所选项目进行遍历修改
            for (String id:spilts) {
                TbItem item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
                //进行下架操作，修改status值
                ///1-正常，2-下架，3-删除
                item.setStatus((byte)2);
                item.setUpdated(new Date());
                itemMapper.updateByPrimaryKey(item);
            }
        }
        return E3Result.ok();
    }

    /**
     * 上架商品
     */
    @Override
    public E3Result reshelf(String ids) {
        //判断所选不为空
        if(!StringUtils.isEmpty(ids)){
            //将所选id分割
            String[] spilts = ids.split(",");
            //将所选项目进行遍历修改
            for (String id:spilts) {
                TbItem item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
                //进行上架操作，修改status值
                ///1-正常，2-下架，3-删除
                item.setStatus((byte)1);
                item.setUpdated(new Date());
                itemMapper.updateByPrimaryKey(item);
            }
        }
        return E3Result.ok();
    }
}


