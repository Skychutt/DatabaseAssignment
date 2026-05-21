package com.COMP2013J.assignment.utils.vo;

import java.util.List;

/**
 * 分页工具
 */
public class PagerVO<T> {
    int current;
    int size;
    int total;
    List<T> list;
    int totalPages;
    boolean showLeft;
    boolean showRight;
    int[] pageNums;

    public void init(){
        if (size <= 0) {
            size = 10;
        }
        if (current <= 0) {
            current = 1;
        }
        if (total < 0) {
            total = 0;
        }
        //计算页数
        totalPages = total / size;
        if(total % size > 0){
            totalPages ++;
        }
        if (totalPages == 0) {
            totalPages = 1;
        }
        if (current > totalPages) {
            current = totalPages;
        }
        //向左向右
        if(current == 1){
            showLeft = false;
        }else{
            showLeft = true;
        }
        if(current == totalPages){
            showRight = false;
        }else{
            showRight = true;
        }

        //计算起始页码和结束页码
        int min = current - 5;
        int max = current + 5;
        if(min < 1){
            min = 1;
        }
        if (max > totalPages){
            max = totalPages;
        }
        int length = max - min + 1;
        pageNums = new int[length];
        for (int i = 0; i < length; i++) {
            pageNums[i] = i + min;
        }
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isShowLeft() {
        return showLeft;
    }

    public boolean isShowRight() {
        return showRight;
    }

    public int[] getPageNums() {
        return pageNums;
    }
}
