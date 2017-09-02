package com.remswork.project.alice.bean;

import org.springframework.stereotype.Component;

@Component
public class GradeHelperBean {

    private int total;

    public GradeHelperBean() {
        super();
    }

    public GradeHelperBean add(double percent) {
        total += (percent * 100);
        return this;
    }

    public boolean isValid() {
        return total <= 100 && total >= 0 ;
    }

    public int getTotal() {
        int _total = total;
        total = 0;
        return _total;
    }
}
