package com.zhuaowei.bookstore.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName: Book
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/18 11:25
 * @Version: 1.0
 **/
@TableName("book")
public class Book {
    @TableId(type = IdType.AUTO)
    private int bookId;
    private String bookName;
    private String subTitle;
    private String author;
    private String cover;
    private String description;
    private int categoryId;
    private float evaluationScore;
    private int evaluationQuantity;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", author='" + author + '\'' +
                ", cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", evaluationScore=" + evaluationScore +
                ", evaluationQuantity=" + evaluationQuantity +
                '}';
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(float evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public int getEvaluationQuantity() {
        return evaluationQuantity;
    }

    public void setEvaluationQuantity(int evaluationQuantity) {
        this.evaluationQuantity = evaluationQuantity;
    }
}
