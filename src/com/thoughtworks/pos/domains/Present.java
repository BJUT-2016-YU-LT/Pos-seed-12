package com.thoughtworks.pos.domains;

/**
 * Created by fht_6 on 2016/7/1.
 */
public class Present {
    private String name;
    private  int point;

    Present(String name, int point)
    {
        this.setName(name);
        this.setPoint(point);
    }

    public void setName(String name) { this.name = name; }

    public void setPoint(int point) { this.point = point;}

    public String getName() { return this.name; }

    public int getPoint() { return this.point; }
}
