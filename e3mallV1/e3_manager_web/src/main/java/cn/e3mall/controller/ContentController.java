package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.pojo.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 内容管理Controller
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 新增节点
     * @param content
     * @return
     */
    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addContent(TbContent content){
        //调用服务把数据保存到数据库
        E3Result result = contentService.addContent(content);
        return result;
    }
    /**
     * 编辑节点
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult list(Long categoryId, Integer page, Integer rows){
        //调用内容服务查询指定内容
        EasyUIDataGridResult result = contentService.getItemList(categoryId,page,rows);
        return result;
    }
    /**
     * 删除节点
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(String [] ids){
        E3Result result=contentService.deleteBatchContent(ids);
        return result;
    }
}
