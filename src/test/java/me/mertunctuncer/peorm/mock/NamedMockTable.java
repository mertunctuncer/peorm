package me.mertunctuncer.peorm.mock;

import me.mertunctuncer.peorm.api.annotation.*;

@Table(name = "named-mock-table")
public class NamedMockTable {
    @Column(name = "no")
    @PrimaryKey @Identity(seed = 9, increment = 2)
    private int id;

    @Column
    @AutoIncrement ( increment = 2 )
    private int count;


    @Column(
            name = "NAME",
            nullable = false,
            size = 123
    )
    @ForeignKey  @Unique
    private String name;




}
