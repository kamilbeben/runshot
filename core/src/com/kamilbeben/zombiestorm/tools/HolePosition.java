package com.kamilbeben.zombiestorm.tools;

/**
 * Created by bezik on 01.10.16.
 */
public class HolePosition {

    public int start;
    public int length;
    public int end;

    public HolePosition(int start, int length) {
        this.start = start;
        this.length = length;
        end = start + length;
    }
}
