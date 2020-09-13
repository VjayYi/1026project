package com.butuh.uang.bu.tuhu.bean;

import com.butuh.uang.bu.tuhu.util.CommonUtil;
import com.butuh.uang.bu.tuhu.util.NumberUtil;

import java.util.List;

public class PageTableBean<T> {

    /**
     * assemble : 0
     * pageTable : 1
     * kuantitas : []
     */

    private int assemble;
    private int pageTable;
    private List<T> kuantitas;

    public int getAssemble() {
        return assemble;
    }

    public void setAssemble(int assemble) {
        this.assemble = assemble;
    }

    public int getPageTable() {
        return pageTable;
    }

    public void setPageTable(int pageTable) {
        this.pageTable = pageTable;
    }

    public List<T> getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(List<T> kuantitas) {
        this.kuantitas = kuantitas;
    }

    public boolean haveMore(){
        int totalPage=assemble/10+(assemble%10>0?1:0);
        return pageTable<totalPage;
    }
}
