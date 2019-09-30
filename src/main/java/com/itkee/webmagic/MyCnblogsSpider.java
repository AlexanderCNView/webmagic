package com.itkee.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MyCnblogsSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private boolean request = false;
    @Override
    public void process(Page page) {
        if (request) {
            System.out.println(page.getUrl().toString());
            page.putField("ID",page.getHtml().xpath("/html/body/div[1]/div/div[3]/p/text()"));
            page.putField("url",page.getUrl().toString());
            page.putField("time",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[1]/text()"));
            page.putField("type",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[2]/text()"));
            page.putField("company",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[3]/a/text()"));
            page.putField("num",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[4]/text()"));
            page.putField("price",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[5]/text()"));
            page.putField("money",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[6]/span/text()"));
            page.putField("Proportion",page.getHtml().xpath("/html/body/div[1]/div/div[6]/div/ul/li[2]/ul/li[7]/span/text()"));
            page.putField("allMoney",page.getHtml().xpath("/html/body/div[1]/div/div[4]/div[1]/div[2]/p[2]/text()"));
            page.putField("bisai",page.getHtml().xpath("/html/body/div[1]/div/div[2]/div[1]/p[1]/strong/text()"));
            page.putField("mingci",page.getHtml().xpath("/html/body/div[1]/div/div[4]/div[3]/div/p[2]/text()"));
        }
        else {
            page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/div[2]/div[5]/div[3]/ul/li[1]/ul/a/@href").all());
            page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/div[2]/div[5]/div[3]/ul/li[2]/ul/a/@href").all());
            page.addTargetRequests(page.getHtml().xpath("/html/body/div[1]/div[2]/div[5]/div[3]/ul/li[3]/ul/a/@href").all());
            request = true;
        }
    }
    @Override
    public Site getSite() {
        return site;
    }
}

class MyPipeline implements Pipeline {
    public MyPipeline() {
    }
    @Override
    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
        String url = "url";
        if(mapResults.containsKey(url)){
            String u = mapResults.get("url").toString();
            Map mm = Msgsend.getMap(u);
            if(mm==null){
                setMsg(mapResults);
            }
            else{
                String time = mapResults.get("time").toString();
                String lastDate = mm.get("time").toString();
                if (!time.equals(lastDate)) {
                    Msgsend.datalist.remove(mm);
                    setMsg(mapResults);
                }
            }
        }
    }

    public int[] twoSum(int[] nums, int target) {

        return null;
    }

    public void setMsg(Map mapResults){
        System.out.println(mapResults.get("time"));
        Msgsend.lastDate = mapResults.get("time").toString();
        String type = mapResults.get("type").toString();
        String ID = mapResults.get("ID").toString();
        String num = mapResults.get("num").toString();
        String price = mapResults.get("price").toString();
        System.out.println(price);
        String allMoney = mapResults.get("allMoney").toString();
        String[] f = allMoney.split(",");
        String ff = "";
        for (int i=0;i<f.length;i++){
            ff = ff + f[i];
        }
        String Proportion = mapResults.get("Proportion").toString();
        String bisai = mapResults.get("bisai").toString();
        String url = mapResults.get("url").toString();
        String mingci = mapResults.get("mingci").toString();
        double zb = Double.valueOf(num) * Double.valueOf(price) / Double.valueOf(ff) * 100;
        DecimalFormat df = new DecimalFormat("#.000");
        String zbs = df.format(zb);
        double gushujianyi = Double.valueOf(num) * 50000 / Double.valueOf(ff) * 100;
        String gushujianyis = df.format(gushujianyi);
        // 输出到控制台
        Map map = new HashMap();
        String str = String.format("ID:%s\n" +
                "成交时间：%s\n" +
                "交易行为：%s\n" +
                "数量：%s\n" +
                "成交价格：%s\n" +
                "单笔盈亏比：%s\n" +
                "仓位占比：%s\n" +
                "操作股数建议：%s\n" +
                "比赛：%s\n" +
                "榜单：%s\n" +
                "名次：%s\n" +
                "详情：%s",ID,Msgsend.lastDate,type,num,price,Proportion,zbs+"%",gushujianyis+"$",bisai,"总榜单",mingci,url);
        map.put("msg",str);
        map.put("phone","18363857597,15563886389");
        Msgsend.sendSmsByPost(map);
        Msgsend.datalist.add(mapResults);

    }
}
