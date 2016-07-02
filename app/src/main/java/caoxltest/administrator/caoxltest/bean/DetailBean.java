package caoxltest.administrator.caoxltest.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/25.
 */
public class DetailBean {

    /**
     * count : 2
     * elements : [{"CardLID":"20134831357","ComeOutTime":"2016/6/25 16:27:53","ComeTime":"/Date(1466843273000+0800)/","Id":497,"LayoutID":"1111222222","StuName":"蒋逸欣"},{"CardLID":"20134831357","ComeOutTime":"2016/6/25 16:21:48","ComeTime":"/Date(1466842908000+0800)/","Id":496,"LayoutID":"111111111111","StuName":"蒋逸欣"}]
     * msg : 成功
     * res : 200
     */

    private int count;
    private String msg;
    private int res;
    /**
     * CardLID : 20134831357
     * ComeOutTime : 2016/6/25 16:27:53
     * ComeTime : /Date(1466843273000+0800)/
     * Id : 497
     * LayoutID : 1111222222
     * StuName : 蒋逸欣
     */

    private List<ElementsBean> elements;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public List<ElementsBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementsBean> elements) {
        this.elements = elements;
    }

    public static class ElementsBean {
        private String CardLID;
        private String ComeOutTime;
        private String ComeTime;
        private int Id;
        private String LayoutID;
        private String StuName;

        public String getCardLID() {
            return CardLID;
        }

        public void setCardLID(String CardLID) {
            this.CardLID = CardLID;
        }

        public String getComeOutTime() {
            return ComeOutTime;
        }

        public void setComeOutTime(String ComeOutTime) {
            this.ComeOutTime = ComeOutTime;
        }

        public String getComeTime() {
            return ComeTime;
        }

        public void setComeTime(String ComeTime) {
            this.ComeTime = ComeTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getLayoutID() {
            return LayoutID;
        }

        public void setLayoutID(String LayoutID) {
            this.LayoutID = LayoutID;
        }

        public String getStuName() {
            return StuName;
        }

        public void setStuName(String StuName) {
            this.StuName = StuName;
        }
    }
}
