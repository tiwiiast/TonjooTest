package com.example.iast.tonjootest.helper;

import com.example.iast.tonjootest.model.ContactDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iast on 1/17/17.
 */

public class Paginator {
    private int ITEMS_PER_PAGE=4;
    public Paginator(){
    }
    public List<ContactDao> generatePage(int currentPage, List<ContactDao> contactList, int total)
    {
        int startItem=currentPage*ITEMS_PER_PAGE;
        int numOfData=ITEMS_PER_PAGE;

        List<ContactDao> pageData=new ArrayList<ContactDao>();



        if (currentPage==(total/ITEMS_PER_PAGE) && (total % ITEMS_PER_PAGE)>0)
        {
            for (int i=startItem;i<startItem+(total % ITEMS_PER_PAGE);i++)
            {
                pageData.add(contactList.get(i));
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                pageData.add(contactList.get(i));
            }
        }


        return pageData;
    }
}
