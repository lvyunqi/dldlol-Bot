package com.mryunqi.qimenbot.Template;


import com.mryunqi.qimenbot.Controller.WordNoticeC;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.service.WordnoticeService;

/**
 * @author mryunqi
 * @date 2022/12/19
 */
public class NpcShopTpl {
    public static String npcShopMain(WordnoticeDao wordnoticeDao,WordnoticeService wordnoticeService, String data, int pageNumber, int totalPages){
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return "[NPC：地摊]\n---商品---\n"+
                data +
                "---第"+pageNumber+"页/共"+totalPages+"页\n"+
                "<可用命令>\n" +
                "查看物品 商品名\n" +
                "购买 商品名"+
                wordNoticeC.getOneWordNotice();
    }
}
