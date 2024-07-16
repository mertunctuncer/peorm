package me.mertunctuncer.peorm.mock;

import me.mertunctuncer.peorm.api.annotation.*;

@Table(name = "named-mock-table")
public class NamedMockTable {
    @Column(name = "no")
    @Identity @NotNull
    private int id;

    @Column
    @AutoIncrement ( increment = 2 ) @NotNull
    private int count;



}
