package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xiaxi on 2016/11/30.
 */
public class BookBean  extends BmobObject{
    String name;
    String writer;
    String introduce;
    String coverImg;
    int ownNumber;
    int readNumber;
    /**
     * showType=0 未被审核通过  只能推荐的人自己可见
     * showType=1  可见
     */
    int showType;
    BmobUser recommendPerson;
    BmobFile coverImage;

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public BmobFile getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(BmobFile coverImage) {
        this.coverImage = coverImage;
    }

    public BmobUser getRecommendPerson() {
        return recommendPerson;
    }

    public void setRecommendPerson(BmobUser recommendPerson) {
        this.recommendPerson = recommendPerson;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnNumber() {
        return ownNumber;
    }

    public void setOwnNumber(int ownNumber) {
        this.ownNumber = ownNumber;
    }

    public int getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(int readNumber) {
        this.readNumber = readNumber;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public BookBean(String name, String writer, String introduce, int ownNumber, int readNumber,String coverImg) {
        this.name = name;
        this.writer = writer;
        this.introduce = introduce;
        this.ownNumber = ownNumber;
        this.readNumber = readNumber;
        this.coverImg = coverImg;
    }
    public BookBean() {

    }
}
