package com.axonactive.jpa.enumerate;

public enum Nationality {
    VIETNAM(1),
    CHINA(2),
    USA(3),
    JAPAN(4),
    RUSSIA(5);
    private int value;

    Nationality(int value){this.value = value;}

    public int getValue(){return this.value;}
}
