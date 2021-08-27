package com.zhuaowei.bookstore.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @ClassName: ReadState
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/20 10:50
 * @Version: 1.0
 **/
@TableName("member_read_state")
public class MemberReadState {
    @TableId(type = IdType.AUTO)
    private int rsId;
    private int bookId;
    private int memberId;
    private int readState;
    private Date createTime;

    @Override
    public String toString() {
        return "MemberReadState{" +
                "rsId=" + rsId +
                ", bookId=" + bookId +
                ", memberId=" + memberId +
                ", readState=" + readState +
                ", createTime=" + createTime +
                '}';
    }

    public int getRsId() {
        return rsId;
    }

    public void setRsId(int rsId) {
        this.rsId = rsId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
