package com.example.eleme.data;

public class OneComment {
    String customerName;
    String commentContent;
    double star;

    public OneComment(String customerName, String commentContent, double star) {
        this.customerName = customerName;
        this.commentContent = commentContent;
        this.star = star;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }
}
