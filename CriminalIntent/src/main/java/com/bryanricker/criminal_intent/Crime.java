
package com.bryanricker.criminal_intent;

import java.util.UUID;
import java.util.Date;


public class Crime
{

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mIsSolved;


    public Crime()
    {
        mId     = UUID.randomUUID();
        mDate   = new Date();
    }


    public UUID getId()
    {
        return mId;
    }


    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }


    public Date getDate()
    {
        return mDate;
    }

    public void setDate(Date date)
    {
        mDate = date;
    }


    public boolean isSolved()
    {
        return mIsSolved;
    }

    public void setIsSolved(boolean solved)
    {
        mIsSolved = solved;
    }

}