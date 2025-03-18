package tpo.lab1.model;

import tpo.lab1.model.enums.BlockResult;

import java.util.List;

public class BlockAttempt {
    private List<Person> blockers;
    private List<Person> intruders;
    private BlockResult result;
    private Integer block;

    public BlockAttempt(List<Person> blockers, List<Person> intruders,Integer block) {
        this.blockers = blockers;
        this.intruders = intruders;
        this.result = null;
        this.block = block;
    }
    public BlockAttempt(){

    }

    public Integer getBlock(){
        return  this.block;
    }

    public BlockResult getResult() { return result; }
}